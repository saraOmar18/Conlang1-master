package com.example.alhanoufaldawood.conlang.Translator;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class customDetails extends AppCompatActivity  {
    String customKey;
    DatabaseReference ref;
    Activity context;
    TextView reguestNo;
    TextView customerName;
    TextView reguestStatus;
    TextView requestComment;
    TextView requestField;
    TextView transFrom;
    TextView transTo;
    String st;
    String name;
    Button preview;
    Button accept;
    Button reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_details);
        getSupportActionBar().setTitle("Request details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ref = FirebaseDatabase.getInstance().getReference("Orders");
        context = this;

        Intent i = getIntent();
        customKey = i.getStringExtra("key");

        Toast.makeText(customDetails.this,customKey,Toast.LENGTH_LONG).show();

        preview = (Button) findViewById(R.id.reviewCustom);
        accept = (Button) findViewById(R.id.acceptCustom);
        reject = (Button) findViewById(R.id.reject);


        final DatabaseReference mref = FirebaseDatabase.getInstance().getReference("Orders").child(customKey);

        reguestNo = (TextView) findViewById(R.id.customOrderNumber);
        customerName = (TextView) findViewById(R.id.customCustomerN);
        reguestStatus = (TextView) findViewById(R.id.customOrderStatus);
        requestComment = (TextView) findViewById(R.id.customComment);
        requestField = (TextView) findViewById(R.id.customDocumentFiled);
        transFrom = (TextView) findViewById(R.id.customTranslateFrom);
        transTo = (TextView) findViewById(R.id.customTranslateTo);

        mref.child("translatorStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                st = snapshot.getValue(String.class);

                if (st.equals("Rejected") ||st.equals("Offer sent to the customer") ) {

                    accept.setVisibility(View.GONE);
                    reject.setVisibility(View.GONE);

                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   PublicRequests pRequest = new PublicRequests();
                CustomRequests request = dataSnapshot.getValue(CustomRequests.class);
                // String orderNo = request.getorderNo();


                reguestNo.setText(request.getorderNo());
                customerName.setText(request.getname());
                reguestStatus.setText(request.getstatus());
                requestComment.setText(request.getcomment());
                requestField.setText(request.getfield());
                transFrom.setText(request.getfrom());
                transTo.setText(request.getto());

                //ToDo customer name.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mref.child("translatorName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                name = snapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Custom_request_pdf_viewer.class);
                i.putExtra("key", customKey);
                startActivity(i);

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (st.equals("Rejected")) {
                   // alreadyRejected();
                }
                else
                if(st.equals("Offer sent to the customer")){
                   // openErrorDialog();
                }else {
                    Intent intent2 = new Intent(context, sendCustomOffer.class);
                    intent2.putExtra("key", customKey);
                    startActivity(intent2);
                    Toast.makeText(customDetails.this,"Request has been accepted successfully",Toast.LENGTH_LONG).show();

                }
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(st.equals("Offer sent to the customer")){
                   // openErrorDialog();
                   accept.setVisibility(View.GONE);
                   reject.setVisibility(View.GONE);
                }
                else
                if(st.equals("Rejected")){
                   // alreadyRejected();
                    accept.setVisibility(View.GONE);
                    reject.setVisibility(View.GONE);
                    }
                else
                    openRejectingDialog();
            }


        });
    }
    public void openErrorDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(customDetails.this);
        dialog.setCancelable(false);
        dialog.setTitle("You already sent an offer! ");
        dialog.setMessage("Please waite for customer acceptance.." );

        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Delete".

            }
        });


        final AlertDialog alert = dialog.create();
        alert.show();
    }


    public void openRejectingDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(customDetails.this);
        dialog.setCancelable(false);
        dialog.setTitle("Dear "+name+" ..");
        dialog.setMessage(" Are you sure about rejecting this offer?" );


        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ref.child(customKey).child("status").setValue("Rejected");
                ref.child(customKey).child("translatorStatus").setValue("Rejected");
                Toast.makeText(customDetails.this,"The request has been rejected successfully",Toast.LENGTH_LONG).show();
                accept.setVisibility(View.GONE);
                reject.setVisibility(View.GONE);
                finish();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();
    }

    public void alreadyRejected(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(customDetails.this);
        dialog.setCancelable(false);
        dialog.setTitle("Dear "+name+" ..");
        dialog.setMessage("Sorry,This request has been rejected by you!" );
        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Delete".
            }
        });


        final AlertDialog alert = dialog.create();
        alert.show();
    }

}