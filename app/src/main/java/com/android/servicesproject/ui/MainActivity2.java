package com.android.servicesproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.IntentFilter;
import android.os.Bundle;

import com.android.servicesproject.R;
import com.android.servicesproject.receivers.OrderReceiver1;

public class MainActivity2 extends AppCompatActivity {

    OrderReceiver1 orderReceiver1 = new OrderReceiver1();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        IntentFilter filter = new IntentFilter("com.android.services.EXAMPLE_ACTION");
        registerReceiver(orderReceiver1,filter, Manifest.permission.VIBRATE,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(orderReceiver1);
    }
}