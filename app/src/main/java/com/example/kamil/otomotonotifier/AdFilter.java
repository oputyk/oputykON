package com.example.kamil.otomotonotifier;

import android.util.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 01/09/2017.
 */

public abstract class AdFilter {
    public List<Ad> filterAds(List<Ad> ads) {
        List<Ad> filteredAds = new ArrayList<>();
        for(Ad ad : ads) {
            if(isTheAdGoodToAdd(ad)) {
                filteredAds.add(ad);
            }
        }
        return filteredAds;
    }

    abstract boolean isTheAdGoodToAdd(Ad ad);
}
