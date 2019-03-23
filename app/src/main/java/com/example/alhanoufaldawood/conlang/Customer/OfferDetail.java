package com.example.alhanoufaldawood.conlang.Customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alhanoufaldawood.conlang.Order;
import com.example.alhanoufaldawood.conlang.R;
import com.example.alhanoufaldawood.conlang.Translator.PublicRequests;
import com.example.alhanoufaldawood.conlang.Translator.pdf_viewer;
import com.example.alhanoufaldawood.conlang.Translator.requestsOffers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class OfferDetail extends AppCompatActivity {

    private TextView mTextNote;
    private TextView mTextField;
    private TextView mTextPrice;
    private TextView mTextDeadLine;

    //private PDFViewCtrl mPdfViewCtrl;
    private Button mTextDocument;
    private Button mAccept;
    private Button mdecline;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    DatabaseReference ref;

    private requestsOffers offer;
    private ProgressDialog progressDialog;
    private boolean status ;
    String key ;
    private Order order;
    PublicRequests publicRequests;
    private String url;
    DatabaseReference ref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+"Offer details"+" </font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signin_actionbar, null));
        // set the view now
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent myData = getIntent();
        key=myData.getStringExtra("key");
        ref2 = FirebaseDatabase.getInstance().getReference("requestsOffers");


        initElement();
        createOrder();



    }
    private void initElement() {

        mTextDocument = (Button) findViewById(R.id.document);

        mTextField =(TextView) findViewById(R.id.field);
        mTextPrice =(TextView) findViewById(R.id.price);
        mTextDeadLine =(TextView) findViewById(R.id.deadLine);
        mTextNote=(TextView) findViewById(R.id.note);

        mAccept=(Button) findViewById(R.id.Accept);
        mdecline=(Button) findViewById(R.id.Decline);
        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        //user = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        Intent myData = getIntent();
        key=myData.getStringExtra("key");
        String deadLine=myData.getStringExtra("deadLine");
        String documentName =myData.getStringExtra("documentName");
        String price=myData.getStringExtra("price");
        String customerId=myData.getStringExtra("customerId");
        offer = new requestsOffers(deadLine,documentName,price,customerId,"","","","","","","","");
        status=false;

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref2.child(key).child("customerAcceptanceStatus").setValue("going to payment");
                Intent intent = new Intent(OfferDetail.this,PayClass.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });



    }

    private void createOrder() {

        ref2.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             //   for (DataSnapshot childSnapShot : dataSnapshot.getChildren())
               // {
                   // Toast.makeText(OfferDetail.this, key+dataSnapshot.getKey()+"here", Toast.LENGTH_SHORT).show();
                    if (dataSnapshot.getKey().equals(key)) {

                        publicRequests = dataSnapshot.getValue(PublicRequests.class);

                       // url = publicRequests.getfileURL();
                      //  order = new Order(publicRequests.getcustomerID(), publicRequests.gettranslatorID(), offer.getPrice(), offer.getDeadline(), " ", "false", publicRequests.getFrom(), publicRequests.getto(), publicRequests.getfield());
                        mTextPrice.setText(offer.getPrice());
                        mTextDeadLine.setText(offer.getDeadline());
                        mTextNote.setText(offer.getOfferComments());
                        mTextField.setText(publicRequests.getfield());


                    } else {
                       // Toast.makeText(OfferDetail.this, key, Toast.LENGTH_SHORT).show();
                    }
              //  }

                displayData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void displayData() {


        //see your Document
        mTextDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OfferDetail.this, "loading..", Toast.LENGTH_SHORT).show();
                Context context = OfferDetail.this;
                Class homeClass = pdf_viewer.class; // where to go
                Intent intent = new Intent(context,homeClass);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        // to accept offer

        mdecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //status=false;
               // addToList(offer);
               // deleteOffer(offer);
            }
        });
    }


    private void addToList(final requestsOffers offer) {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        int count=28;
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        if(status)
            order.setStatus("Rejected");
        else
            order.setStatus("Accepted");
        FirebaseDatabase.getInstance().getReference("Order")
                .child(builder.toString()).setValue(order);
    }

   /* private void deleteOffer(requestsOffers offer) {
        // delete offer from offer list via user email
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("requestsOffers");
        ref.child(key).removeValue();

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("PublicRequests");
        ref2.child(key).removeValue();

        goToHome();
    }*/


   /* private void goToHome() {
        Toast.makeText(OfferDetail.this, "see you later", Toast.LENGTH_SHORT).show();
        Context context = OfferDetail.this;
        Class homeClass = PayClass.class; // where to go
        Intent intent = new Intent(context,homeClass);
        startActivity(intent);

    }*/
   /* private void checkOfpayment(){

        Intent intent = getIntent();
        boolean isPaymentCorrectly = intent.getBooleanExtra("isPaymentCorrectly",false);
        if(isPaymentCorrectly){
            progressDialog.show();
            addToList(offer);
        }else {
            Toast.makeText(OfferDetail.this, "make payment first", Toast.LENGTH_LONG).show();
        }
    }*/

  /*  private void goToPaymenet() {
        Class buymenetClass = PayClass.class;
        Context context = OfferDetail.this;
        String price=offer.getPrice();
        Intent intent = new Intent(context,buymenetClass);
        intent.putExtra("price",price);
        startActivity(intent);
    }*/

}
