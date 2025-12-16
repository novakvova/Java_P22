package com.example.mytaskmanager.application;

import android.app.Application;
import android.content.Context;

public class HomeApplication extends Application {
    private static HomeApplication instance;
    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appContext = getApplicationContext();
    }
    public static HomeApplication getInstance() {
        return instance;
    }
    public static Context getAppContext() {
        return appContext;
    }
}
