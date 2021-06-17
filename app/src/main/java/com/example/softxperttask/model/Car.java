package com.example.softxperttask.model;

public class Car {
    private int id;
    private String brand;
    private String ConstructionYear ;
    private boolean isUsed;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getConstructionYear() {
        return ConstructionYear;
    }

    public void setConstructionYear(String constructionYear) {
        ConstructionYear = constructionYear;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
