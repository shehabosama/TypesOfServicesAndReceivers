package com.android.servicesproject.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.servicesproject.R;
import com.android.servicesproject.ui.mainactivity.MainActivity;

import static com.android.servicesproject.other.App.CHANNEL_ID;

public class ExampleService extends Service {
    MediaPlayer mediaPlayer;
    private MyInternetConnectionReceiver myInternetConnectionReceiver;

    @Override
        public void onCreate() {
            super.onCreate();
        IntentFilter intentFilter = new IntentFilter();

        // Add network connectivity change action.
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        // Register the broadcast receiver with the intent filter object.
        startBraodCastReceiver();
        registerReceiver(myInternetConnectionReceiver, intentFilter);
        }
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            String input = intent.getStringExtra("inputExtra");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example Service")
                    .setContentText(input)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notification);
            //do heavy work on a background thread
            //stopSelf();
            return START_NOT_STICKY;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mediaPlayer.stop();
        }
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        public void startBraodCastReceiver(){
            myInternetConnectionReceiver = new MyInternetConnectionReceiver();
        }
    }