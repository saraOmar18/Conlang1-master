package com.example.alhanoufaldawood.conlang.Translator;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class TranslatorOrderDetails extends AppCompatActivity {

    String orderId;

    DatabaseReference ref,ref2;
    TextView orderNumber, customerName, orderDate, from, to, field, comment;
    Button download , chat;
    TextView TComment,Tcomment,Price,price,Deadline,deadline;
    static String Id;
    static String orderNo;
    static String customerName1;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator_order_details);

        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Back"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar, null));
        // set the view now

        ref = FirebaseDatabase.getInstance().getReference("Orders");



        orderNumber = (TextView) findViewById(R.id.orderNumber);
        customerName = (TextView) findViewById(R.id.translatorName);
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

        download = (Button) findViewById(R.id.document);
        chat = (Button) findViewById(R.id.chat);
        chat.setBackgroundResource(R.drawable.nonempty_btn);
        chat.setVisibility(View.VISIBLE);


        ref.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               Id = dataSnapshot.child("customerID").getValue().toString();


                orderNo = dataSnapshot.child("orderNo").getValue().toString();
                orderNumber.setText(orderNo);

                customerName1 = dataSnapshot.child("name").getValue().toString();
                customerName.setText(customerName1);

                chat.setText("Talk to "+customerName1+"  ");
                chat.setTextColor(Color.WHITE);

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
                if (comment1.equals("")){
                    comment.setText("No comments");
                }


                    String offerId = dataSnapshot.child("offerId").getValue().toString();
                    String price1 = dataSnapshot.child("Offers").child(offerId).child("price").getValue().toString();
                    String deadline1 = dataSnapshot.child("Offers").child(offerId).child("deadline").getValue().toString();
                    String tcomment = dataSnapshot.child("Offers").child(offerId).child("comment").getValue().toString();

                    price.setText(price1);
                    deadline.setText(deadline1);
                    Tcomment.setText(tcomment);

                if (tcomment.equals("")){
                    Tcomment.setText("No comments");
                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TranslatorOrderDetails.this , Chatting.class);
                i.putExtra("id",Id);
                i.putExtra("orderNo",orderNo);
                i.putExtra("name",customerName1);
                startActivity(i);

            }
        });
    }

    private void downloadFile() {

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //String uriString = downRef.child(key).child("fileURL").toString();
        String uriString = ref.child(orderId).child("fileURL").toString();
        Uri url = Uri.parse(uriString);
        // Uri url = Uri.parse("https://firebasestorage.googleapis.com/v0/b/conlang-60a4d.appspot.com/o/NeedTranslationDocuments%2FHello..1551887633037?alt=media&token=44d723e5-ee63-466f-9c9a-4d66bb1c31e2");
        DownloadManager.Request request = new DownloadManager.Request(url);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long refrence=downloadManager.enqueue(request);
    }







}
