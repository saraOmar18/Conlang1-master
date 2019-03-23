package com.example.alhanoufaldawood.conlang.Translator;

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
import android.widget.ListView;
import android.widget.TextView;

import com.example.alhanoufaldawood.conlang.Customer.ServiceRequest;
import com.example.alhanoufaldawood.conlang.Customer.Utility;
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

public class Translators_orders_fragment extends Fragment {

    ListView listViewOrders;
    ListView listViewHistory;
    DatabaseReference ref;

    TextView history;
    TextView no;

    List<ServiceRequest> ordersList;
    List<ServiceRequest> historyList;
    Activity context;
    FirebaseUser user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_translators_orders_fragment, container, false);

        ordersList = new ArrayList<>();
        historyList = new ArrayList<>();
        listViewOrders = (ListView) rootView.findViewById(R.id.orders);
        listViewHistory = (ListView) rootView.findViewById(R.id.listView2);

        history =(TextView) rootView.findViewById(R.id.history);
        history.setVisibility(View.GONE);
        no =(TextView) rootView.findViewById(R.id.No);

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

                            Intent intent = new Intent(getActivity(), TranslatorOrderDetails.class);
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

        return rootView;
    }


    public void onResume(){
        super.onResume();

        // Set title bar
        ((TranslatorHome) getActivity()).setActionBarTitle("Orders");

    }

    @Override
    public void onStart() {
        super.onStart();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ordersList.clear();

                for (DataSnapshot childSnapShot :dataSnapshot.getChildren()){

                    ServiceRequest serviceRequest=childSnapShot.getValue(ServiceRequest.class);


                    String translatorID = serviceRequest.gettranslatorID();
                    //Toast.makeText(getActivity(),customerId,Toast.LENGTH_LONG).show();

                    String userId;
                    userId=user.getUid();

                    if ( (translatorID.equals(userId)) && (serviceRequest.gettranslatorStatus().equals("Active"))  ) {
                        ordersList.add(serviceRequest);
                        no.setVisibility(View.GONE);

                    }
                    if (translatorID.equals(userId) && (serviceRequest.gettranslatorStatus().contains("Closed"))) {
                        historyList.add(serviceRequest);
                        history.setVisibility(View.VISIBLE);
                        no.setVisibility(View.GONE);

                    }
                }

                Translator_orders_adapter adapter = new Translator_orders_adapter(context , ordersList);
                listViewOrders.setAdapter(adapter);

                com.example.alhanoufaldawood.conlang.Translator.HistoryAdapter adapter1 = new com.example.alhanoufaldawood.conlang.Translator.HistoryAdapter(context, historyList);
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

