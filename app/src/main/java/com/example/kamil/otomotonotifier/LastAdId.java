package com.example.kamil.otomotonotifier;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LastAdId {
    @PrimaryKey
    public String key;
    public String lastAdId;

    public LastAdId(String lastAdId, String key) {
        this.lastAdId = lastAdId;
        this.key = key;
    }
}
