package com.android.servicesproject.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.android.servicesproject.ui.ForegroundJobIntentActivity;

public class ExampleJobIntentService extends JobIntentService {
    public static final String TAG = "ExampleJobIntentService";
    public static void enqueueWork(Context context, Intent work){
        enqueueWork(context,ExampleJobIntentService.class,1234,work);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: ");

        String inputString = intent.getStringExtra("inputExtra");
        for (int i = 0; i <100 ; i++) {
            Log.d(TAG, "onHandleWork: "+ inputString + "-"+i );
            if(isStopped()){
                  return;
            }
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");


    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork: ");
        return super.onStopCurrentWork();
    }

//    private void registerScreenOffReceiver()
//    {
//        broadcastReceiver = new BroadcastReceiver()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent)
//            {
//                Log.d(TAG, "ACTION_SCREEN_OFF");
//                // do something, e.g. send Intent to main app
//
//                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo netInfo = cm.getActiveNetworkInfo();
//                //should check null because in airplane mode it will be null
//                new ForegroundJobIntentActivity().showMsgBar(netInfo != null && netInfo.isConnected());
//            }
//        };
//        Intent i = new Intent();
//        i.setAction("android.net.conn.CONNECTIVITY_CHANGE");
//        // i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
////        i.setComponent(
////                new ComponentName("PackageNameApp2","PackageNameApp2.MainActivity"));
//        sendBroadcast(i);
//        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
//        registerReceiver(broadcastReceiver, filter);
//    }
}
