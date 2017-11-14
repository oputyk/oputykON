package com.example.kamil.otomotonotifier;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;

import com.example.kamil.otomotonotifier.Activities.AdListActivity;
import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchersCallerService extends Service {
    List<Ad> newSearchedAds = new ArrayList();
    List<Searcher> searchers = new ArrayList();

    class SearchersCallerServiceThread implements Runnable {
        SearchersCallerServiceThread() {
        }

        public void run() {
            try {
                doCallersServiceWork();
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

    private void doCallersServiceWork() throws Exception {
        downloadSearchers();
        gatherSearchedAds();
        if (!newSearchedAds.isEmpty()) {
            notifyAboutSearchedAds(searchedAdsToString());
        }
        saveSearchedAds();
    }

    private void downloadSearchers() {
        searchers = AppSearchersDatabase.getDatabase(getApplicationContext()).getSearcherDao().getAllSearchers();
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
        for (Ad ad : newSearchedAds) {
            stringBuilder.append(ad + "\n");
        }
        return stringBuilder.toString();
    }

    private void gatherSearchedAds() throws Exception {
        //if(!DateBlocker.isBlocked(getBeginningOf2018YearDate())) {
            SearcherContentDownloader searcherContentDownloader = new SearcherContentDownloader();
            searcherContentDownloader.downloadSearcherContent(searchers, getApplicationContext());
            for (Searcher searcher : searchers) {
                List<Ad> ads = searcher.searchInAds(searcherContentDownloader.getAdListForSearcher(searcher));
                for (Ad ad : ads) {
                    newSearchedAds.add(ad);
                }
            }
        //}
    }

    private Date getBeginningOf2018YearDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.YEAR, 2018);
        return calendar.getTime();
    }

    private void saveSearchedAds() {
        for (int i = newSearchedAds.size() - 1; i >= 0; i--) {
            AppAdsDatabase.getDatabase(getApplicationContext()).getAdDao().addAd(newSearchedAds.get(i));
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
