package com.ipm.ipm.Model;

import com.google.gson.annotations.SerializedName;

public class Factory {
    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("acreage")
    private float acreage;

    @SerializedName("status")
    private String status;

    @SerializedName("price")
    private long price;

    @SerializedName("idIndustrial")
    private String idIndustrial;


    public Factory(String id, String name, String image, float acreage, String status, long price, String idIndustrial) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.acreage = acreage;
        this.status = status;
        this.price = price;
        this.idIndustrial = idIndustrial;
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

    public float getAcreage() {
        return acreage;
    }

    public void setAcreage(float acreage) {
        this.acreage = acreage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getIdIndustrial() {
        return idIndustrial;
    }

    public void setIdIndustrial(String idIndustrial) {
        this.idIndustrial = idIndustrial;
    }
}
