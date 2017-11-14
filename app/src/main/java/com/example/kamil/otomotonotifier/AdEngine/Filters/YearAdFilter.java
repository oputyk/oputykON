package com.example.kamil.otomotonotifier.AdEngine.Filters;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;

/**
 * Created by kamil on 01/09/2017.
 */

public class YearAdFilter extends AdFilter {
    int maxYear;
    int minYear;

    @Override
    boolean isTheAdGoodToAdd(Ad ad) {
        int adYear = ad.getYear();
        if(adYear >= minYear && adYear <= maxYear) {
            return true;
        } else {
            return false;
        }
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }
}
