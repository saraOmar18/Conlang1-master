package com.example.alhanoufaldawood.conlang;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.alhanoufaldawood.conlang.Customer.CustomerHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ReportIssue extends AppCompatActivity implements View.OnClickListener {
    Spinner mySpinner;
    EditText description1;
    Button send;
    FirebaseAuth mAuth;
    Issue ReportedIssue;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String item;
    String description;
    String UserType;
    String currentUser;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signin_actionbar, null));
        // set the view now

        databaseReference = FirebaseDatabase.getInstance().getReference("ReportedIssues");
        send = findViewById(R.id.btnSend);
        mySpinner = findViewById(R.id.spinner1);
        description1 = findViewById(R.id.description);
        // ToDo current user ;
        //user = mAuth.getCurrentUser(); // This is not how to get the current user
        findViewById(R.id.btnSend).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        // ToDo check current user not equal null.

        user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser =user.getUid();





     ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ReportIssue.this,
             android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.issues));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter); // allow the adapter to be shown in the spinner.


    }



    public void sendIssue() {

          description = description1.getText().toString().trim();
          item = mySpinner.getSelectedItem().toString().trim();
          UserType="Customer";

         // ToDo if translator replace customer by translator.. rType="Translator";


        if (description.isEmpty()) {
            description1.setError("Please describe the issue to help us to improve our services ");
            description1.requestFocus();
            return;}

        ReportedIssue= new Issue(description,item,UserType,currentUser);

          // ToDo current user ;
            databaseReference.push().setValue(ReportedIssue);
        }

    public void openDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReportIssue.this);
        dialog.setCancelable(false);
        dialog.setTitle("Thank you for helping us to improve our services..");
        dialog.setMessage("Your request has been sent to our team successfully." );
        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(ReportIssue.this , CustomerHome.class);
                startActivity(i);
            }
        });


        final AlertDialog alert = dialog.create();
        alert.show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                sendIssue();
                openDialog();
                break;
        }
    }
}