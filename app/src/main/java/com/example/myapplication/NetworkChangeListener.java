package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!NetworkDetails.isConnectedToInternet (context)){
            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.activity_network_change_listener, null);
            ad.setView(view);
            AlertDialog alertDialog = ad.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            AppCompatButton btnRetry = view.findViewById(R.id.acbtnnCheckInternetConnectionRetry);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    onReceive(context, intent);
                }
            });
        }
            else{
            Toast.makeText(context, "Your Internet is Connected", Toast.LENGTH_SHORT).show();


            }
        }
    }


