package com.example.kamil.otomotonotifier.Data.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kamil.otomotonotifier.Models.Client;

import java.util.List;

/**
 * Created by kamil on 14/11/2017.
 */

@Dao
public interface ClientDao {
    @Insert(onConflict = 1)
    void addClient(Client client);

    @Query("delete from client")
    void deleteAllClients();

    @Query("delete from client where phoneNumber = :phoneNumber")
    void deleteClientByPhoneNumber(String phoneNumber);

    @Query("select * from client")
    List<Client> getAllClients();

    @Query("select * from client where phoneNumber= :phoneNumber")
    Client getClientByPhoneNumber(String phoneNumber);

    @Update(onConflict = 1)
    void update(Client client);
}
