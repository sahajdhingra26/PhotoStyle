package com.comp90018;

import android.app.Application;
import android.content.ContextWrapper;
import android.content.res.Configuration;

import com.pixplicity.easyprefs.library.Prefs;

public class PhotoStyleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        // Required initialization logic here!
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}