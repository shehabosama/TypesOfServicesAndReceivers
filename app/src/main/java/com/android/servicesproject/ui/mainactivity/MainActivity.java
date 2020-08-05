package com.android.servicesproject.ui.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.servicesproject.R;
import com.android.servicesproject.repository.MainActivityRepository;
import com.android.servicesproject.services.MyBoundServices;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MyBoundServices myBoundServices;
    private MainActivityViewModel mainActivityViewModel;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progresss_bar);
        mTextView = findViewById(R.id.text_view);
        mButton = findViewById(R.id.toggle_updates);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleUpdate();
            }
        });

        mainActivityViewModel.getIsProgressUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(final Boolean aBoolean) {
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(aBoolean){
                            if(mainActivityViewModel.getMyBinder().getValue() != null){
                                if(myBoundServices.getmProgress() == myBoundServices.getmMaxValue()){
                                    mainActivityViewModel.setIsUpdating(false);
                                }
                                mProgressBar.setProgress(myBoundServices.getmProgress());
                                mProgressBar.setMax(myBoundServices.getmMaxValue());
                                String progress = String.valueOf(100 * myBoundServices.getmProgress()/myBoundServices.getmMaxValue())+"%";
                                mTextView.setText(progress);
                                handler.postDelayed(this,100);
                            }
                        }else{

                            handler.removeCallbacks(this);
                        }
                    }
                };

                if(aBoolean){
                    mButton.setText("Pause");
                    handler.postDelayed(runnable ,100);

                }else{

                    if(myBoundServices.getmProgress() == myBoundServices.getmMaxValue()){
                        mButton.setText("Restart");
                    }else{
                        mButton.setText("Start");
                    }
                }
            }
        });
        mainActivityViewModel.getMyBinder().observe(this, new Observer<MyBoundServices.MyBinder>() {
            @Override
            public void onChanged(MyBoundServices.MyBinder myBinder) {
                if(myBinder !=null){
                    Log.d(TAG,"onChanged : connected to service");

                    myBoundServices = myBinder.getService();
                }else{
                    Log.d(TAG,"onChanged : unbound from service");

                    myBoundServices = null;
                }
            }
        });
        mainActivityViewModel.getStringData().observe(this, new Observer<MainActivityRepository>() {
            @Override
            public void onChanged(MainActivityRepository s) {
                mTextView.append(s.getStringData(MainActivity.this));
            }
        });
    }

    private void toggleUpdate(){
        if(myBoundServices !=null){
            if(myBoundServices.getmProgress() == myBoundServices.getmMaxValue()){
                myBoundServices.resetTask();
                mButton.setText("Start");
            }else{
                if(myBoundServices.getmIsPaused()){
                    myBoundServices.unPausePretendLongRunningTask();
                    mainActivityViewModel.setIsUpdating(true);
                }else{
                    myBoundServices.pausePretendLongRunningTask();
                    mainActivityViewModel.setIsUpdating(false);
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mainActivityViewModel.getMyBinder() !=null){
            unbindService(mainActivityViewModel.getServiceConnection());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService();
    }
    private void startService(){
        Intent serviceIntent = new Intent(this,MyBoundServices.class);
        startService(serviceIntent);
        bindService();
    }
    private void bindService(){
        Intent serviceIntent = new Intent(this, MyBoundServices.class);
        bindService(serviceIntent,mainActivityViewModel.getServiceConnection(), Context.BIND_AUTO_CREATE);
    }
}