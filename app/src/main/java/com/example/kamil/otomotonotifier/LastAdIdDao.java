package com.example.kamil.otomotonotifier;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface LastAdIdDao {
    @Insert(onConflict = 1)
    void addLastAdId(LastAdId lastAdId);

    @Query("delete from lastAdId")
    void deleteAllLastAdIds();

    @Query("delete from lastAdId where key = :key")
    void deleteLastAdId(String key);

    @Query("select * from lastAdId")
    List<LastAdId> getAllLastAdIds();

    @Query("select * from lastAdId where key= :key")
    LastAdId getLastAdId(String key);

    @Update(onConflict = 1)
    void update(LastAdId lastAdId);
}
