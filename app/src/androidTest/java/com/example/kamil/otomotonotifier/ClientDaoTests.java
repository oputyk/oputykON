package com.example.kamil.otomotonotifier;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.Beta;
import android.support.test.runner.AndroidJUnit4;

import com.example.kamil.otomotonotifier.Data.Daos.ClientDao;
import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.Client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Created by kamil on 03/12/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ClientDaoTests {
    private ClientDao clientDao;
    private AppDatabase appDatabase;

    @Before
    public void createDatabase() {
        Context context = InstrumentationRegistry.getTargetContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        clientDao = appDatabase.getClientDao();
    }

    @After
    public void closeDatabase() throws IOException {
        appDatabase.close();
    }

    @Test(expected = Exception.class)
    public void insertConflictTest() {
        clientDao.addClient(new Client("9909"));
        clientDao.addClient(new Client("9909"));
    }
}
