package com.example.alhanoufaldawood.conlang.Customer;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alhanoufaldawood.conlang.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TranslatorListAdapter extends ArrayAdapter<Translator> {




    private Activity context;
    private List<Translator> translatorList;

    public TranslatorListAdapter( Activity context, List<Translator> translatorList) {

       super(context, R.layout.translator, translatorList);

        this.context = context;
        this.translatorList = translatorList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.translator, null, false);

        Translator translator = translatorList.get(position);


        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        name.setText(translator.getName());
        //name.setTextColor(Color.parseColor("#1386C1"));
        name.setTextColor(Color.parseColor("Black"));


        TextView language = (TextView) listViewItem.findViewById(R.id.language);
        language.setText(translator.getLanguage());

       RatingBar rate = (RatingBar) listViewItem.findViewById(R.id.ratingBar);
       rate.setRating(translator.getRate());

        TextView feild = (TextView) listViewItem.findViewById(R.id.feild);
        feild.setText(translator.getField());

        listViewItem.setBackgroundResource(R.drawable.divider);
        //listViewItem.setBackgroundResource(R.color.white);
        ImageView icon = (ImageView) listViewItem.findViewById(R.id.icon);

        if (translator.getType().equals("office")){
            icon.setImageResource(R.drawable.office);
        }

        CircleImageView imageView = (CircleImageView) listViewItem.findViewById(R.id.imageView9);
        Glide.with(context)

              .load(translator.getImage())
                .into(imageView);




        return listViewItem;

    }}
