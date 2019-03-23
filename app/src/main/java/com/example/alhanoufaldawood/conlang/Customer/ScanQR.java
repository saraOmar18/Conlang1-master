package com.example.alhanoufaldawood.conlang.Customer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.alhanoufaldawood.conlang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.app.Activity;
import android.support.annotation.Nullable;

import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;



public class ScanQR extends AppCompatActivity {
    private Button scan;
    private DatabaseReference db;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String ticketCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initElement();
    }
    private void initElement(){
        setContentView(R.layout.activity_scan_qr);
        ticketCode=null;
        scan =(Button)findViewById(R.id.scan);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        final Activity activity = this;
        progressDialog = new ProgressDialog(this);


        /*bottom nav*/





        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator= new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);// in orginall code false :)
                integrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()==null){
                Toast.makeText(this,"You canceled the scanner",Toast.LENGTH_LONG).show();
            }
            else {
                // Toast.makeText(this,"Notnull",Toast.LENGTH_LONG).show();
                //
            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}