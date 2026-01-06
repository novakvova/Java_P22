package com.example.mytaskmanager.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mytaskmanager.security.JwtSecurityService;

public class HomeApplication extends Application implements JwtSecurityService {
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

    @Override
    public void saveJwtToken(String token) {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs =  instance.getSharedPreferences("jwtStore", MODE_PRIVATE);
        edit=prefs.edit();
        try {
            edit.putString("token",token);
            edit.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getToken() {
        SharedPreferences prefs=instance.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return token;
    }

    @Override
    public void deleteToken() {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs=instance.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        edit=prefs.edit();
        try {
            edit.remove("token");
            edit.apply();
            edit.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAuth() {
        if(getToken().equals(""))
            return false;
        return true;
    }
}
