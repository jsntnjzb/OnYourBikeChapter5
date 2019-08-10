package com.androiddevbook.onyuorbike.chapter5.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

public class Settings {
    private static String CLASS_NAME;
    private static String VIBRATE = "vibrate";

    public Settings(){
        CLASS_NAME = getClass().getName();
    }

    /**振动功能是否已经启动*/
    protected boolean vibrateOn;

    public boolean isVibrateOn(Activity activity){
        Log.d(CLASS_NAME,"isVibrateOn");
        SharedPreferences preferences = activity.getSharedPreferences(CLASS_NAME,Activity.MODE_PRIVATE);
        if(preferences.contains(VIBRATE)){
            vibrateOn = preferences.getBoolean(VIBRATE,false);
        }
        return vibrateOn;
    }

    public void setVibrate(Activity activity,boolean vibrate){
        Log.d(CLASS_NAME,"setVibtate");
        vibrateOn = vibrate;
        SharedPreferences preferences = activity.getSharedPreferences(CLASS_NAME,Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(VIBRATE,vibrate);
        editor.apply();
    }
}
