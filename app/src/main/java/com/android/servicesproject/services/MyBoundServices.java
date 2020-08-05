package com.android.servicesproject.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyBoundServices extends Service {

    private static final String TAG = "MyBoundServices";
    private IBinder mBinder = new MyBinder();
    private Handler mHandler;
    private int mProgress,mMaxValue;
    private Boolean mIsPaused;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mProgress = 0;
        mIsPaused = true;
        mMaxValue  = 5000;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public class MyBinder extends Binder {
        public MyBoundServices getService(){
            return MyBoundServices.this;
        }
    }


    public void startPretendLongRunningTask(){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(mProgress>=mMaxValue || mIsPaused){
                    Log.d(TAG,"run : removing callbacks");
                    mHandler.removeCallbacks(this);
                    pausePretendLongRunningTask();

                }else{
                    Log.d(TAG,"run : progress "+ mProgress);
                    mProgress+=100;
                    mHandler.postDelayed(this,100);
                }
            }


        };
        mHandler.postDelayed(runnable,100);
    }
    public void pausePretendLongRunningTask() {
        mIsPaused = true;

    }

    public  void unPausePretendLongRunningTask(){
        mIsPaused = false;
        startPretendLongRunningTask();
    }

    public int getmProgress() {
        return mProgress;
    }

    public int getmMaxValue() {
        return mMaxValue;
    }

    public Boolean getmIsPaused() {
        return mIsPaused;
    }
    public void resetTask(){
        mProgress = 0;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }
}
