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
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestRegistration extends AppCompatActivity {


    EditText name1,email1,password1,confirmPassword1;
    Button next;
    DatabaseReference RegistrationRequests;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_registration);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back To Sign in"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signin_actionbar, null));
        // set the view now

        ////////////////  VARIABLES /////////////////////////////

        name1 = (EditText) findViewById(R.id.Name);
        email1 = (EditText) findViewById(R.id.Email);
        password1 = (EditText) findViewById(R.id.Password);
        confirmPassword1 = (EditText) findViewById(R.id.ConfirmPassword);

        next = (Button) findViewById(R.id.Next);


        RegistrationRequests = FirebaseDatabase.getInstance().getReference("RegistrationRequests");

        ///////////////////////////////////////////////////////////

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInfo();
            }
        });
    }

    private void AddInfo(){

        String name = name1.getText().toString().trim();
        String email = email1.getText().toString().trim();
        String password =password1.getText().toString().trim();
        String confirmPassword =confirmPassword1.getText().toString().trim();
        String Type = "";

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) &&  !TextUtils.isEmpty(password)) { //first

            if (TextUtils.equals(password,confirmPassword) && isEmailValid(email) && isValidPassword(password)){ //second




                    String key = RegistrationRequests.push().getKey();

                    RegistrationRequest request = new RegistrationRequest(name, email, password, " ", "");

                    RegistrationRequests.child(key).setValue(request);



                        Intent register = new Intent(RequestRegistration.this, RequestRegistration2.class);
                        register.putExtra("key", key);
                        startActivity(register);






            }else{ // second
                if (!TextUtils.equals(password,confirmPassword) && !isEmailValid(email) && !isValidPassword(password)){ //second

                    password1.setError("Password must contain minimum 8 characters at least 1 Alphabet, 1 Number");
                    email1.setError("Invalid Email format");
                    confirmPassword1.setError("Passwords does not match");
            }else if (TextUtils.equals(password,confirmPassword) && !isEmailValid(email) && !isValidPassword(password)) { //second

                    password1.setError("Password must contain minimum 8 characters at least 1 Alphabet, 1 Number");
                    email1.setError("Invalid Email format");

                }else if (TextUtils.equals(password,confirmPassword) && isEmailValid(email) && !isValidPassword(password)) { //second

                    password1.setError("Password must contain minimum 8 characters at least 1 Alphabet, 1 Number");

                }else if (TextUtils.equals(password,confirmPassword) && !isEmailValid(email) && isValidPassword(password)) { //second

                    email1.setError("Invalid Email format");

                }else if (!TextUtils.equals(password,confirmPassword) && isEmailValid(email) && !isValidPassword(password)) { //second

                    password1.setError("Password must contain minimum 8 characters at least 1 Alphabet, 1 Number");
                    confirmPassword1.setError("Passwords does not match");
                }else if (!TextUtils.equals(password,confirmPassword) && isEmailValid(email) && isValidPassword(password)) { //second

                    confirmPassword1.setError("Passwords does not match");

                }else if (TextUtils.equals(password,confirmPassword) && isEmailValid(email) && !isValidPassword(password)) { //second

                    password1.setError("Password must contain minimum 8 characters at least 1 Alphabet, 1 Number");
                }else if (!TextUtils.equals(password,confirmPassword) && !isEmailValid(email) && isValidPassword(password)) { //second

                    confirmPassword1.setError("Passwords does not match");
                    email1.setError("Invalid Email format");

                }else if (!TextUtils.equals(password,confirmPassword) && isEmailValid(email) && isValidPassword(password)) { //second

                    confirmPassword1.setError("Passwords does not match");

                }else if (TextUtils.equals(password,confirmPassword) && !isEmailValid(email) && isValidPassword(password)) { //second

                    email1.setError("Invalid Email format");

                }

                }
        }else { // first
            Toast.makeText(RequestRegistration.this, "required fields", Toast.LENGTH_LONG).show();


        }



        }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
        = Pattern.compile("^" +

                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }


    @Override
    protected void onStart() {
        super.onStart();


        name1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(email1.getText().toString()) && !TextUtils.isEmpty(name1.getText().toString()) &&!TextUtils.isEmpty(password1.getText().toString()) && !TextUtils.isEmpty(confirmPassword1.getText().toString()) ) {
                    next.setBackground(ContextCompat.getDrawable(RequestRegistration.this, R.drawable.contin_onclick));// set here your backgournd to button
                }else {
                    next.setBackground(ContextCompat.getDrawable(RequestRegistration.this, R.drawable.contin));// set here your backgournd to button
                }

            }});

        email1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(email1.getText().toString()) && !TextUtils.isEmpty(name1.getText().toString()) &&!TextUtils.isEmpty(password1.getText().toString()) && !TextUtils.isEmpty(confirmPassword1.getText().toString()) ) {
                    next.setBackground(ContextCompat.getDrawable(RequestRegistration.this, R.drawable.contin_onclick));// set here your backgournd to button
                }else {
                    next.setBackground(ContextCompat.getDrawable(RequestRegistration.this, R.drawable.contin));// set here your backgournd to button
                }
            }
        });

        password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(email1.getText().toString()) && !TextUtils.isEmpty(name1.getText().toString()) &&!TextUtils.isEmpty(password1.getText().toString()) && !TextUtils.isEmpty(confirmPassword1.getText().toString()) ) {
                    next.setBackground(ContextCompat.getDrawable(RequestRegistration.this, R.drawable.contin_onclick));// set here your backgournd to button
                }else {
                    next.setBackground(ContextCompat.getDrawable(RequestRegistration.this, R.drawable.contin));// set here your backgournd to button
                }
            }
        });

        confirmPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(email1.getText().toString()) && !TextUtils.isEmpty(name1.getText().toString()) &&!TextUtils.isEmpty(password1.getText().toString()) && !TextUtils.isEmpty(confirmPassword1.getText().toString()) ) {
                    next.setBackground(ContextCompat.getDrawable(RequestRegistration.this, R.drawable.contin_onclick));// set here your backgournd to button
                }else {
                    next.setBackground(ContextCompat.getDrawable(RequestRegistration.this, R.drawable.contin));// set here your backgournd to button
                }
            }
        });

    }
    }

