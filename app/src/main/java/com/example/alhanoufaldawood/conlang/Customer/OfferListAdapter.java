package com.example.alhanoufaldawood.conlang.Customer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.alhanoufaldawood.conlang.R;

import java.util.ArrayList;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.MyViewHolder> {

    Context context;
    OnClick onClick;
    ArrayList<Offer> offers;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView translatorName;
        public TextView price,deadline,comment;
        public TextView price2,deadline2,comment2;

        public Button accept,reject;
        public MyViewHolder(View v) {
            super(v);
             price=v.findViewById(R.id.descriptions);
             translatorName=v.findViewById(R.id.issueType);
             deadline=v.findViewById(R.id.reporterType);
             comment=v.findViewById(R.id.reporterId);
              accept = v.findViewById(R.id.accept);
              reject = v.findViewById(R.id.reject);
            price2=v.findViewById(R.id.descriptions2);
            deadline2=v.findViewById(R.id.reporterType2);
            comment2=v.findViewById(R.id.reporterId2);
        }
    }

    public OfferListAdapter(Context context, ArrayList<Offer> issues, OnClick onClick){
        this.context=context;
        this.offers=issues;
        this.onClick=onClick;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(context)
                .inflate(R.layout.offer_item, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        final Offer offer = offers.get(i);
        holder.translatorName.setText("Offer from "+offer.getTranslatorName());
        holder.price2.setText(offer.getPrice()+"SR");
        holder.deadline.setText(offer.getDeadLine());
          if (offer.getComment().equals("")){
        holder.comment2.setText("No comment");
         }else {
            holder.comment2.setText(offer.getComment());
        }
        holder.translatorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.price.getVisibility()==View.GONE){
                    holder.price.setVisibility(View.VISIBLE);
                    holder.accept.setVisibility(View.VISIBLE);
                    holder.reject.setVisibility(View.VISIBLE);
                    holder.comment.setVisibility(View.VISIBLE);
                    holder.deadline.setVisibility(View.VISIBLE);
                    holder.deadline2.setVisibility(View.VISIBLE);
                    holder.price2.setVisibility(View.VISIBLE);
                    holder.comment2.setVisibility(View.VISIBLE);

                }else {
                    holder.price.setVisibility(View.GONE);
                    holder.accept.setVisibility(View.GONE);
                    holder.comment.setVisibility(View.GONE);
                    holder.reject.setVisibility(View.GONE);
                    holder.deadline.setVisibility(View.GONE);
                    holder.deadline2.setVisibility(View.GONE);
                    holder.price2.setVisibility(View.GONE);
                    holder.comment2.setVisibility(View.GONE);
                }
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.Reject(offer.getKey());
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.Accept(offer.getKey());
            }
        });

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }
    public interface OnClick{
        void  Reject(String key);
        void  Accept(String key);
    }
}
