package com.example.alhanoufaldawood.conlang.Translator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class custom_pdf_viewer extends AppCompatActivity {
    private TextView text2;
    private PDFView pdfViewer2;
    public static String  key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_pdf_viewer);
        getSupportActionBar().setTitle("Review file");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //To get the key.
        Intent intent = getIntent();
        key= intent.getStringExtra("key");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CustomRequests").child(key).child("fileURL");
        pdfViewer2 = (PDFView) findViewById(R.id.custompdfviewer);
        text2 = (TextView) findViewById(R.id.text2);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                text2.setText(value);
                Toast.makeText(custom_pdf_viewer.this, "Loading..", Toast.LENGTH_SHORT).show();

                String url = text2.getText().toString();
                new custom_pdf_viewer.RetrivePdfStream().execute(url);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(custom_pdf_viewer.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });
    }//onCreate.

    class RetrivePdfStream extends AsyncTask<String,Void,InputStream>{

        @Override
        protected InputStream doInBackground(String... strings){
            InputStream inputStream;
            //ToDo here was InputStream inputStream=null;
            try {
                URL url=new URL (strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==200);{
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }//if

            }catch (IOException e){
                return null;
            }//catch.
            return inputStream;
        }//doInBackground method

        @Override
        protected void onPostExecute(InputStream inputStream){
            pdfViewer2.fromStream(inputStream).load();
        }
    }
}



