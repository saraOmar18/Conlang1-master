package com.example.alhanoufaldawood.conlang;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


        public static final int left_msg = 0;
        public static final int right_msg = 1;

        private Context mContext;
        private List<ChatMessage> list;

        FirebaseUser sender;

        public MessageAdapter(Context mContext, List<ChatMessage> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @NonNull
        @Override
        public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if(i == right_msg){
                View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,viewGroup,false);
                return new MessageAdapter.ViewHolder(view);
            }else {
                View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,viewGroup,false);
                return new MessageAdapter.ViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {


            ChatMessage msg = list.get(i);

            viewHolder.msgShow.setText(msg.getMessageText());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }




        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView msgShow;

            public ViewHolder(View itemView) {
                super(itemView);

                msgShow = itemView.findViewById(R.id.show_massage);
            }
        }

        @Override
        public int getItemViewType(int position) {
            sender = FirebaseAuth.getInstance().getCurrentUser();

            if(list.get(position).getMessageSender().equals(sender.getUid())){
                return right_msg;
            }else {
                return left_msg;
            }
        }
    }



