package com.example.alhanoufaldawood.conlang;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class ReportIssueDialog extends Activity{


  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.4));
    }
  }
