package com.example.kamil.otomotonotifier.AdEngine.Filters;

import com.example.kamil.otomotonotifier.AdEngine.Filters.AdFilter;
import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 01/09/2017.
 */

public class FilterContainer {
    private List<AdFilter> adFilters = new ArrayList<>();

    public List<Ad> filterAds(List<Ad> ads) {
        for(AdFilter adFilter : adFilters) {
            ads = adFilter.filterAds(ads);
        }
        return ads;
    }

    public void setAdFilters(List<AdFilter> adFilters) {
        this.adFilters = adFilters;
    }
}
