package com.example.alhanoufaldawood.conlang.Translator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class sendOffer extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "sendOffer";

    EditText translationPrice;
    EditText translatorComment;
    Button send;
    FirebaseAuth mAuth;
    requestsOffers offerInfo;
    static String requestID;
    String price;
    String offerComments;

    String deadline;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    DatabaseReference mref;
    DatabaseReference updateStatus;

    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_offer);
        send = findViewById(R.id.sendToCustomer);
        translationPrice = findViewById(R.id.price);
        translatorComment = findViewById(R.id.offerComment);
        mDisplayDate = (TextView) findViewById(R.id.offerData);

        mref = FirebaseDatabase.getInstance().getReference("requestsOffers");
        updateStatus=FirebaseDatabase.getInstance().getReference("PublicRequests");

        context= this;
        findViewById(R.id.sendToCustomer).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        requestID = i.getStringExtra("key");

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        sendOffer.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                deadline = month + "/" + day + "/" + year;
                mDisplayDate.setText(deadline);
            }
        };

    }
        public void sendOffer () {

            price = translationPrice.getText().toString().trim();
            offerComments = translatorComment.getText().toString().trim();

         //   offerInfo = new requestsOffers(price, offerComments, deadline,"test without customer id in sendOffer class line 102");

            /*if (price == null || deadline == null) {
                if (price == null) {
                    price.setError("Price is required!");
                }
                if (deadline == null) {
                    deadline.setError("Deadline is required!");
                }
                return;
            } else {*/

                // ToDo current user ; .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).

                mref.child(requestID).setValue(offerInfo);
                updateStatus.child(requestID).child("status").setValue("Wait for offer acceptance");
                Toast.makeText(this, "sending..", Toast.LENGTH_LONG).show();
           // }
        }

    public void openDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(sendOffer.this);
        dialog.setCancelable(false);
        dialog.setTitle("Your offer has been sent successfully");
        dialog.setMessage("Wait for customer acceptance.." );
        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Action for "Delete".
                Intent i = new Intent(sendOffer.this , TranslatorHome.class);
                startActivity(i);
            }
        });


        final AlertDialog alert = dialog.create();
        alert.show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendToCustomer:
                sendOffer();
                openDialog();
                break;
                //ToDo.
               // Intent intent = new Intent(context, TranslatorHome.class);
                //startActivity(intent);
        }
    }
}
