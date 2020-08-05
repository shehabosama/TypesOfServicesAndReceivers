package com.android.servicesproject.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomeBroadCastReceiverExample extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       // Toast.makeText(context, "ERB triggered", Toast.LENGTH_SHORT).show();

        if("com.android.services.EXAMPLE_ACTION".equals(intent.getAction())){
           // String receivedText = intent.getStringExtra("com.andorid.servicesproject.EXTRA_TEXT");
            Toast.makeText(context, "I AM INSIDE THE RECEIVER ACTION", Toast.LENGTH_SHORT).show();
        }
    }
}
