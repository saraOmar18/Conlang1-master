package com.example.alhanoufaldawood.conlang.Customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alhanoufaldawood.conlang.Chatting;
import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderDetails extends AppCompatActivity {

    DatabaseReference ref,ref2;
    TextView orderNumber, translatorName, orderDate, from, to, field, comment;
    Button preview , offers , chat;
    TextView TComment,Tcomment,Price,price,Deadline,deadline;

    static String orderNo;

    String orderId;
    static String translatorId;
    static String translatorName1;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar, null));

        final Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");


        ref = FirebaseDatabase.getInstance().getReference("Orders");



        orderNumber = (TextView) findViewById(R.id.orderNumber);
        translatorName = (TextView) findViewById(R.id.translatorName);
        orderDate = (TextView) findViewById(R.id.orderDate);
        from = (TextView) findViewById(R.id.from);
        to = (TextView) findViewById(R.id.to);
        field = (TextView) findViewById(R.id.field);
        comment = (TextView) findViewById(R.id.comment);

        TComment = (TextView) findViewById(R.id.TComment);
        Tcomment = (TextView) findViewById(R.id.Tcomment);
        Price = (TextView) findViewById(R.id.Price);
        price = (TextView) findViewById(R.id.price);
        deadline = (TextView) findViewById(R.id.deadline);
        Deadline = (TextView) findViewById(R.id.Deadline);

        TComment.setVisibility(View.GONE);
        Tcomment.setVisibility(View.GONE);
        Price.setVisibility(View.GONE);
        price.setVisibility(View.GONE);
        deadline.setVisibility(View.GONE);
        Deadline.setVisibility(View.GONE);


        offers = (Button) findViewById(R.id.offers);
        chat = (Button) findViewById(R.id.chat);





        preview = (Button) findViewById(R.id.document);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetails.this,OrderDetailsPreviewFile.class);
                intent.putExtra("key",orderId);
                startActivity(intent);
            }
        });


        ref.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                orderNo = dataSnapshot.child("orderNo").getValue().toString();
               orderNumber.setText(orderNo);

                translatorName1 = dataSnapshot.child("translatorName").getValue().toString();
                translatorName.setText(translatorName1);

                String date = dataSnapshot.child("orderDate").getValue().toString();
                orderDate.setText(date);

                String from1 = dataSnapshot.child("from").getValue().toString();
                from.setText(from1);

                String to1 = dataSnapshot.child("to").getValue().toString();
                to.setText(to1);

                String field1 = dataSnapshot.child("field").getValue().toString();
                field.setText(field1);

                String comment1 = dataSnapshot.child("comment").getValue().toString();
                comment.setText(comment1);

                translatorId = dataSnapshot.child("translatorID").getValue().toString();

                if (comment1.equals("")){
                    comment.setText("No comment");
                }

                String status = dataSnapshot.child("status").getValue().toString();
                if (status.equals("Active")){

                    TComment.setVisibility(View.VISIBLE);
                    Tcomment.setVisibility(View.VISIBLE);
                    Price.setVisibility(View.VISIBLE);
                    price.setVisibility(View.VISIBLE);
                    deadline.setVisibility(View.VISIBLE);
                    Deadline.setVisibility(View.VISIBLE);
                    offers.setVisibility(View.GONE);
                    chat.setVisibility(View.VISIBLE);
                    chat.setText("Talk to "+translatorName1+"     ");
                    chat.setBackgroundResource(R.drawable.nonempty_btn);
                    chat.setTextColor(Color.parseColor("white"));


                    String offerId = dataSnapshot.child("offerId").getValue().toString();
                    String price1 = dataSnapshot.child("Offers").child(offerId).child("price").getValue().toString();
                    String deadline1 = dataSnapshot.child("Offers").child(offerId).child("deadline").getValue().toString();
                    String tcomment = dataSnapshot.child("Offers").child(offerId).child("comment").getValue().toString();

                    price.setText(price1+" SR");
                    deadline.setText(deadline1);
                    Tcomment.setText(tcomment);

                    if (tcomment.equals("")){
                        Tcomment.setText("No comment");
                    }


                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child(orderId).child("Offers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 int x =0;

                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {


                    x++;
                }

                final String count = Integer.toString(x) ;
                if (x > 0) {
                    offers.setText(x + " New offers !");
                    offers.setTextColor(Color.parseColor("white"));
                    offers.setBackgroundResource(R.drawable.nonempty_btn);
                }else {
                    offers.setText("No offers");
                    offers.setBackgroundResource(R.drawable.empty_btn);


                }
                //if (x>0){
                //   ref.child(orderId).child("status").setValue("New offer");
               // }


                offers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderDetails.this,OffersList.class);
                        intent.putExtra("key",orderId);
                        intent.putExtra("offerNo",count);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderDetails.this,Chatting.class);
                i.putExtra("id",translatorId);
                i.putExtra("orderNo", orderNo);
                i.putExtra("name",translatorName1);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //ref2 = FirebaseDatabase.getInstance().getReference("Orders").child(orderId);
     // String offerkey = ref2.child("Offers").push().getKey();
      // ref2.child("Offers").child(offerkey).child("price").setValue("300");
      //  ref2.child("Offers").child(offerkey).child("deadline").setValue("11-2-2019");

    }
}
