package com.example.kamil.otomotonotifier.Data.Databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;
import com.example.kamil.otomotonotifier.Data.Converters.Converters;
import com.example.kamil.otomotonotifier.Data.Daos.AdDao;
import com.example.kamil.otomotonotifier.Data.Daos.ClientDao;
import com.example.kamil.otomotonotifier.Data.Daos.SearcherDao;
import com.example.kamil.otomotonotifier.Models.AdEntity;
import com.example.kamil.otomotonotifier.Models.Client;
import com.example.kamil.otomotonotifier.Models.SearcherEntity;

@Database(entities = {Client.class, SearcherEntity.class, AdEntity.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;

    public abstract AdDao getAdEntityDao();
    public abstract ClientDao getClientDao();
    public abstract SearcherDao getSearcherEntityDao();

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = (AppDatabase) Room.databaseBuilder(context, AppDatabase.class, "AppDatabase").allowMainThreadQueries().build();
        }
        return instance;
    }

    public static void destroyDatabase() {
        instance = null;
    }
}
