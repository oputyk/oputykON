package com.example.kamil.otomotonotifier;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 31/08/2017.
 */

public class Categories {

    @JsonProperty(value = "29")
    private Category cars;
    @JsonProperty(value = "65")
    private Category motorbikes;
    @JsonProperty(value = "73")
    private Category commercial;
    @JsonProperty(value = "1")
    private Category agro;
    @JsonProperty(value = "57")
    private Category trucks;
    @JsonProperty(value = "31")
    private Category construction;
    @JsonProperty(value = "9")
    private Category trailers;

    public List<Category> getCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(cars);
        categoryList.add(motorbikes);
        categoryList.add(commercial);
        categoryList.add(agro);
        categoryList.add(trucks);
        categoryList.add(construction);
        categoryList.add(trailers);
        return categoryList;
    }


    public Category getCars() {
        return cars;
    }

    public void setCars(Category cars) {
        this.cars = cars;
    }

    public Category getMotorbikes() {
        return motorbikes;
    }

    public void setMotorbikes(Category motorbikes) {
        this.motorbikes = motorbikes;
    }

    public Category getCommercial() {
        return commercial;
    }

    public void setCommercial(Category commercial) {
        this.commercial = commercial;
    }

    public Category getAgro() {
        return agro;
    }

    public void setAgro(Category agro) {
        this.agro = agro;
    }

    public Category getTrucks() {
        return trucks;
    }

    public void setTrucks(Category trucks) {
        this.trucks = trucks;
    }

    public Category getConstruction() {
        return construction;
    }

    public void setConstruction(Category construction) {
        this.construction = construction;
    }

    public Category getTrailers() {
        return trailers;
    }

    public void setTrailers(Category trailers) {
        this.trailers = trailers;
    }
}
