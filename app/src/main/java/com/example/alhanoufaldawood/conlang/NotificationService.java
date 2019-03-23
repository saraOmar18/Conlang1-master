package com.example.alhanoufaldawood.conlang;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


import com.example.alhanoufaldawood.conlang.Customer.CustomerHome;
import com.example.alhanoufaldawood.conlang.Customer.ServiceRequest;
import com.example.alhanoufaldawood.conlang.Customer.Translator;
import com.example.alhanoufaldawood.conlang.Translator.TranslatorHome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotificationService extends Service {

    List<ServiceRequest> ordersList;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String[] userData = intent.getStringArrayExtra("Users");

        final DatabaseReference ref=  FirebaseDatabase.getInstance().getReference("Orders");

        Query q =ref.orderByChild("notify").equalTo(true);

        ordersList = new ArrayList<>();

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ordersList.clear();

                for (DataSnapshot childSnapShot :dataSnapshot.getChildren()){

                    ServiceRequest serviceRequest=childSnapShot.getValue(ServiceRequest.class);


                    if ((userData[1].toLowerCase().equals("Customer") && serviceRequest.getcustomerID().equals(userData[0]) ) ||
                            (userData[1].toLowerCase().equals("translator") && serviceRequest.gettranslatorID().equals(userData[0]) )) {

                        ordersList.add(serviceRequest);

                        ref.child(childSnapShot.getKey()).child("notify").setValue(false);                        }
                }

                if(!ordersList.isEmpty())
                    sendNotification("Order Status Update","You have "+ordersList.size()+" order/s updated",userData);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return super.onStartCommand(intent,flags,startId);
    }

    private void sendNotification(String title,String message,String[] userData)
    {
        Intent intent ;

        if ((userData[1].toLowerCase().equals("Customer") ))
            intent = new Intent(NotificationService.this, CustomerHome.class);
        else
            intent = new Intent(NotificationService.this, TranslatorHome.class);

        // Set the intent that will fire when the user taps the notification.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Conlang";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

        Notification notification =
                 new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setDefaults(NotificationCompat.DEFAULT_SOUND)
                        .setContentIntent(pendingIntent)
                        .setChannelId(CHANNEL_ID).build();



        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);


// Issue the notification.
            mNotificationManager.notify(notifyID, notification);


        }

    }
}
