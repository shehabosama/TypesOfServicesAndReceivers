package com.android.servicesproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.servicesproject.R;
import com.android.servicesproject.services.ExampleService;
import com.android.servicesproject.services.MyInternetConnectionReceiver;

public class ForegroundActivity extends AppCompatActivity {
    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground);
        editTextInput = findViewById(R.id.edit_text_input);

    }
    public void startService(View v) {
        String input = editTextInput.getText().toString();
        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", input);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Create an IntentFilter instance.


    }

    @Override
    protected void onPause() {
        super.onPause();
        //  unregisterReceiver(myInternetConnectionReceiver);
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


    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);
    }
}