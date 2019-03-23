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

public class Translator_orders_adapter extends ArrayAdapter<ServiceRequest> {

    private Activity context;
    private List<ServiceRequest> orderList;

    public Translator_orders_adapter( Activity context, List<ServiceRequest> orderList) {

        super(context, R.layout.translator_order_item, orderList);

        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.order, null, false);


        ServiceRequest serviceRequest = orderList.get(position);

        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageView9);
        imageView.setImageResource(R.drawable.profilepic);

        TextView name = (TextView) listViewItem.findViewById(R.id.orderNumber);
        name.setText("Order"+serviceRequest.getorderNo()+" for "+ serviceRequest.getname());
        name.setTextColor(Color.parseColor("Black"));

        TextView language = (TextView) listViewItem.findViewById(R.id.status);
        language.setText(serviceRequest.gettranslatorStatus());


        if (serviceRequest.gettranslatorStatus().equals("Active")){
            language.setTextColor(Color.parseColor("#63b521"));
        }

         if (serviceRequest.getstatus().contains("Closed")){
            language.setTextColor(Color.parseColor("Grey"));
        }


        listViewItem.setBackgroundResource(R.drawable.divider);

        if(position %4 == 0){
            imageView.setImageResource(R.drawable.profilepic);

        }

        else if (position%4 ==1){
            imageView.setImageResource(R.drawable.profilepic2);

        }else if (position%4 == 2){
            imageView.setImageResource(R.drawable.profilepic3);

        }else if (position %4== 3){
            imageView.setImageResource(R.drawable.profilepic4);

        }






        return listViewItem;

    }
}


