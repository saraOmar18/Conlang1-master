package com.example.alhanoufaldawood.conlang.Translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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


public class Translator_requests_fragments extends Fragment {

    ListView listViewCustomRequest;
    ListView listViewPublicRequest;
    DatabaseReference ref;

    List<ServiceRequest> customRequestList;
    List<ServiceRequest> publicRequestList;

    TextView no;
    TextView publicR;


    Activity customContext;
    FirebaseUser translator;
    public static String  key;
    String st;
    String  currentTranslator;
    public Translator_requests_fragments() {
        // Required empty public constructor

    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_translator_requests_fragments, container, false);

        customRequestList = new ArrayList<>();
        publicRequestList = new ArrayList<>();
        listViewCustomRequest = (ListView) myFragmentView.findViewById(R.id.custom_list);
        listViewPublicRequest = (ListView) myFragmentView.findViewById(R.id.listView2);

        publicR =(TextView) myFragmentView.findViewById(R.id.history);
        publicR.setVisibility(View.GONE);
        no =(TextView) myFragmentView.findViewById(R.id.No);

        customContext = getActivity();

        ref = FirebaseDatabase.getInstance().getReference("Orders");

        //to get current translator id.
        translator = FirebaseAuth.getInstance().getCurrentUser();
        currentTranslator =translator.getUid();

        listViewCustomRequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long arg) {
                ServiceRequest custom_request = customRequestList.get(pos);

                //CustomRequests customRequests = new CustomRequests();
                ref.orderByChild("orderNo").equalTo(custom_request.getorderNo()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                            key = childSnapShot.getKey();
                            if (getActivity() != null) {
                            Intent intent = new Intent(getActivity(), customDetails.class);

                                intent.putExtra("key", key);
                                startActivity(intent);
                            }
                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });





        return myFragmentView;
    }

    public void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                customRequestList.clear();

                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    ServiceRequest Crequest = childSnapShot.getValue(ServiceRequest.class);

                    String orderTranslator = Crequest.gettranslatorID();

                    if(Crequest.gettranslatorID() == null)
                    {

                        publicRequestList.add(Crequest);
                        publicR.setVisibility(View.VISIBLE);

                    }
                    else if ((currentTranslator.equals(orderTranslator)) && !(Crequest.gettranslatorStatus().equals("Active")) && !(Crequest.gettranslatorStatus().contains("Closed"))) {

                        no.setVisibility(View.GONE);
                        if ((Crequest.getorderType().equals("Custom"))) {
                            customRequestList.add(Crequest);
                        }


                    }
                    Custom_request_adapter adapter = new Custom_request_adapter(customContext, customRequestList);
                    listViewCustomRequest.setAdapter(adapter);


                    Public_request_adapter adapter1 = new Public_request_adapter(customContext, publicRequestList);
                    listViewPublicRequest.setAdapter(adapter1);

                    Utility.setListViewHeightBasedOnChildren(listViewCustomRequest);
                    Utility.setListViewHeightBasedOnChildren(listViewPublicRequest);

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    public void onResume(){
        super.onResume();

        // Set title bar
        ((TranslatorHome) getActivity()).setActionBarTitle("Requests");

    }
}
