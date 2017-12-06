package com.example.kamil.otomotonotifier.Models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;

/**
 * Created by kamil on 18/11/2017.
 */

@Entity
public class SearcherEntity {
    @PrimaryKey(autoGenerate = true)
    int id;
    String phoneNumber;
    @Embedded
    Searcher searcher;

    public SearcherEntity(int id, String phoneNumber, Searcher searcher) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.searcher = searcher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Searcher getSearcher() {
        return searcher;
    }

    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }
}
