package com.example.kamil.otomotonotifier.AdEngine.Filters;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;

/**
 * Created by kamil on 01/09/2017.
 */

public class TypeAdFilter extends AdFilter {
    String type;

    @Override
    boolean isTheAdGoodToAdd(Ad ad) {
        String adType = ad.getType();
        if(AdFilterTools.stringsEqual(type, adType)) {
            return true;
        } else {
            return false;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
