package com.example.kamil.otomotonotifier.AdEngine.Data.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kamil.otomotonotifier.AdEngine.Models.LastAdId;

import java.util.List;

@Dao
public interface LastAdIdDao {
    @Insert(onConflict = 1)
    void addLastAdId(LastAdId lastAdId);

    @Query("delete from lastAdId")
    void deleteAllLastAdIds();

    @Query("delete from lastAdId where category = :category")
    void deleteLastAdId(String category);

    @Query("select * from lastAdId")
    List<LastAdId> getAllLastAdIds();

    @Query("select * from lastAdId where category= :category")
    LastAdId getLastAdId(String category);

    @Update(onConflict = 1)
    void updateLastAdId(LastAdId lastAdId);
}
