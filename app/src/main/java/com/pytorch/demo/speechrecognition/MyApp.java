package com.pytorch.demo.speechrecognition;

import com.chaquo.python.android.PyApplication;

public class MyApp extends PyApplication {
    public static MyApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static MyApp getInstance() {
        return mApp;
    }
}