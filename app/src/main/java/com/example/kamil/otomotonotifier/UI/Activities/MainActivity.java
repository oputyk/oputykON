package com.example.kamil.otomotonotifier.UI.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kamil.otomotonotifier.R;
import com.example.kamil.otomotonotifier.SearchersCallerReceiver;
import com.example.kamil.otomotonotifier.Services.SimpleSmsSender;
import com.example.kamil.otomotonotifier.Services.SmsSender;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int SMS_PERMISSIONS_REQUEST = 1;
    private PendingIntent alarmIntent;
    private AlarmManager alarmMgr = null;
    private static final int refreshTimeInMinutes = 1;
    private static final String sharedPrefName = "sharedPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!isBroadCastReveiverSet()) {
            initBroadCastReceiver();
        }
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if(currentApiVersion >= 23) {
            accessPermissions();
        }
    }

    @OnClick(R.id.toClientListButton)
    public void toClientList() {
        Intent intent = new Intent(MainActivity.this, ClientListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.toAdListButton)
    public void toAdList() {
        Intent intent = new Intent(MainActivity.this, AdListActivity.class);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void accessPermissions() {
        requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS}, SMS_PERMISSIONS_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 2 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                if(!getSharedPreferences(sharedPrefName, MODE_PRIVATE).contains("firstSms")) {
                    SmsSender smsSender = new SimpleSmsSender();
                    smsSender.sendMessage("SmsSender works!", "728351564");
                    getSharedPreferences(sharedPrefName, MODE_PRIVATE).edit().putBoolean("firstSms", true).commit();
                }
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void initBroadCastReceiver() {
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, SearchersCallerReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), refreshTimeInMinutes * 60000, alarmIntent);
        getSharedPreferences(sharedPrefName, MODE_PRIVATE).edit().putBoolean("isBroadCastReveiverSet", true).commit();
    }

    private boolean isBroadCastReveiverSet() {
        return getSharedPreferences(sharedPrefName, MODE_PRIVATE).contains("isBroadCastReveiverSet");
    }
}
