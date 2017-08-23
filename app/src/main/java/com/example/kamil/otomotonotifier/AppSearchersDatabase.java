package com.example.kamil.otomotonotifier;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Searcher.class, LastAdId.class}, exportSchema = false, version = 1)
public abstract class AppSearchersDatabase extends RoomDatabase {
    private static AppSearchersDatabase instance = null;

    public abstract LastAdIdDao getLastAdIdDao();

    public abstract SearcherDao getSearcherDao();

    public static AppSearchersDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = (AppSearchersDatabase) Room.databaseBuilder(context, AppSearchersDatabase.class, "searchers").allowMainThreadQueries().build();
        }
        return instance;
    }

    public static void destroyDatabase() {
        instance = null;
    }
}
