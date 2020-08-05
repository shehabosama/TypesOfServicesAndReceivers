package com.android.servicesproject.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.servicesproject.ui.ForegroundJobIntentActivity;

public class MyInternetConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Toast.makeText(context, "Boot Completed on this activity", Toast.LENGTH_SHORT).show();
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null

     /**   boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
        if(noConnectivity){
            Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "not connected", Toast.LENGTH_SHORT).show();
        }*/

        if (netInfo != null && netInfo.isConnected()) {
            Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "disconnected", Toast.LENGTH_SHORT).show();
        }
    }
}

