package com.example.alhanoufaldawood.conlang.Translator;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class details extends AppCompatActivity {
    public static String  publicDetailKey;
    DatabaseReference ref;
    Activity context;
    TextView  reguestNo;
    TextView  customerName;
    TextView  reguestStatus;
    TextView  requestComment;
    TextView  requestField;
    TextView  transFrom;
    TextView  transTo;

    Button filePreview;
    Button accept;
    Button reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setTitle("Request details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        filePreview = (Button) findViewById(R.id.review);
        accept = (Button) findViewById(R.id.offer);
        reject = (Button) findViewById(R.id.reject);


        ref = FirebaseDatabase.getInstance().getReference("PublicRequests");

        context= this;

        Intent i = getIntent();
        publicDetailKey=i.getStringExtra("key");

        Toast.makeText(details.this,publicDetailKey,Toast.LENGTH_LONG).show();

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("PublicRequests").child(publicDetailKey);

        reguestNo = (TextView) findViewById(R.id.publicOrderNumber);
        customerName = (TextView) findViewById(R.id.CustomerN);
        reguestStatus = (TextView) findViewById(R.id.publicOrderStatus);
        requestComment = (TextView) findViewById(R.id.publicComment);
        requestField = (TextView) findViewById(R.id.publicDocumentFiled);
        transFrom = (TextView) findViewById(R.id.TranslateFrom);
        transTo = (TextView) findViewById(R.id.TranslateTo);




        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   PublicRequests pRequest = new PublicRequests();
                PublicRequests  pRequest = dataSnapshot.getValue(PublicRequests.class);

                String orderNo = pRequest.getorderNo();

                reguestNo.setText(orderNo);
                customerName.setText(pRequest.getcustomerName());
                reguestStatus.setText(pRequest.getStatus());
                requestComment.setText(pRequest.getcomment());
                requestField.setText(pRequest.getfield());
                transFrom.setText(pRequest.getFrom());
                transTo.setText(pRequest.getto());

                //ToDo customer name.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        filePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, pdf_viewer.class);
                intent.putExtra("key", publicDetailKey);
                startActivity(intent);

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(context, sendCustomOffer.class);
                intent2.putExtra("key", publicDetailKey);
                startActivity(intent2);


            }
        });

    }




}