package com.example.kamil.otomotonotifier;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Ad {
    String adId;
    String category;
    String city;
    boolean damaged;
    Date date = new Date();
    int engine;
    String fuelType;
    @PrimaryKey(autoGenerate = true)
    int id;
    boolean kmNotMth = true;
    String link;
    String make;
    int mileage;
    String model;
    int power;
    int price;
    boolean seen = false;
    String subcategory;
    String type;
    String version;
    int year;

    public String toString() {
        return this.make + " " + this.model + (!this.version.isEmpty() ? " " : "") + this.version;
    }

    public String moneyToString() {
        return formatAndReturnIntToString(this.price) + " zł";
    }

    public String mileageToString() {
        if (this.kmNotMth) {
            return formatAndReturnIntToString(this.mileage) + " km";
        }
        return formatAndReturnIntToString(this.mileage) + " mth";
    }

    private String formatAndReturnIntToString(int number) {
        return getFormatter().format(Integer.toString(number));
    }

    @NonNull
    private DecimalFormat getFormatter() {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter;
    }

    public boolean equals(Ad ad) {
        return this.adId.equals(ad.adId);
    }

    public boolean equals(String adId) {
        return this.adId.equals(adId);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setKmNotMth(boolean kmNotMth) {
        this.kmNotMth = kmNotMth;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public int getId() {
        return this.id;
    }

    public String getAdId() {
        return this.adId;
    }

    public String getCategory() {
        return this.category;
    }

    public String getSubcategory() {
        return this.subcategory;
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    public String getVersion() {
        return this.version;
    }

    public String getType() {
        return this.type;
    }

    public String getFuelType() {
        return this.fuelType;
    }

    public int getPower() {
        return this.power;
    }

    public int getEngine() {
        return this.engine;
    }

    public boolean getDamaged() {
        return this.damaged;
    }

    public String getCity() {
        return this.city;
    }

    public int getPrice() {
        return this.price;
    }

    public int getYear() {
        return this.year;
    }

    public int getMileage() {
        return this.mileage;
    }

    public boolean isKmNotMth() {
        return this.kmNotMth;
    }

    public String getLink() {
        return this.link;
    }

    public boolean getSeen() {
        return this.seen;
    }

    public Date getDate() {
        return this.date;
    }
}
