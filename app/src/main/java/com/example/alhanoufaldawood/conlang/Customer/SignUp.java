package com.example.alhanoufaldawood.conlang.Customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


//import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SignUp extends AppCompatActivity  {

    EditText editTextName, editTextEmail, editTextPassword1, editTextPassword2;
    ProgressBar progressBar;
    Button signUpbtn;

    FirebaseAuth mAuth;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +

                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back To Sign in"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signin_actionbar, null));

        editTextName =  findViewById(R.id.Name);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword1 =  findViewById(R.id.editTextPassword1);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        progressBar= findViewById(R.id.progressbar) ;
        progressBar.setVisibility((View.GONE));

        mAuth = FirebaseAuth.getInstance();

        signUpbtn = (Button) findViewById(R.id.buttonSignUp);

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }



    private void registerUser(){

        final String username = editTextName.getText().toString().trim();
        String password1 = editTextPassword1.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String type = "Customer";


        if (username.isEmpty()) {
            editTextName.setError("Your name is required");
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (password1.isEmpty()) {
            editTextPassword1.setError("Password is required");
            editTextPassword1.requestFocus();
            return;
        }

        if (password2.isEmpty()) {
            editTextPassword2.setError("Confirm password is required");
            editTextPassword2.requestFocus();
            return;
        }

//Check email format.

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter valid email");
            editTextEmail.requestFocus();
            return;  }


//Check if the user already exist.



 //valid password.
        if (!PASSWORD_PATTERN.matcher(password1).matches()) {
            editTextPassword1.setError("Password should be at least 6 characters contains any letter ");
            editTextPassword1.requestFocus();
            return;}

            if (!password2.equals(password1)){
                editTextPassword2.setError("Confirm password dose not match password");
                editTextPassword2.requestFocus();
                return;
            }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            //store fields in firebase DB.

                            User user = new User(type, email);
                            Customer customer = new Customer(username);

                            FirebaseDatabase.getInstance().getReference("Customers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(customer);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "register successfully", Toast.LENGTH_LONG).show();
                                        Intent register = new Intent(SignUp.this, CustomerHome.class);
                                        startActivity(register);
                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }



    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null) {
            //handle already login user.
        }



        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editTextEmail.getText().toString()) && !TextUtils.isEmpty(editTextName.getText().toString()) &&!TextUtils.isEmpty(editTextPassword1.getText().toString()) && !TextUtils.isEmpty(editTextPassword2.getText().toString()) ) {
                    signUpbtn.setBackground(ContextCompat.getDrawable(SignUp.this, R.drawable.signup_onclick));// set here your backgournd to button
                }else {
                    signUpbtn.setBackground(ContextCompat.getDrawable(SignUp.this, R.drawable.signup));// set here your backgournd to button
                }

            }});

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editTextEmail.getText().toString()) && !TextUtils.isEmpty(editTextName.getText().toString()) &&!TextUtils.isEmpty(editTextPassword1.getText().toString()) && !TextUtils.isEmpty(editTextPassword2.getText().toString()) ) {
                    signUpbtn.setBackground(ContextCompat.getDrawable(SignUp.this, R.drawable.signup_onclick));// set here your backgournd to button
                }else {
                    signUpbtn.setBackground(ContextCompat.getDrawable(SignUp.this, R.drawable.signup));// set here your backgournd to button
                }
            }
        });

        editTextPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editTextEmail.getText().toString()) && !TextUtils.isEmpty(editTextName.getText().toString()) &&!TextUtils.isEmpty(editTextPassword1.getText().toString()) && !TextUtils.isEmpty(editTextPassword2.getText().toString()) ) {
                    signUpbtn.setBackground(ContextCompat.getDrawable(SignUp.this, R.drawable.signup_onclick));// set here your backgournd to button
                }else {
                    signUpbtn.setBackground(ContextCompat.getDrawable(SignUp.this, R.drawable.signup));// set here your backgournd to button
                }
            }
        });

        editTextPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editTextEmail.getText().toString()) && !TextUtils.isEmpty(editTextName.getText().toString()) &&!TextUtils.isEmpty(editTextPassword1.getText().toString()) && !TextUtils.isEmpty(editTextPassword2.getText().toString()) ) {
                    signUpbtn.setBackground(ContextCompat.getDrawable(SignUp.this, R.drawable.signup_onclick));// set here your backgournd to button
                }else {
                    signUpbtn.setBackground(ContextCompat.getDrawable(SignUp.this, R.drawable.signup));// set here your backgournd to button
                }
            }
        });

    }

}
