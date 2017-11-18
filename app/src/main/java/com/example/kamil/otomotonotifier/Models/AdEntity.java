package com.example.kamil.otomotonotifier.Models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;

/**
 * Created by kamil on 18/11/2017.
 */

@Entity
public class AdEntity {
    @PrimaryKey(autoGenerate = true)
    int id;
    @Embedded
    Ad ad;
    int idSearcher;

    public AdEntity(int id, Ad ad, int idSearcher) {
        this.id = id;
        this.ad = ad;
        this.idSearcher = idSearcher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public int getIdSearcher() {
        return idSearcher;
    }

    public void setIdSearcher(int idSearcher) {
        this.idSearcher = idSearcher;
    }
}
