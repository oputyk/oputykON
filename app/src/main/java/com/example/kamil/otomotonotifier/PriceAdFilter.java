package com.example.kamil.otomotonotifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 01/09/2017.
 */

public class PriceAdFilter extends AdFilter {
    public int maxPrice;
    public int minPrice;

    @Override
    boolean isTheAdGoodToAdd(Ad ad) {
        int adPrice = ad.getPrice();
        if(adPrice >= minPrice && adPrice <= maxPrice) {
            return true;
        } else {
            return false;
        }
    }


    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }
}
