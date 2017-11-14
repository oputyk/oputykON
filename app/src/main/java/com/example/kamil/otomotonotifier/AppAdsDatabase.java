package com.example.kamil.otomotonotifier;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.kamil.otomotonotifier.Daos.AdDao;
import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;

@Database(entities = {Ad.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class AppAdsDatabase extends RoomDatabase {
    private static AppAdsDatabase instance = null;

    public abstract AdDao getAdDao();

    public static AppAdsDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = (AppAdsDatabase) Room.databaseBuilder(context, AppAdsDatabase.class, "Ads").allowMainThreadQueries().build();
        }
        return instance;
    }

    public static void destroyDatabase() {
        instance = null;
    }
}
