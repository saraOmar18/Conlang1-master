package com.example.alhanoufaldawood.conlang.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {

    private TextView profileName,profileEmail,profilePhone ;
    private Button btnEdit ;
    FirebaseAuth firebaseAuth;
    String Id ;
    DatabaseReference databaseref ;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileName =(TextView)findViewById(R.id.Name) ;
        profileEmail = (TextView)findViewById(R.id.Email) ;
        profilePhone = (TextView)findViewById(R.id.Phone) ;
        btnEdit=(Button)findViewById(R.id.Edit) ;



        firebaseAuth = FirebaseAuth.getInstance();
        databaseref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         String id = user.getUid();//easy to refer




        databaseref.child("Customers").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

//                String fname = dataSnapshot.child("Name").getValue().toString();
             //   String cemail = dataSnapshot.child("email").getValue().toString();
              //  String cphone = dataSnapshot.child("phone").getValue().toString();

                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText(userProfile.getname());
                profileEmail.setText(userProfile.getemail());
                profilePhone.setText(userProfile.getphone());
               /* profileName.setText(fname);
                profileEmail.setText(cemail);
                profilePhone.setText(cphone);*/



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });



        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, UpdateProfile.class);
                startActivity(i);
            }
        });


    }


}
