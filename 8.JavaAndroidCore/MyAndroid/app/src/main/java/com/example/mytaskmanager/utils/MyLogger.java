package com.example.mytaskmanager.utils;


import android.widget.Toast;

import com.example.mytaskmanager.application.HomeApplication;

public class MyLogger {
    public static void toast(String text) {
        Toast.makeText(HomeApplication.getAppContext(), text, Toast.LENGTH_SHORT).show();
    }
}
