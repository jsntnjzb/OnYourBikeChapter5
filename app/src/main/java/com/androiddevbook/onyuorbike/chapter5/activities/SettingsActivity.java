package com.androiddevbook.onyuorbike.chapter5.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.androiddevbook.onyuorbike.chapter5.OnYourBike;
import com.androiddevbook.onyuorbike.chapter5.R;
import com.androiddevbook.onyuorbike.chapter5.helpers.Toaster;
import com.androiddevbook.onyuorbike.chapter5.model.Settings;

public class SettingsActivity extends Activity {
        static String CLASS_NAME;
        private CheckBox vibrate;

        public SettingsActivity(){
            CLASS_NAME = getClass().getName();
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        vibrate = (CheckBox)findViewById(R.id.vibrate_checkbox);
        Settings settings = ((OnYourBike)getApplication()).getSettings();
        vibrate.setChecked(settings.isVibrateOn(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Settings settings = ((OnYourBike)getApplication()).getSettings();
        settings.setVibrate(this,vibrate.isChecked());
    }

    public void vibrateChanged(View view){
        Toaster toaster = new Toaster(getApplicationContext());
        if(vibrate.isChecked()){
            toaster.make(R.string.vibrate_on);
        }else {
            toaster.make(R.string.vibrate_off);
        }
    }

    public void goBack(View view){
        finish();
    }

    private void gotoHome(){
        Log.d(CLASS_NAME,"gotoHome");
        Intent timer = new Intent(getApplicationContext(),TimerActivity.class);
        timer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(timer);
    }

    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

   

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                gotoHome();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
