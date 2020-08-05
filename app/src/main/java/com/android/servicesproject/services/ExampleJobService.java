package com.android.servicesproject.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ExampleJobService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "job started");
        doBackgroundWork(jobParameters);
        return true;
    }

    private void doBackgroundWork(final JobParameters jobParameters) {
        Log.d(TAG, "doBackgroundWork: no thing hppind");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0 ;i <40 ; i++){
                    Log.d(TAG, "run: "+i);
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "job finished");
                jobFinished(jobParameters , false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "job stoped");
        jobCancelled = true;

        return true;
    }
}
