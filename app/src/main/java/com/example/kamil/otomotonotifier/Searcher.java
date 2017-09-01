package com.example.kamil.otomotonotifier;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.List;

@Entity
public class Searcher {
    private String category;
    private String categoryCode;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String make = null;
    private Integer maxPrice = null;
    private Integer maxYear = null;
    private Integer minPrice = null;
    private Integer minYear = null;
    private String model = null;
    private String version = null;
    private String subcategory = null;
    private String subcategoryCode = null;

    public String toString() {
        return this.make != null ? this.make : "";
    }

    List<Ad> searchInAds(List<Ad> ads) throws Exception {
        SearcherToFilterContainerConverter converter = new SearcherToFilterContainerConverter();
        FilterContainer filterContainer = converter.convertSearcherToFilterContainer(this);
        return filterContainer.filterAds(ads);
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getMinYear() {
        return this.minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public Integer getMaxYear() {
        return this.maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public Integer getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getSubcategoryCode() {
        return subcategoryCode;
    }

    public void setSubcategoryCode(String subcategoryCode) {
        this.subcategoryCode = subcategoryCode;
    }
}
