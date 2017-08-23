package com.example.kamil.otomotonotifier;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import java.util.Calendar;
import java.util.List;

public class AdListActivity extends AppCompatActivity implements OnItemClickListener {
    private List<Ad> ads;
    private PendingIntent alarmIntent;
    private AlarmManager alarmMgr = null;
    private ListView searchedAdListView;
    Button clearAllAdsButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initClearAllAdsButton();
        if (!isBroadCastReveiverSet()) {//to change!!!
            initBroadCastReceiver();
        }
        initsearchedAdListView();
    }

    protected void onResume() {
        super.onResume();
        update();
    }

    public void goToSearcherListActivity(View view) {
        startActivity(new Intent(this, SearcherListActivity.class));
    }

    public void clearAllAds(View view) {
        AppAdsDatabase.getDatabase(getApplicationContext()).getAdDao().deleteAllAds();
        update();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((Ad) parent.getItemAtPosition(position)).getLink())));
    }

    private void initClearAllAdsButton() {
        clearAllAdsButton = (Button)findViewById(R.id.clearAllAdsButton);
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
        searchedAdListView.setAdapter(new SearchedAdArrayAdapter(this, ads));
    }

    public void initBroadCastReceiver() {
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, SearchersCaller.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, alarmIntent);
    }

    private boolean isBroadCastReveiverSet() {
        return PendingIntent.getBroadcast(this, 0, new Intent(this, SearchersCaller.class), PendingIntent.FLAG_NO_CREATE) != null;
    }

    private void downloadAds() {
        ads = AppAdsDatabase.getDatabase(getApplicationContext()).getAdDao().getAllAds();
    }

    private String makeClearAllAdsButttonText() {
        StringBuilder stringBuilder = new StringBuilder("");
        String baseText = getResources().getString(R.string.clear_all_ads);
        stringBuilder.append(baseText);
        stringBuilder.append(" (");
        stringBuilder.append(ads.size());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}