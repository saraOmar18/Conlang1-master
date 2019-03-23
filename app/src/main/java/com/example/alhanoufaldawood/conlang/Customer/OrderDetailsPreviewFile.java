package com.example.alhanoufaldawood.conlang.Customer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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



public class OrderDetailsPreviewFile extends AppCompatActivity {

        private TextView text1;
        private PDFView pdfViewer;

        Activity context;
        String  key;

        @SuppressLint("RestrictedApi")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order_details_preview_file);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back"+" </font>"));
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar, null));



            context = this;

            //To get key.
            Intent i = getIntent();
            key= i.getStringExtra("key");

            Toast.makeText(OrderDetailsPreviewFile.this,key,Toast.LENGTH_LONG).show();



            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders").child(key).child("fileURL");
            pdfViewer = (PDFView) findViewById(R.id.pdfviewer);
            text1 = (TextView) findViewById(R.id.text1);



            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value=dataSnapshot.getValue(String.class);
                    text1.setText(value);
                    Toast.makeText(OrderDetailsPreviewFile.this, "Loading..", Toast.LENGTH_SHORT).show();

                    String url = text1.getText().toString();
                    new RetrivePdfStream().execute(url);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(OrderDetailsPreviewFile.this, "Failed to load", Toast.LENGTH_SHORT).show();
                }
            });
        }//onCreate.

        class RetrivePdfStream extends AsyncTask<String,Void,InputStream> {

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
                pdfViewer.fromStream(inputStream).load();
            }
        }
    }



