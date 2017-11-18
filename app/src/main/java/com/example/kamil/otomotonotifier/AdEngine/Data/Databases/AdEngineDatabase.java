package com.example.kamil.otomotonotifier.AdEngine.Data.Databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.kamil.otomotonotifier.AdEngine.Data.Daos.LastAdIdDao;
import com.example.kamil.otomotonotifier.AdEngine.Models.LastAdId;

/**
 * Created by kamil on 18/11/2017.
 */

@Database(entities = {LastAdId.class}, exportSchema = false, version = 1)
public abstract class AdEngineDatabase extends RoomDatabase {
    private static AdEngineDatabase instance = null;

    public abstract LastAdIdDao getLastAdIdDao();

    public static AdEngineDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = (AdEngineDatabase) Room.databaseBuilder(context, AdEngineDatabase.class, "AdEngineDatabase").allowMainThreadQueries().build();
        }
        return instance;
    }

    public static void destroyDatabase() {
        instance = null;
    }
}
