package com.example.kamil.otomotonotifier.AdEngine.Filters;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.AdFilterTools;

/**
 * Created by kamil on 01/09/2017.
 */

public class MakeAdFilter extends AdFilter {
    String make;

    @Override
    boolean isTheAdGoodToAdd(Ad ad) {
        String adMake = ad.getMake();
        if(AdFilterTools.stringsEqual(make, adMake)) {
            return true;
        } else {
            return false;
        }
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
}
