package com.example.alhanoufaldawood.conlang.Translator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfficeRegistrationRequest extends AppCompatActivity {

    EditText officeRegisterNo1;
    Button send;

    public String key;

    DatabaseReference RegistrationRequests;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offic_registration_request);


        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signin_actionbar, null));
        // set the view now

        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        ////////////////  VARIABLES /////////////////////////////

        officeRegisterNo1 = (EditText) findViewById(R.id.NationalId);
        send = (Button) findViewById(R.id.Send);

        RegistrationRequests = FirebaseDatabase.getInstance().getReference("RegistrationRequests");

        ///////////////////////////////////////////////////////////


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInfo();
            }
        });
    }

    private void AddInfo(){

       String  officeRegisterNo =  officeRegisterNo1.getText().toString().trim();

        if (!TextUtils.isEmpty(officeRegisterNo)) {

            RegistrationRequests.child(key).child("id").setValue(officeRegisterNo);
            openDialog();


       }

    }

    public void openDialog(){

        RegistrationRequestDialog dialog = new RegistrationRequestDialog();
        dialog.show(getSupportFragmentManager(),"Dialog");
    }

    @Override
    protected void onStart() {
        super.onStart();


        officeRegisterNo1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(officeRegisterNo1.getText().toString()) ) {
                    send.setBackground(ContextCompat.getDrawable(OfficeRegistrationRequest.this, R.drawable.send_request_onclick));// set here your backgournd to button
                }else {
                    send.setBackground(ContextCompat.getDrawable(OfficeRegistrationRequest.this, R.drawable.send_request));// set here your backgournd to button
                }

            }});


    }
}

