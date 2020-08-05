package com.android.servicesproject.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class OrderReceiver3 extends BroadcastReceiver {
    public static final String TAG = "OrderReceiver3";
    @Override
    public void onReceive(Context context, Intent intent) {
        int resultCode = getResultCode();
        String resultData = getResultData();
        Bundle resultExtra = getResultExtras(true);
        String stringExtras = resultExtra.getString("stringExtra");

        resultCode++;
        stringExtras+="->OR3";

        String toastText =
                "OR3\n"+
                        "resultCode:"+resultCode+"\n"+
                        "resultData:"+resultData+"\n"+
                        "stringExtra:"+stringExtras;

        Toast.makeText(context, toastText , Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReceive: "+ toastText);


        resultData = "OR3";
        resultExtra.putString("stringExtra",stringExtras);
        setResult(resultCode,resultData,resultExtra);
    }
}
