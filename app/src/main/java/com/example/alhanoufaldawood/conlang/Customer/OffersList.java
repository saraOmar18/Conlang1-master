package com.example.alhanoufaldawood.conlang.Customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OffersList extends AppCompatActivity {

    RecyclerView offerList;
    private ArrayList<Offer> offers;
    private DatabaseReference ref;
    private DatabaseReference ref2;


    static String  orderId;
    static String  offersCount;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_list);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar, null));


        Intent intent = getIntent();
        orderId = intent.getStringExtra("key");
        offersCount = intent.getStringExtra("offerNo");



        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        ref2 = database1.getReference("Orders").child(orderId);

        ref2.child("orderNo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String orderNo = dataSnapshot.getValue().toString();
                getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Order"+orderNo+"'s offers"+" </font>"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Orders").child(orderId).child("Offers");

        offers = new ArrayList<>();
        offerList = (RecyclerView) findViewById(R.id.offerList);
        offerList.setLayoutManager(new LinearLayoutManager(OffersList.this));


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                offers.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String key = item.getKey();
                    String translatorName = item.child("translatorName").getValue(String.class);
                    String price = item.child("price").getValue(String.class);
                    String comment = item.child("comment").getValue(String.class);
                    String deadline = item.child("deadline").getValue(String.class);

                    Offer offer = new Offer(deadline, price, comment, translatorName,key);
                    offers.add(offer);
                }
                setDataToList(offers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void RejectOffer(String key) {
        if (Integer.parseInt(offersCount) == 1) {
            ref2.child("status").setValue("Rejected");
        }
       ref2.child("translatorStatus").setValue("Rejected");
       ref.child(key).removeValue();
    }

    void AcceptOffer(String key) {
        Intent intent = new Intent(OffersList.this,PayClass.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("offerId",key);
        startActivity(intent);
    }

    void setDataToList(ArrayList<Offer> list) {
        OfferListAdapter adapter = new OfferListAdapter(OffersList.this, list, new OfferListAdapter.OnClick(){
            //@Override
             public void Reject(String key) {
              RejectOffer(key);
            }
            public void Accept(String key) {
                AcceptOffer(key);
            }
        });

        offerList.setAdapter(adapter);
    }


}
