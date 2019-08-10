package com.androiddevbook.onyuorbike.chapter5.helpers;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public  class Toaster {
    private static String CLASS_NAME;
    private final Context context;
    private static int TOAST_DURATION = Toast.LENGTH_SHORT;

    public Toaster(Context context) {
        CLASS_NAME = getClass().getName();
        this.context = context;
    }

    public void make(int resource){
        Log.d(CLASS_NAME,"make()");
        Toast toast = Toast.makeText(context,resource,TOAST_DURATION);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }
}
