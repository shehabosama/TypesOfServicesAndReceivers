package com.android.servicesproject.ui.mainactivity;


import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.servicesproject.repository.MainActivityRepository;
import com.android.servicesproject.services.MyBoundServices;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "MainActivityViewModel";
    private MutableLiveData<Boolean> mIsProgressUpdating = new MutableLiveData<>();
    private MutableLiveData<MyBoundServices.MyBinder> mBinder = new MutableLiveData<>();
    private MainActivityRepository mainActivityRepository = new MainActivityRepository();
    private MutableLiveData<MainActivityRepository> mStringData = new MutableLiveData<>();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG,"onServiceConnected : connected to service");
            MyBoundServices.MyBinder binder = (MyBoundServices.MyBinder) iBinder;
            mBinder.postValue(binder);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBinder.postValue(null);
        }
    };

    public LiveData<Boolean> getIsProgressUpdating(){
        setStringData();
        return mIsProgressUpdating;
    }
    public LiveData<MyBoundServices.MyBinder> getMyBinder(){
        return mBinder;
    }

    public ServiceConnection getServiceConnection(){
        return serviceConnection;
    }

    public void setIsUpdating(Boolean isUpdating){
        mIsProgressUpdating.postValue(isUpdating);
    }
    public void setStringData(){

        mStringData.postValue(mainActivityRepository);
    }

    public LiveData<MainActivityRepository> getStringData(){
        return mStringData;
    }
}
