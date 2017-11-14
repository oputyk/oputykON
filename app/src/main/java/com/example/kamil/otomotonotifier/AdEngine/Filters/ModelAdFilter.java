package com.example.kamil.otomotonotifier.AdEngine.Filters;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.AdFilterTools;

/**
 * Created by kamil on 01/09/2017.
 */

public class ModelAdFilter extends AdFilter {
    String model;

    @Override
    boolean isTheAdGoodToAdd(Ad ad) {
        String adModel = ad.getModel();
        if(AdFilterTools.stringsEqual(model, adModel)) {
            return true;
        } else {
            return false;
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
