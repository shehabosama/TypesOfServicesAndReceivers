package com.android.servicesproject.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class OrderReceiver2 extends BroadcastReceiver {
    public static final String TAG = "OrderReceiver2";
    @Override
    public void onReceive(final Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(10000);
                int resultCode = pendingResult.getResultCode();
                String resultData = pendingResult.getResultData();
                Bundle resultExtra = pendingResult.getResultExtras(true);
                String stringExtras = resultExtra.getString("stringExtra");

                resultCode++;
                stringExtras+="->OR2";

                final String toastText =
                        "OR2\n"+
                                "resultCode:"+resultCode+"\n"+
                                "resultData:"+resultData+"\n"+
                                "stringExtra:"+stringExtras;

                Log.d(TAG, "onReceive: "+ toastText);





                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, toastText , Toast.LENGTH_SHORT).show();

                    }
                });


                resultData = "OR2";
                resultExtra.putString("stringExtra",stringExtras);
                pendingResult.setResult(resultCode,resultData,resultExtra);
                pendingResult.finish();
            }
        }).start();



    }
}
