package com.androiddevbook.onyuorbike.chapter5.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androiddevbook.onyuorbike.chapter5.BuildConfig;
import com.androiddevbook.onyuorbike.chapter5.R;
import com.androiddevbook.onyuorbike.chapter5.model.TimerState;

public class TimerActivity extends Activity {
    static String CLASS_NAME;
    protected TextView counter;
    protected Button start;
    protected Button stop;
    //protected boolean timerRunning;//计时器的状态
    protected long startedAt;//计时开始时间
    protected long lastStopped;//计时结束时间
    private static long UPDATE_EVERY = 200;//更新屏幕counter的频率
    protected Handler handler;
    protected UpdateTimer updateTimer;
    protected Vibrator vibrator;
    protected long lastSeconds;
    private TimerState timer;

    public TimerActivity() {
        CLASS_NAME = getClass().getName();
        timer = new TimerState();
    }

    /**
     * 切换按钮启用或者禁用
     * */
    protected void enableButtons(){
        Log.d(CLASS_NAME,"Set buttons enabled/disabled");
        start.setEnabled(timer.isRunnning());
        stop.setEnabled(timer.isRunnning());
    }

    /**
     * 启动定时器
     * */
    public void clickedStart(View view){
        Log.d(CLASS_NAME,"Clicked start button.");
        //timerRunning = true;
        //timer.isRunnning() = false;
        //startedAt = System.currentTimeMillis();
        timer.start();
        enableButtons();
       //setTimeDisplay();
        counter.setText(timer.display());
        handler = new Handler();
        updateTimer = new UpdateTimer();
        handler.postDelayed(updateTimer,UPDATE_EVERY);
    }

    /**
     * 关闭定时器
     * */
    public void clickedStop(View view){
        Log.d(CLASS_NAME,"Clicked stop button");
        //timerRunning = false;
        //timer.isRunnning() = false;
        //lastStopped = System.currentTimeMillis();
        timer.stop();
        enableButtons();
        //setTimeDisplay();
        counter.setText(timer.display());
        handler.removeCallbacks(updateTimer);
        handler = null;
    }

    public void clickedSettings(View view){
        Intent settingsIntent = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(settingsIntent);
        Log.d(CLASS_NAME,"clickedSettings");
    }

    protected void setTimeDisplay(){
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;

        Log.d(CLASS_NAME,"Setting time display");

        if(timer.isRunnning()){
            timeNow = System.currentTimeMillis();
        }else {
            timeNow = lastStopped;
        }

        diff = timeNow-startedAt;
        if(diff<0){
            diff = 0;
        }

        seconds = diff/1000;
        minutes = seconds/60;
        hours = minutes/60;//小时
        seconds = seconds%60;//秒
        minutes = minutes%60;//分钟

        display = String.format("%d",hours)+":"+String.format("%02d",minutes) +":"+String.format("%02d",seconds);
        counter.setText(display);
    }

    protected void vibrateCheck(){
        long timeNow = System.currentTimeMillis();
        //long diff = timeNow-startedAt;
        long diff = timer.elapsedTime();
        long seconds = diff/1000;
        long minutes = seconds/60;

        Log.d(CLASS_NAME,"vibrateCheck");
        seconds = seconds%60;
        minutes = minutes%60;

        if(vibrator!=null && seconds == 0 && seconds!=lastSeconds){
            long[] once = {0,100};
            long[] twice = {0,100,400,100};
            long[] thrice = {0,100,400,100,400,100};

            //every hour
            if(minutes==0){
                Log.i(CLASS_NAME,"Vibrate 3 times");
                vibrator.vibrate(thrice,-1);
            }

            //every 15 minutes
            if(minutes%15==0){
                Log.i(CLASS_NAME,"Vibrate 2 times");
                vibrator.vibrate(twice,-1);
            }

            //every 5 minutes
            if(minutes%5==0){
                Log.i(CLASS_NAME,"Vibrate once");
                vibrator.vibrate(once,-1);
            }
        }
        lastSeconds = seconds;
    }

    class UpdateTimer implements Runnable{
        @Override
        public void run() {
            //Log.d(CLASS_NAME,"run");
            //setTimeDisplay();
//            counter.setText(timer.display());
//            if(handler!=null){
//                handler.postDelayed(this,UPDATE_EVERY);
//            }
            if(timer.isRunnning()){

            }
            vibrateCheck();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Log.i("Oncreate","Oncreate被调用");
        counter = (TextView)findViewById(R.id.timer);
        start = (Button)findViewById(R.id.start_button);
        stop = (Button)findViewById(R.id.stop_button);
        enableButtons();
        Log.d(CLASS_NAME,"Setting text.");
        /**
         * 使用strict模式
         * */
        if(BuildConfig.DEBUG){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(CLASS_NAME,"OnStart");
        if(timer.isRunnning()){
            handler = new Handler();
            updateTimer = new UpdateTimer();
            handler.postDelayed(updateTimer,UPDATE_EVERY);
        }

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        if(vibrator==null){
            Log.w(CLASS_NAME,"No vibration service exists");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(CLASS_NAME,"onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(CLASS_NAME,"onResume");
        enableButtons();
        //setTimeDisplay();
        counter.setText(timer.display());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(CLASS_NAME,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(CLASS_NAME,"onStop");
        if(timer.isRunnning()){
            handler.removeCallbacks(updateTimer);
            updateTimer = null;
            handler = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(CLASS_NAME,"onDestroy");
    }

    /**
     * 仅被调用一次，设置Activity菜单
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(CLASS_NAME,"Showing menu.");
        //getLayoutInflater().inflate(R.m)
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                clickedSettings(null);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
