package com.android.servicesproject.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.servicesproject.R;
import com.android.servicesproject.receivers.AlarmReceiver;

import java.text.DateFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity  {

    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        mTextView = findViewById(R.id.textView);
        Button buttonTimePicker = findViewById(R.id.button_timepicker);
        Button buttonCancel = findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,i);
                        c.set(Calendar.MINUTE,i1);
                        c.set(Calendar.SECOND,0);

                        updateTimeText(c);
                        startAlarm(c);
                    }
                }, 0,0,false);
                timePicker.show();
            }
        });
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm Canceled");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

    private void updateTimeText(Calendar calendar) {

        String timeText = "Alarm set for : ";
        timeText+= DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        mTextView.setText(timeText);
    }



}