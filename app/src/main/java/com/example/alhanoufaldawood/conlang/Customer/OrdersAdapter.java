package com.example.alhanoufaldawood.conlang.Customer;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alhanoufaldawood.conlang.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrdersAdapter extends ArrayAdapter<ServiceRequest> {



    private Activity context;
    private List<ServiceRequest> orderList;

    public OrdersAdapter( Activity context, List<ServiceRequest> orderList) {

        super(context, R.layout.order, orderList);

        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.order, null, false);

        ServiceRequest serviceRequest = orderList.get(position);


        TextView name = (TextView) listViewItem.findViewById(R.id.orderNumber);
        name.setText("Order"+serviceRequest.getorderNo()+" with "+ serviceRequest.gettranslatorName());
        name.setTextColor(Color.parseColor("Black"));

        TextView language = (TextView) listViewItem.findViewById(R.id.status);
        language.setText(serviceRequest.getstatus());

        if (serviceRequest.getstatus().equals("Sent...")){
            language.setTextColor(Color.parseColor("#3cbbcd"));
        }
        if (serviceRequest.getstatus().contains("New offer")){
            language.setTextColor(Color.parseColor("Blue"));
        }
        if (serviceRequest.getstatus().contains("Active")){
            language.setTextColor(Color.parseColor("#63b521"));
        }
        if (serviceRequest.getstatus().contains("Rejected")){
            language.setTextColor(Color.parseColor("Red"));

        } if (serviceRequest.getstatus().contains("Closed")){
            language.setTextColor(Color.parseColor("Grey"));
        }


        listViewItem.setBackgroundResource(R.drawable.divider);

        CircleImageView imageView = (CircleImageView) listViewItem.findViewById(R.id.imageView9);
        Glide.with(context)

                .load(serviceRequest.getTranslatorImage())
                .into(imageView);






        return listViewItem;

    }
}


