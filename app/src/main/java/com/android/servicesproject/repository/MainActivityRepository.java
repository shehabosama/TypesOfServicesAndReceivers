package com.android.servicesproject.repository;

import android.content.Context;

public class MainActivityRepository {
    private String data;

    public void setStringData(){
        this.data = "hello from repo" ;
    }
    public String getStringData(Context context){
        data = "this the data from web service an it will appear in the main activity to test it to make sure if the repos is working fine"+ context.getPackageName();
        return data;
    }
}
