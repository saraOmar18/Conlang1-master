package com.example.alhanoufaldawood.conlang.Translator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class RegistrationRequestDialog extends AppCompatDialogFragment {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("THANK YOU!!")
                    .setMessage("You are very important to us, \n" +
                            "all information received will \n" +
                            "always remain confidential. \n"+
                            "We will contact you as soon as \n" +
                            "we review your request.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent register = new Intent(getActivity(), LoginTranslator.class);
                            startActivity(register);

                        }
                    });


            return builder.create();
        }



    }


