package com.example.kamil.otomotonotifier.AdEngine.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kamil on 29/08/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    private String id;
    private String name;
    private String nameEn;
    private String code;
    private List<Category> subCategories = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value = "name_en")
    public String getNameEn() {
        return nameEn;
    }

    @JsonProperty(value = "name_en")
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @JsonProperty(value = "children")
    public List<Category> getSubCategories() {
        return subCategories;
    }

    @JsonProperty(value = "children")
    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
