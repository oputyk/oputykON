package com.example.kamil.otomotonotifier.Data.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;

import java.util.List;

@Dao
public interface AdDao {
    @Insert(onConflict = 1)
    void addAd(Ad ad);

    @Query("delete from ad where adId = :adId")
    void deleteAd(String adId);

    @Query("delete from ad")
    void deleteAllAds();

    @Query("select * from ad where adId = :adId")
    Ad getAd(String adId);

    @Query("select * from ad")
    List<Ad> getAllAds();

    @Update(onConflict = 1)
    void update(Ad ad);
}
