package com.android.servicesproject.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.ScriptC;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.servicesproject.R;
import com.android.servicesproject.services.ExampleJobService;
import com.android.servicesproject.services.MyMusicService;

public class PlayMusicActivity extends AppCompatActivity {

    private Button btnStart,btnStop;
    private static final int JOB_ID = 1001;
    private static final long REFRESH_INTERVAL  = 5 * 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
              //  stopService(new Intent(PlayMusicActivity.this, MyMusicService.class));
                @SuppressLint("JobSchedulerService") ComponentName componentName = new ComponentName(PlayMusicActivity.this, ExampleJobService.class);

                JobInfo  jobInfo;
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                         jobInfo = new JobInfo.Builder(123, componentName)
//                            .setMinimumLatency(5000)
//                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                            .setPersisted(true)
//                            .build();
//                } else {
//                      jobInfo = new JobInfo.Builder(123, componentName)
//                            .setPeriodic(5000)
//                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                            .setPersisted(true)
//                            .build();
//                }

                // 5 seconds

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                            .setMinimumLatency(REFRESH_INTERVAL)
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                            .build();
                } else {
                    jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                            .setPeriodic(REFRESH_INTERVAL)
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                            .build();
                }

//                JobInfo info = new JobInfo.Builder(123,componentName)
//                        .setRequiresCharging(true)
//                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                        .setPersisted(true)
//                        .setPeriodic(15*60*1000)
//                        .build();
                JobScheduler scheduler  = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                int reusltConde = scheduler.schedule(jobInfo);
                if(reusltConde == JobScheduler.RESULT_SUCCESS){
                    Log.d("PlayMusicActivity", "onClick: JobScheduler Success");
                }else{
                    Log.d("PlayMusicActivity", "onClick: JobScheduler failed");

                }
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  startService(new Intent(PlayMusicActivity.this, MyMusicService.class));
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    JobScheduler scheduler  = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                    scheduler.cancel(123);
                    Log.d("PlayMusicActivity", "onClick: JobScheduler cancelled");


                }

            }
        });
    }
}