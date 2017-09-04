package com.example.kamil.otomotonotifier.Filters;

import com.example.kamil.otomotonotifier.Ad;
import com.example.kamil.otomotonotifier.AdFilterTools;

/**
 * Created by kamil on 01/09/2017.
 */

public class SubcategoryAdFilter extends AdFilter {
    String subcategory;

    @Override
    boolean isTheAdGoodToAdd(Ad ad) {
        String adSubcategory = ad.getSubcategory();
        if(AdFilterTools.stringsEqual(subcategory, adSubcategory)) {
            return true;
        } else {
            return false;
        }
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
