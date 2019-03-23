package com.example.alhanoufaldawood.conlang.Customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alhanoufaldawood.conlang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    ListView listViewOrders;
    ListView listViewHistory;
    DatabaseReference ref;

    TextView history;
    TextView no;



    List<ServiceRequest> ordersList;
    List<ServiceRequest> historyList;

    Activity context;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.orders_fragment, container, false);

        ordersList = new ArrayList<>();
        historyList = new ArrayList<>();
        listViewOrders = (ListView) myFragmentView.findViewById(R.id.listView1);
        listViewHistory = (ListView) myFragmentView.findViewById(R.id.listView2);


        history =(TextView) myFragmentView.findViewById(R.id.history);
        history.setVisibility(View.GONE);
        no =(TextView) myFragmentView.findViewById(R.id.No);

        context = getActivity();

        ref = FirebaseDatabase.getInstance().getReference("Orders");
        user = FirebaseAuth.getInstance().getCurrentUser();






        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               final ServiceRequest order = ordersList.get(position);

                ref.orderByChild("orderNo").equalTo(order.getorderNo()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                            String orderId = childSnapShot.getKey();

                            Intent intent = new Intent(getActivity(), OrderDetails.class);
                            intent.putExtra("orderId",orderId);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        });

        return myFragmentView;
    }


    public void onResume(){
        super.onResume();

        // Set title bar
        ((CustomerHome) getActivity()).setActionBarTitle("Orders");


    }



    @Override
    public void onStart() {
        super.onStart();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ordersList.clear();

               for (DataSnapshot childSnapShot :dataSnapshot.getChildren()) {

                   ServiceRequest serviceRequest = childSnapShot.getValue(ServiceRequest.class);


                   String customerId = serviceRequest.getcustomerID();

                   String userId;
                   userId = user.getUid();


                       if (customerId.equals(userId) && !(serviceRequest.getstatus().contains("Closed"))) {
                           ordersList.add(serviceRequest);
                           no.setVisibility(View.GONE);

                       }
                       else { //(customerId.equals(userId) && (serviceRequest.getstatus().contains("Closed")))
                           historyList.add(serviceRequest);
                           history.setVisibility(View.VISIBLE);
                           no.setVisibility(View.GONE);
                       }

                       }

                   OrdersAdapter adapter = new OrdersAdapter(context, ordersList);
                   listViewOrders.setAdapter(adapter);

                   HistoryAdapter adapter1 = new HistoryAdapter(context, historyList);
                   listViewHistory.setAdapter(adapter1);


                Utility.setListViewHeightBasedOnChildren(listViewOrders);
                Utility.setListViewHeightBasedOnChildren(listViewHistory);
                   }





            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}
