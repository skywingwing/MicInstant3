package com.pytorch.demo.speechrecognition;

import android.app.Application;

public class MyApp extends Application {
    public static MyApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }
}