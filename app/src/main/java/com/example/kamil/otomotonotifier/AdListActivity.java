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
import android.widget.ListView;
import java.util.Calendar;
import java.util.List;

public class AdListActivity extends AppCompatActivity implements OnItemClickListener {
    private List<Ad> ads;
    private PendingIntent alarmIntent;
    private AlarmManager alarmMgr = null;
    private ListView searchedAdListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isBroadCastReveiverSet()) {
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
    }

    private void updateListView() {
        searchedAdListView.setAdapter(new SearchedAdArrayAdapter(this, ads));
    }

    public void initBroadCastReceiver() {
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, SearchersCaller.class);
        intent.setAction("com.example.kamil.otomotonotifier.broadcast");
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarmMgr.setRepeating(0, calendar.getTimeInMillis(), 60000, alarmIntent);
    }

    private boolean isBroadCastReveiverSet() {
        return PendingIntent.getBroadcast(this, 0, new Intent("com.example.kamil.otomotonotifier.broadcast"), PendingIntent.FLAG_UPDATE_CURRENT) != null;
    }

    private void downloadAds() {
        ads = AppAdsDatabase.getDatabase(getApplicationContext()).getAdDao().getAllAds();
    }
}
