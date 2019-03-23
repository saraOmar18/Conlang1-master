package com.example.alhanoufaldawood.conlang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.example.alhanoufaldawood.conlang.Customer.LoginCustomer;
import com.example.alhanoufaldawood.conlang.Translator.LoginTranslator;


public class MainActivity extends Activity {
    private Button translator;
    private Button customer;
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElement();



    }

    private void initElement() {
        translator=(Button) findViewById(R.id.transator);
        customer=(Button) findViewById(R.id.customer);
        // for translator
        translator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translator.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.im_translator_onclick));// set here your backgournd to button

                intent= new Intent(MainActivity.this, LoginTranslator.class);
                startActivity(intent);
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.im_customer_onclick));// set here your backgournd to button

                intent= new Intent(MainActivity.this,LoginCustomer.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        translator.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.im_translator));
        customer.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.im_customer));// set here your backgournd to button


    }
}
