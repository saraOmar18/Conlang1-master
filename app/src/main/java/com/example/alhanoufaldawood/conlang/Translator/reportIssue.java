package com.example.alhanoufaldawood.conlang.Translator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reportIssue extends AppCompatActivity implements View.OnClickListener {
    Spinner mySpinner;
    EditText desc;
    Button send;
    Button close;
    FirebaseAuth mAuth;
    ReportedIssues userInfo;
    DatabaseReference databaseReference;
     FirebaseUser user;

    String descriptions;
    String IssueType;
    String reporterType;
    String reporterId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        databaseReference = FirebaseDatabase.getInstance().getReference("ReportedIssues");
        send = findViewById(R.id.btnSend);
        mySpinner = findViewById(R.id.spinner1);
        desc = findViewById(R.id.description);
        // ToDo current user ;
         user = FirebaseAuth.getInstance().getCurrentUser();
        reporterId =user.getUid();




        findViewById(R.id.btnSend).setOnClickListener(this);
//        findViewById(R.id.dialog_cancel).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        // ToDo check current user not equal null.


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(reportIssue.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.issues));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter); // allow the adapter to be shown in the spinner.

    }




    public void sendIssue(){

        descriptions = desc.getText().toString().trim();
        IssueType = mySpinner.getSelectedItem().toString().trim();
        reporterType ="Translator";



        if (descriptions.isEmpty()) {
            desc.setError("Please describe the issue to help us to improve our services ");
            desc.requestFocus();
            return;}

        userInfo= new ReportedIssues(descriptions,IssueType,reporterType,reporterId);

        // ToDo current user ; .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
        databaseReference.push().setValue(userInfo);
        Toast.makeText(this,"sending..",Toast.LENGTH_LONG).show();


    }



    public void openDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(reportIssue.this);
        dialog.setCancelable(false);
        dialog.setTitle("Thank you for helping us to improve our services..");
        dialog.setMessage("Your request has been sent to our team successfully." );
        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(reportIssue.this , TranslatorHome.class);
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
