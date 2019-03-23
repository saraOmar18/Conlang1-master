package com.example.alhanoufaldawood.conlang.Translator;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class TranslatorRegistrationRequest extends AppCompatActivity {

    EditText nationalId1 ;
    Button upload;
    Button send;
    TextView fileName1;
    Uri fileURL;

    public String key;

    private StorageReference storage;
    DatabaseReference RegistrationRequests;

    ProgressDialog progressDialog;



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator_registration_request);


        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signin_actionbar, null));
        // set the view now

        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        ////////////////  VARIABLES /////////////////////////////

        nationalId1 = (EditText) findViewById(R.id.NationalId);
        send = (Button) findViewById(R.id.Send);
        upload = (Button) findViewById(R.id.uploadFile);
        fileName1 = (TextView) findViewById(R.id.FileName);

        storage = FirebaseStorage.getInstance().getReference();
        RegistrationRequests = FirebaseDatabase.getInstance().getReference("RegistrationRequests");

        ///////////////////////////////////////////////////////////

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (ContextCompat.checkSelfPermission(TranslatorRegistrationRequest.this , android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    selectFile();
               }else {

                   ActivityCompat.requestPermissions(TranslatorRegistrationRequest.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AddInfo();
                uploadFile();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            selectFile();
        }else{
            Toast.makeText(TranslatorRegistrationRequest.this,"Please provide us a permission",Toast.LENGTH_LONG);
        }
    }

    private void AddInfo(String URL){

        String  nationalId =  nationalId1.getText().toString().trim();

        if (!TextUtils.isEmpty(nationalId)) {

            RegistrationRequests.child(key).child("id").setValue(nationalId);
            RegistrationRequests.child(key).child("ResumePath").setValue(URL);
            openDialog();


        }


    }

    public void openDialog(){

        RegistrationRequestDialog dialog = new RegistrationRequestDialog();
        dialog.show(getSupportFragmentManager(),"Dialog");
    }

    public void selectFile(){

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent ,"Select file"),86);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode ==86 && resultCode == RESULT_OK && data!= null && data.getData() != null){

            //fileURL = data.getData();
            //fileName1.setText(data.getData().getLastPathSegment());
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }

            fileName1.setText(displayName);


        }else {
            Toast.makeText(TranslatorRegistrationRequest.this,"Please select file",Toast.LENGTH_LONG);
        }
    }

    public void  uploadFile(){


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Sending request...");
        progressDialog.setProgress(0);
        progressDialog.show();

        String filePath = System.currentTimeMillis()+"";

        final StorageReference riversRef = storage.child("test").child("test.."+filePath);

        riversRef.putFile(fileURL) .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String URL = uri.toString();
                        AddInfo(URL);

                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TranslatorRegistrationRequest.this ,"File not successfully uploaded", Toast.LENGTH_LONG);


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        nationalId1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(nationalId1.getText().toString()) ) {
                    send.setBackground(ContextCompat.getDrawable(TranslatorRegistrationRequest.this, R.drawable.send_request_onclick));// set here your backgournd to button
                }else {
                    send.setBackground(ContextCompat.getDrawable(TranslatorRegistrationRequest.this, R.drawable.send_request));// set here your backgournd to button
                }

            }});


    }
}

