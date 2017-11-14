package com.example.kamil.otomotonotifier.Data.Databases;

/**
 * Created by kamil on 14/11/2017.
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.kamil.otomotonotifier.Data.Converters.Converters;
import com.example.kamil.otomotonotifier.Data.Daos.ClientDao;
import com.example.kamil.otomotonotifier.Data.Models.Client;

@Database(entities = {Client.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class ClientDatabase extends RoomDatabase {
    private static ClientDatabase instance = null;

    public abstract ClientDao getClientDao();

    public static ClientDatabase getDatabase(Context context) {
        if(instance == null) {
            instance = (ClientDatabase) Room.databaseBuilder(context, ClientDatabase.class, "clients").allowMainThreadQueries().build();
        }
        return instance;
    }

    public static void destroyDatabase() {
        instance = null;
    }
}
