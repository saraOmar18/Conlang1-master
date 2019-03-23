package com.example.alhanoufaldawood.conlang.Customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TranslatorProfilePage extends AppCompatActivity {

    ImageView profilePic;
    TextView name;
    TextView bio;
    TextView langauge;
    TextView feild;
    TextView about;
    RatingBar ratingBar;

    String translatorId;
    Button requestServive;

    DatabaseReference ref;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator_profile_page);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar, null));
        Intent intent = getIntent();
        translatorId = intent.getStringExtra("translatorId");


        profilePic = (ImageView) findViewById(R.id.profilePic);
        ratingBar= (RatingBar)findViewById(R.id.ratingBar2);
        name = (TextView) findViewById(R.id.name);
        bio = (TextView) findViewById(R.id.bio);
        langauge = (TextView) findViewById(R.id.language);
        feild = (TextView) findViewById(R.id.feild);
        about = (TextView) findViewById(R.id.about);

        ratingBar.setNumStars(5);

       // profilePic.setImageResource(R.drawable.profilepic);

        requestServive = (Button) findViewById(R.id.request);

        ref = FirebaseDatabase.getInstance().getReference("Translators");


        ref.child(translatorId).addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {

                Translator translator = new Translator();

                translator = dataSnapshot.getValue(Translator.class);
                ratingBar.setNumStars(5);
                ratingBar.setRating((float) translator.getRate());
                name.setText(translator.getName());
                bio.setText(translator.getBio());
                langauge.setText(translator.getProvidedLanguage());
                feild.setText(translator.getField());
                Glide.with(TranslatorProfilePage.this)

                        .load(translator.getImage())
                        .into(profilePic);

                getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+translator.getName()+"'s profile"+" </font>"));



                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {


                    }


                }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        requestServive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TranslatorProfilePage.this, RequestService.class);
                i.putExtra("translatorId", translatorId);
                startActivity(i);
            }
        });

    }
}
