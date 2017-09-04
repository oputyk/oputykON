package com.example.kamil.otomotonotifier.Filters;

import com.example.kamil.otomotonotifier.Ad;
import com.example.kamil.otomotonotifier.AdFilterTools;

/**
 * Created by kamil on 04/09/2017.
 */

public class FuelTypeAdFilter extends AdFilter {
    private String fuelType;

    @Override
    boolean isTheAdGoodToAdd(Ad ad) {
        String adFuelType = ad.getFuelType();
        if(AdFilterTools.stringsEqual(fuelType, adFuelType)) {
            return true;
        } else {
            return false;
        }
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
