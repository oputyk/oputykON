package com.example.kamil.otomotonotifier;

import android.graphics.PorterDuff;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 01/09/2017.
 */

public class SearcherToFilterContainerConverter {
    private Searcher searcher;
    private List<AdFilter> adFilters = new ArrayList<>();
    FilterContainer filterContainer = new FilterContainer();

    public FilterContainer convertSearcherToFilterContainer(Searcher searcher) {
        this.searcher = searcher;
        createAdFiltersFromSearcher();
        createFilterContainerFromAdFilters();
        return filterContainer;
    }


    private void createAdFiltersFromSearcher() {
        addSubcategoryFilter();
        addMakeFilter();
        addModelFilter();
        addVersionFilter();
        addPriceFilter();
        addYearFilter();
    }


    @NonNull
    private void createFilterContainerFromAdFilters() {
        filterContainer.setAdFilters(adFilters);
    }


    private void addSubcategoryFilter() {
        if(searcher.getSubcategory() != null) {
            SubcategoryAdFilter subcategoryAdFilter = new SubcategoryAdFilter();
            subcategoryAdFilter.setSubcategory(searcher.getSubcategory());
            adFilters.add(subcategoryAdFilter);
        }
    }

    private void addMakeFilter() {
        if(searcher.getMake() != null) {
            MakeAdFilter makeAdFilter = new MakeAdFilter();
            makeAdFilter.setMake(searcher.getMake());
            adFilters.add(makeAdFilter);
        }
    }

    private void addModelFilter() {
        if(searcher.getModel() != null) {
            ModelAdFilter modelAdFilter = new ModelAdFilter();
            modelAdFilter.setModel(searcher.getModel());
            adFilters.add(modelAdFilter);
        }
    }

    private void addVersionFilter() {
        if(searcher.getVersion() != null) {
            VersionAdFilter versionAdFilter = new VersionAdFilter();
            versionAdFilter.setVersion(searcher.getVersion());
            adFilters.add(versionAdFilter);
        }
    }

    private void addPriceFilter() {
        if(isMaxPriceOrMinPriceNotNull()) {
            PriceAdFilter priceAdFilter = new PriceAdFilter();
            setMaxPriceInPriceAdFilter(priceAdFilter);
            setMinPriceInPriceAdFilter(priceAdFilter);
            adFilters.add(priceAdFilter);
        }
    }

    private void addYearFilter() {
        if(isMaxYearOrMinYearNotNull()) {
            YearAdFilter yearAdFiler = new YearAdFilter();
            setMaxYearAdFilter(yearAdFiler);
            setMinYearAdFilter(yearAdFiler);
            adFilters.add(yearAdFiler);
        }
    }


    private boolean isMaxPriceOrMinPriceNotNull() {
        return searcher.getMaxPrice() != null || searcher.getMinPrice() != null;
    }

    private void setMaxPriceInPriceAdFilter(PriceAdFilter priceAdFilter) {
        if(searcher.getMaxPrice() != null) {
            priceAdFilter.setMaxPrice(searcher.getMaxPrice());
        }
    }

    private void setMinPriceInPriceAdFilter(PriceAdFilter priceAdFilter) {
        if(searcher.getMinPrice() != null) {
            priceAdFilter.setMinPrice(searcher.getMinPrice());
        }
    }

    private boolean isMaxYearOrMinYearNotNull() {
        return searcher.getMaxYear() != null || searcher.getMinYear() != null;
    }

    private void setMaxYearAdFilter(YearAdFilter yearAdFiler) {
        if(searcher.getMaxYear() != null) {
            yearAdFiler.setMaxYear(searcher.getMaxYear());
        }
    }

    private void setMinYearAdFilter(YearAdFilter yearAdFiler) {
        if(searcher.getMinYear() != null) {
            yearAdFiler.setMinYear(searcher.getMinYear());
        }
    }

}
