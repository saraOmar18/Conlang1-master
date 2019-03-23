package com.example.alhanoufaldawood.conlang.Translator;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.alhanoufaldawood.conlang.Customer.ServiceRequest;
import com.example.alhanoufaldawood.conlang.R;

import java.util.List;

public class Public_request_adapter extends ArrayAdapter<ServiceRequest>  {

    private Activity context;
    private List<ServiceRequest> customRequestList;

    public Public_request_adapter(Activity context, List<ServiceRequest> customRequestList) {

        super(context, R.layout.custom_requests, customRequestList);

        this.context = context;
        this.customRequestList = customRequestList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.custom_requests, null, false);

        ServiceRequest request = customRequestList.get(position);


        TextView name = (TextView) listViewItem.findViewById(R.id.sender);


        name.setText("Request"+request.getorderNo()+" from "+request.getname());



        TextView field = (TextView) listViewItem.findViewById(R.id.translatorStatus);
        field.setText(request.gettranslatorStatus());

        // listViewItem.setBackgroundResource(R.drawable.t_list);
        if (request.gettranslatorStatus().equals("Offer sent to the customer")) {
            field.setTextColor(Color.parseColor("#3cbbcd"));
        }
        if (request.gettranslatorStatus().contains("New request")) {
            field.setTextColor(Color.parseColor("Blue"));
        }
        if (request.gettranslatorStatus().contains("Rejected")) {
            field.setTextColor(Color.parseColor("Red"));
        }

        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageView9);
        imageView.setImageResource(R.drawable.profilepic);

        if (position % 4 == 0) {
            imageView.setImageResource(R.drawable.profilepic);

        } else if (position % 4 == 1) {
            imageView.setImageResource(R.drawable.profilepic2);

        } else if (position % 4 == 2) {
            imageView.setImageResource(R.drawable.profilepic3);

        } else if (position % 4 == 3) {
            imageView.setImageResource(R.drawable.profilepic4);

        }


        return listViewItem;
    }


}

