package com.ipm.ipm.Model;

import com.google.gson.annotations.SerializedName;

public class Industrial {
    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("address")
    private String address;

    @SerializedName("location")
    private String location;

    @SerializedName("description")
    private String description;

    @SerializedName("totalAcreage")
    private int totalAcreage;

    @SerializedName("factories")
    private int factories;

    public Industrial(String id, String name, String image, String address, String location, String description, int totalAcreage, int factories) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.address = address;
        this.location = location;
        this.description = description;
        this.totalAcreage = totalAcreage;
        this.factories = factories;
    }

    public Industrial(String id, String name, String image, String address, String location, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.address = address;
        this.location = location;
        this.description = description;
    }

    public int getTotalAcreage() {
        return totalAcreage;
    }

    public void setTotalAcreage(int totalAcreage) {
        this.totalAcreage = totalAcreage;
    }

    public int getFactories() {
        return factories;
    }

    public void setFactories(int factories) {
        this.factories = factories;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
