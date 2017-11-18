package com.example.kamil.otomotonotifier;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;

import com.example.kamil.otomotonotifier.AdEngine.AdEngine;
import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.AdEntity;
import com.example.kamil.otomotonotifier.Models.SearcherEntity;
import com.example.kamil.otomotonotifier.UI.Activities.AdListActivity;
import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;

import java.util.ArrayList;
import java.util.List;

public class SearchersCallerService extends Service {
    List<AdEntity> newSearchedAdEntities = new ArrayList();
    List<SearcherEntity> searcherEntities = new ArrayList();

    class SearchersCallerServiceThread implements Runnable {
        SearchersCallerServiceThread() {
        }

        public void run() {
            try {
                FindAndSaveNewSearchedAdEntites();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SearchersCallerService.this.stopSelf();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new SearchersCallerServiceThread()).start();
        return Service.START_NOT_STICKY;
    }

    private void FindAndSaveNewSearchedAdEntites() throws Exception {
        downloadSearchers();
        AdEngine.findNewAdsBySearchers(getSearchersFromSearcherEntities(), getApplicationContext());
        if (!newSearchedAdEntities.isEmpty()) {
            notifyAboutSearchedAds(searchedAdsToString());
        }
        saveSearchedAds();
    }

    private void downloadSearchers() {
        searcherEntities = AppDatabase.getDatabase(getApplicationContext()).getSearcherDao().getAllSearcherEntities();
    }

    private void notifyAboutSearchedAds(String str) throws Exception {
        Builder mBuilder = new Builder(this).setLights(1000, 1000, 1000)
                .setVibrate(new long[]{0, 1000})
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle("Nowe ogloszenia")
                .setContentText(str);
        Intent notifIntent = new Intent(this, AdListActivity.class);
        mBuilder.setContentIntent(PendingIntent.getActivity(this, 0, notifIntent, 0));
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, mBuilder.build());
    }

    private String searchedAdsToString() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (AdEntity ad : newSearchedAdEntities) {
            stringBuilder.append(ad + "\n");
        }
        return stringBuilder.toString();
    }

    private List<Searcher> getSearchersFromSearcherEntities() {
        List<Searcher> searchers = new ArrayList<>();
        for(SearcherEntity searcherEntity : searcherEntities) {
            searchers.add(searcherEntity.getSearcher());
        }
        return searchers;
    }

    private void saveSearchedAds() {
        for (int i = newSearchedAdEntities.size() - 1; i >= 0; i--) {
            AppDatabase.getDatabase(getApplicationContext()).getAdDao().addAdEntity(newSearchedAdEntities.get(i));
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
