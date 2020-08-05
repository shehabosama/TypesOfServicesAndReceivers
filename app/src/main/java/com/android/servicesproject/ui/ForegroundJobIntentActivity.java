package com.android.servicesproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.servicesproject.R;
import com.android.servicesproject.receivers.CustomeBroadCastReceiverExample;
import com.android.servicesproject.services.ExampleJobIntentService;

public class ForegroundJobIntentActivity extends AppCompatActivity {
    private CustomeBroadCastReceiverExample customeBroadCastReceiverExample;

    private EditText editTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_job_intent);
        editTextInput = findViewById(R.id.edit_text_input);
       customeBroadCastReceiverExample = new CustomeBroadCastReceiverExample();
//        // Create an IntentFilter instance.
//        IntentFilter intentFilter = new IntentFilter();
//
//        // Add network connectivity change action.
//        intentFilter.addAction("com.andorid.servicesproject");
//
//        // Register the broadcast receiver with the intent filter object.
//        registerReceiver(customeBroadCastReceiverExample, intentFilter);
    }
    public void startService(View v) {
        String input = editTextInput.getText().toString();
        Intent serviceIntent = new Intent(this, ExampleJobIntentService.class);
        serviceIntent.putExtra("inputExtra", input);
        ExampleJobIntentService.enqueueWork(this,serviceIntent);
    }


    @Override
    protected void onStop() {
        super.onStop();
        //  unregisterReceiver(customeBroadCastReceiverExample);
    }

//    public class MyInternetConnectionReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo netInfo = cm.getActiveNetworkInfo();
//            //should check null because in airplane mode it will be null
//            showMsgBar(netInfo != null && netInfo.isConnected());
//
//        }
//    }

    /**
     *
     * @param isConnected
     * this boolean variable will return connectivity status
     */

}
