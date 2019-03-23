package com.example.alhanoufaldawood.conlang.Customer;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.Forget_password;
import com.example.alhanoufaldawood.conlang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

// NEEDS TO CHECK IF EMAIL IS NOT EXIST

public class LoginCustomer extends AppCompatActivity {


    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private DatabaseReference RefDatabase;
    private String userID, userType;
    private Button btnLogin;
    private Button signUp;
    private Button forgetPass;
    private static final String TAG = "MainActivity";




    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signin_actionbar, null));
        // set the view now
        setContentView(R.layout.activity_login_customer);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();





        inputEmail = (EditText) findViewById(R.id.Email);
        inputPassword = (EditText) findViewById(R.id.password);

        signUp = (Button) findViewById(R.id.SignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginCustomer.this, SignUp.class);
                startActivity(i);
            }
        });

        forgetPass =(Button) findViewById(R.id.ForgetPass);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginCustomer.this, Forget_password.class);
                startActivity(i);
            }
        });


        btnLogin = (Button) findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();


                // check if one of the field missing
                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Email address is required !");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Password is required !");
                    return;
                }
                // check if email contain @ for valid Email format
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    inputEmail.setError(" Incorrect Email format ");
                    inputEmail.requestFocus();
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginCustomer.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {


                                    try
                                    {
                                        throw task.getException();
                                    }
                                    // if user enters wrong email.
                                    catch (FirebaseAuthWeakPasswordException weakPassword)
                                    {
                                        Log.d(TAG, "onComplete: weak_password");
                                        inputPassword.setError("Invalid password");
                                        // TODO: take your actions!
                                    }
                                    // if user enters wrong password.
                                    catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                    {
                                        Log.d(TAG, "onComplete: malformed_email");
                                        inputPassword.setError("Invalid password");


                                        // TODO: Take your action
                                    }
                                    catch (FirebaseAuthUserCollisionException existEmail)
                                    {
                                        Log.d(TAG, "onComplete: exist_email");


                                        // TODO: Take your action
                                    }
                                    catch (Exception e)
                                    {
                                        Log.d(TAG, "onComplete: " + e.getMessage());
                                    }
                                }
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginCustomer.this, "login successfully", Toast.LENGTH_LONG).show();
                                    //get the current user Id
                                    FirebaseUser currentUser  = FirebaseAuth.getInstance().getCurrentUser();
                                    userID = currentUser.getUid();

                                    //according to their user id get the user type
                                    RefDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                                    RefDatabase.addValueEventListener(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            userType = dataSnapshot.child("type").getValue().toString();



                                            if (userType.equals("Customer")) {
                                                Intent i = new Intent(LoginCustomer.this, CustomerHome.class);
                                                startActivity(i);
                                                finish();


                                            } else {
                                                Toast.makeText(LoginCustomer.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(LoginCustomer.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                        }


                                    });

                                }
                            }

                        });
            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!(inputEmail.getText().toString()!=null) && !(inputPassword.getText().toString()!=null)) {
            btnLogin.setBackground(ContextCompat.getDrawable(LoginCustomer.this,R.drawable.signin_btn));
            return;
        }

        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(inputEmail.getText().toString()) && !TextUtils.isEmpty(inputPassword.getText().toString()) ) {
                    btnLogin.setBackground(ContextCompat.getDrawable(LoginCustomer.this, R.drawable.signin_btn_onclick));// set here your backgournd to button
                }else {
                    btnLogin.setBackground(ContextCompat.getDrawable(LoginCustomer.this, R.drawable.signin_btn));// set here your backgournd to button
                }

        }});

        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(inputEmail.getText().toString()) && !TextUtils.isEmpty(inputPassword.getText().toString()) ) {
                    btnLogin.setBackground(ContextCompat.getDrawable(LoginCustomer.this, R.drawable.signin_btn_onclick));// set here your backgournd to button
                }else {
                    btnLogin.setBackground(ContextCompat.getDrawable(LoginCustomer.this, R.drawable.signin_btn));// set here your backgournd to button
               }
            }
        });

    }

}
