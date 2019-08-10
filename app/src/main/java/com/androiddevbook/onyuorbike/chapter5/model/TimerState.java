package com.androiddevbook.onyuorbike.chapter5.model;

import android.util.Log;

public class TimerState {
    public static String CLASS_NAME;
    public TimerState(){
        CLASS_NAME = getClass().getName();
    }

    private long startedAt;
    private long lastStoped;
    private boolean timerRunning;//计时器的状态

    public void reset(){
        timerRunning = false;
        startedAt = System.currentTimeMillis();
        lastStoped = 0;
    }

    public void  start(){
        timerRunning = true;
        startedAt = System.currentTimeMillis();
    }

    public void stop(){
        timerRunning = true;
        lastStoped = System.currentTimeMillis();
    }

    public String display(){
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;

        Log.d(CLASS_NAME,"Setting time display");

        if(isRunnning()){
            timeNow = System.currentTimeMillis();
        }else {
            timeNow = lastStoped;
        }

        //diff = timeNow-startedAt;
        diff = elapsedTime();
        if(diff<0){
            diff = 0;
        }

        seconds = diff/1000;
        minutes = seconds/60;
        hours = minutes/60;//小时
        seconds = seconds%60;//秒
        minutes = minutes%60;//分钟

        display = String.format("%d",hours)+":"+String.format("%02d",minutes) +":"+String.format("%02d",seconds);
        Log.i(CLASS_NAME,"Time is"+display);
        return display;
    }

    public long elapsedTime(){
        long timeNow;
        if(isRunnning()){
            timeNow = System.currentTimeMillis();
        }else {
            timeNow = lastStoped;
        }
        return timeNow-startedAt;
    }


    public boolean isRunnning(){
        return timerRunning;
    }
}
