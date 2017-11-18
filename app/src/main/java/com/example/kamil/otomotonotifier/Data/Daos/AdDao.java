package com.example.kamil.otomotonotifier.Data.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.Models.AdEntity;

import java.util.List;

@Dao
public interface AdDao {
    @Insert(onConflict = 1)
    void addAdEntity(AdEntity ad);

    @Query("delete from AdEntity where id = :id")
    void deleteAdEntity(String id);

    @Query("delete from AdEntity")
    void deleteAllAdEntities();

    @Query("select * from AdEntity where id = :id")
    AdEntity getAdEntity(String id);

    @Query("select * from AdEntity")
    List<AdEntity> getAllAdEntities();

    @Update(onConflict = 1)
    void updateAdEntity(AdEntity adEntity);
}
