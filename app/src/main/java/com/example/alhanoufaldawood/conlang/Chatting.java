package com.example.alhanoufaldawood.conlang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chatting extends AppCompatActivity {

    MessageAdapter messageAdapter;
    List<ChatMessage> messages;
    RecyclerView recyclerView;
    DatabaseReference ref1;

    FirebaseUser user;
    String userId ;
    static String id;
    static String orderNo;
    static String name;
    static String classname = "Chat";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);


            ImageButton sendMsg;
            final EditText message;

            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            orderNo = intent.getStringExtra("orderNo");
            name = intent.getStringExtra("name");



            getSupportActionBar().setTitle(Html.fromHtml("<font color='#707070'>"+name+" </font>"));
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar, null));
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


            sendMsg = findViewById(R.id.btn_send);
            message=  findViewById(R.id.text_send);

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);









        user = FirebaseAuth.getInstance().getCurrentUser();
            userId= user.getUid();


            sendMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String msg;
                    msg = message.getText().toString();

                    if (!msg.equals("")) {
                        sendMessage(userId, id, msg,orderNo);
                    } else{

                        Toast.makeText(Chatting.this, "empty meassge", Toast.LENGTH_LONG).show();
                        //Toast.makeText(ChattingActivity.this, childId, Toast.LENGTH_LONG).show();


                    }
                    message.setText("");

                }
            });

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("children").child("childId");


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    // Child child = dataSnapshot.getValue(Child.class);
                    //Toast.makeText(ChattingActivity.this, childId+"hello", Toast.LENGTH_LONG).show();
                    readMsg(user.getUid(),id);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } // on create




        private void sendMessage (String sender , String resever, String message, String orderNo){

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            ChatMessage msg = new ChatMessage(message,sender,resever,orderNo);


            ref.child("Chats").push().setValue(msg);


        }

        private void readMsg (final String sender , final String resever){

            messages = new ArrayList<>() ;

            ref1 = FirebaseDatabase.getInstance().getReference("Chats");

            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    messages.clear();

                    for (DataSnapshot msgSnapShot : dataSnapshot.getChildren()) {
                        if(msgSnapShot != null){
                            ChatMessage msg = msgSnapShot.getValue(ChatMessage.class);



                            if ((msg.getMessageRecever().equals(sender)&& msg.getMessageSender().equals(resever))||( msg.getMessageRecever().equals(resever)&&msg.getMessageSender().equals(sender))){

                                if (msg.getOrderNo().equals(orderNo)) {
                                    messages.add(msg);

                                    messageAdapter = new MessageAdapter(Chatting.this, messages);
                                    recyclerView.setAdapter(messageAdapter);

                                } }}}



                }

                @Override
                public void onCancelled(DatabaseError databaseError) { }

            });
        }
    }

