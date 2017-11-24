package com.example.kamil.otomotonotifier.UI.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.Converters.EntityConverter;
import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.AdEntity;
import com.example.kamil.otomotonotifier.R;
import com.example.kamil.otomotonotifier.Services.SmsSender;
import com.example.kamil.otomotonotifier.Services.SimpleSmsSender;
import com.example.kamil.otomotonotifier.UI.Adapters.SearchedAdArrayAdapter;
import com.example.kamil.otomotonotifier.SearchersCallerReceiver;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdListActivity extends AppCompatActivity implements OnItemClickListener {
    private static final int SMS_PERMISSIONS_REQUEST = 1;
    private List<AdEntity> adEntities;
    private PendingIntent alarmIntent;
    private AlarmManager alarmMgr = null;
    private ListView searchedAdListView;
    private int refreshTimeInMinutes = 1;
    @BindView(R.id.clearAllAdsButton) public Button clearAllAdsButton;
    private static String sharedPrefName = "sharedPref";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!isBroadCastReveiverSet()) {
            initBroadCastReceiver();
        }
        initsearchedAdListView();
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if(currentApiVersion >= 23) {
            accessPermissions();
        }
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
                    smsSender.sendMessage("SmsSender works!", "+48728351564");
                    getSharedPreferences(sharedPrefName, MODE_PRIVATE).edit().putBoolean("firstSms", true).commit();
                }
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void onResume() {
        super.onResume();
        update();
    }

    public void goToSearcherListActivity(View view) {
        startActivity(new Intent(this, SearcherListActivity.class));
    }

    public void clearAllAds(View view) {
        AppDatabase.getDatabase(getApplicationContext()).getAdDao().deleteAllAdEntities();
        update();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((Ad) parent.getItemAtPosition(position)).getLink())));
    }

    private void initsearchedAdListView() {
        searchedAdListView = (ListView) findViewById(R.id.searchedAdListView);
        searchedAdListView.setOnItemClickListener(this);
    }

    private void update() {
        downloadAds();
        updateListView();
        updateClearAllAdsButton();
    }


    private void updateClearAllAdsButton() {
        clearAllAdsButton.setText(makeClearAllAdsButttonText());
    }

    private void updateListView() {
        searchedAdListView.setAdapter(new SearchedAdArrayAdapter(this, EntityConverter.AdEntitiesToAds(adEntities)));
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

    private void downloadAds() {
        adEntities = AppDatabase.getDatabase(getApplicationContext()).getAdDao().getAllAdEntities();
        Collections.reverse(adEntities);
    }

    private String makeClearAllAdsButttonText() {
        StringBuilder stringBuilder = new StringBuilder("");
        String baseText = getResources().getString(R.string.clear_all_ads);
        stringBuilder.append(baseText);
        stringBuilder.append(" (");
        stringBuilder.append(adEntities.size());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
