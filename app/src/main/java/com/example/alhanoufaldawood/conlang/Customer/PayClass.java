package com.example.alhanoufaldawood.conlang.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PayClass extends AppCompatActivity {

    Button pay;

    DatabaseReference order;
    DatabaseReference order1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_class);


        Intent myData = getIntent();
        final String offerId=myData.getStringExtra("offerId");
        String orderId = myData.getStringExtra("orderId");

        Toast.makeText(PayClass.this, orderId, Toast.LENGTH_SHORT).show();


        order = FirebaseDatabase.getInstance().getReference("Orders").child(orderId);



        pay = (Button) findViewById(R.id.pay);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                order.child("offerId").setValue(offerId);
                order.child("status").setValue("Active");
                order.child("translatorStatus").setValue("Active");

               // Intent i = new Intent(PayClass.this,CustomerHome.class);
                 //           startActivity(i);





            }
        });
    }
}
