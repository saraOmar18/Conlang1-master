package com.example.alhanoufaldawood.conlang.Translator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestRegistration2 extends AppCompatActivity {


    public String key;
    RadioGroup type;
    Button next;
    DatabaseReference RegistrationRequests;
    String userID;


    private FirebaseAuth auth;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_registration2);


        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signin_actionbar, null));
        // set the view now

        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        type = (RadioGroup) findViewById(R.id.radioGroup);

        next = (Button) findViewById(R.id.Next);


        RegistrationRequests = FirebaseDatabase.getInstance().getReference("RegistrationRequests");


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                add();

            }
        });
    }

    public void add() {

        String Type = "";

        int selectedId = type.getCheckedRadioButtonId();
        RadioButton selectedGender = (RadioButton) findViewById(selectedId);
        Type = selectedGender.getText().toString();

        RegistrationRequests.child(key).child("type").setValue(Type);


        if (Type.equals("Freelancer")) {

            Intent register = new Intent(RequestRegistration2.this, TranslatorRegistrationRequest.class);
            register.putExtra("key", key);
            startActivity(register);
        } else {

            Intent register = new Intent(RequestRegistration2.this, OfficeRegistrationRequest.class);
            register.putExtra("key", key);
            startActivity(register);
        }


    }
}