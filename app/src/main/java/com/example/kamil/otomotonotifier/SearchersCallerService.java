package com.example.kamil.otomotonotifier;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import com.example.kamil.otomotonotifier.AdEngine.AdEngine;
import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.Converters.AdEntityToTextMessageConverter;
import com.example.kamil.otomotonotifier.Converters.EntityConverter;
import com.example.kamil.otomotonotifier.Data.Daos.ClientDao;
import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.AdEntity;
import com.example.kamil.otomotonotifier.Models.Client;
import com.example.kamil.otomotonotifier.Models.SearcherEntity;
import com.example.kamil.otomotonotifier.Services.SimpleSmsSender;
import com.example.kamil.otomotonotifier.Services.SmsSender;
import com.example.kamil.otomotonotifier.UI.Activities.AdListActivity;
import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchersCallerService extends Service {
    Map<SearcherEntity, List<AdEntity>> searcherEntityAdEntityListMap;
    List<AdEntity> newSearchedAdEntities = new ArrayList();
    List<SearcherEntity> searcherEntities = new ArrayList();

    class SearchersCallerServiceThread implements Runnable {
        SearchersCallerServiceThread() {
        }

        public void run() {
            try {
                findAndSaveNewSearchedAdEntites();
                saveAppDatabase();
                if (!newSearchedAdEntities.isEmpty()) {
                    notifyAboutSearchedAds();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            SearchersCallerService.this.stopSelf();
        }
    }

    private void saveAppDatabase() {
        try {
            File sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite()) {
                String currentDBPath =
                        getDatabasePath("AppDatabase").getAbsolutePath();
                String backupDBPath = "AppDatabase.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new SearchersCallerServiceThread()).start();
        return Service.START_NOT_STICKY;
    }

    private void findAndSaveNewSearchedAdEntites() throws Exception {
        downloadSearchers();
        Map<Searcher, List<Ad>> searcherAndAdlistMap = AdEngine.findNewAdsBySearchers(EntityConverter.SearcherEntitiesToSearchers(searcherEntities), getApplicationContext());
        saveSearchedAdEntites(searcherAndAdlistMap);
    }

    private void downloadSearchers() {
        searcherEntities = AppDatabase.getDatabase(getApplicationContext()).getSearcherDao().getAllSearcherEntities();
    }

    private void notifyAboutSearchedAds() throws Exception {
        makeNotification(searchedAdEntitiesToString());
        sendSmss();
    }

    private void sendSmss() {
        SmsSender smsSender = new SimpleSmsSender();
        ClientDao clientDao = AppDatabase.getDatabase(getApplicationContext()).getClientDao();
        for(AdEntity adEntity : newSearchedAdEntities) {
            Client client = clientDao.getClientByAdEntityId(adEntity.getId());
            smsSender.sendMessage(AdEntityToTextMessageConverter.convert(adEntity), client.getPhoneNumber());
        }
    }

    private void makeNotification(String str) {
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

    private String searchedAdEntitiesToString() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (AdEntity adEntity : newSearchedAdEntities) {
            stringBuilder.append(adEntity.getAd() + "\n");
        }
        return stringBuilder.toString();
    }

    private void saveSearchedAdEntites(Map<Searcher, List<Ad>> searcherAndAdlistMap) {
        for(SearcherEntity searcherEntity : searcherEntities) {
            for (Ad ad : searcherAndAdlistMap.get(searcherEntity.getSearcher())) {
                AppDatabase.getDatabase(getApplicationContext()).getAdDao().addAdEntity(new AdEntity(0, ad, searcherEntity.getId()));
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
