package com.example.alhanoufaldawood.conlang.Customer;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alhanoufaldawood.conlang.R;

import java.util.List;

  public class HistoryAdapter  extends ArrayAdapter<ServiceRequest> {

    private Activity context;
    private List<ServiceRequest> orderList;

    public HistoryAdapter( Activity context, List<ServiceRequest> orderList) {

        super(context, R.layout.order_history, orderList);

        this.context = context;
        this.orderList = orderList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.order_history, null, false);

        ServiceRequest serviceRequest = orderList.get(position);

        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageView9);
        Glide.with(context)

                .load(serviceRequest.getTranslatorImage())
                .into(imageView);

        TextView name = (TextView) listViewItem.findViewById(R.id.orderNumber);
        name.setText("Order"+serviceRequest.getorderNo()+" with "+ serviceRequest.gettranslatorName());
        name.setTextColor(Color.parseColor("Black"));

        TextView language = (TextView) listViewItem.findViewById(R.id.status);
        language.setText(serviceRequest.getstatus());

        if (serviceRequest.getstatus().equals("Sent to the translator...")){
            language.setTextColor(Color.parseColor("#3cbbcd"));
        }
        if (serviceRequest.getstatus().contains("New offer")){
            language.setTextColor(Color.parseColor("Blue"));
        }
        if (serviceRequest.getstatus().equals("Active")){
            language.setTextColor(Color.parseColor("#63b521"));
        }
        if (serviceRequest.getstatus().equals("Rejected")){
            language.setTextColor(Color.parseColor("Red"));

        } if (serviceRequest.getstatus().contains("Closed")){
            language.setTextColor(Color.parseColor("Grey"));
        }


        listViewItem.setBackgroundResource(R.drawable.divider);







        return listViewItem;

    }
}



