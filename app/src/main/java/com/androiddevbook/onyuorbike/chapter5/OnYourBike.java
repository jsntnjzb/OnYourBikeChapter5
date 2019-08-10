package com.androiddevbook.onyuorbike.chapter5;

import android.app.Application;

import com.androiddevbook.onyuorbike.chapter5.model.Settings;

public class OnYourBike extends Application {
    protected Settings settings;

    public Settings getSettings(){
        if(settings==null){
            settings = new Settings();
        }
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
