package com.example.alhanoufaldawood.conlang.Customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    private EditText newName, newEmail, newPhone;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        newName = findViewById(R.id.NameUpdate);
        newEmail = findViewById(R.id.EmailUpdate);
        newPhone = findViewById(R.id.UpdatePhonet);
        save = findViewById(R.id.btnSave);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


       // final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Customers").child("591ZDiaEyfUWM73I8EP7UoxqwSd2");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
               newName.setText(userProfile.getname());
                newEmail.setText(userProfile.getemail());
                newPhone.setText(userProfile.getphone());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newName.getText().toString();
                String email = newEmail.getText().toString();
                String phone = newPhone.getText().toString();

                UserProfile userProfile = new UserProfile(name, email, phone);

                databaseReference.setValue(userProfile);
                finish();
            }


        });

    }
}

