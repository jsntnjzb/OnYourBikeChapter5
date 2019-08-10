package com.androiddevbook.onyuorbike.chapter5.helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.androiddevbook.onyuorbike.chapter5.R;

/**
 *
 * */
public class Notify {
    private static String CLASS_NAME;
    private final NotificationManager manager;
    private final Context context;
    private static final int MESSAGE_ID = 1;

    public Notify(Activity activity){
        CLASS_NAME = getClass().getName();
        manager = (NotificationManager)activity.getSystemService(Context.NOTIFICATION_SERVICE);
        context = activity.getApplicationContext();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification create(String title, String message, long when){
        Notification notification = new Notification.Builder(context)
                .setContentTitle(title).setContentText(message).setWhen(when)
                .setSmallIcon(R.mipmap.ic_launcher).build();
        return notification;
    }

    public void notify(String title,String message){
        Log.d(CLASS_NAME, "notify()");
        Notification notification = create(title,message,System.currentTimeMillis());
        manager.notify(MESSAGE_ID,notification);
    }

    public void notify(String title,String message,long when){
        Log.d(CLASS_NAME, "notify()");
        Notification notification = create(title,message,when);
        manager.notify(MESSAGE_ID,notification);
    }
}
