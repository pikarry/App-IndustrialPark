package com.ipm.ipm.Model;

import com.google.gson.annotations.SerializedName;

public class Contract {
    @SerializedName("_id")
    private String id;

    @SerializedName("idUser")
    private String idUser;

    @SerializedName("idFactory")
    private String idFactory;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("deposit")
    private long deposit;

    @SerializedName("pricePerMonth")
    private long pricePerMonth;

    @SerializedName("isAccepted")
    private int isAccepted;

    @SerializedName("isFinieshed")
    private int isFinieshed;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    public Contract(String id, String idUser, String idFactory, String startDate, String endDate, long deposit, long pricePerMonth, int isAccepted, int isFinieshed, String createdAt, String updatedAt) {
        this.id = id;
        this.idUser = idUser;
        this.idFactory = idFactory;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.pricePerMonth = pricePerMonth;
        this.isAccepted = isAccepted;
        this.isFinieshed = isFinieshed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Contract(String idFactory, String startDate, String endDate) {
        this.idFactory = idFactory;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdFactory() {
        return idFactory;
    }

    public void setIdFactory(String idFactory) {
        this.idFactory = idFactory;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getDeposit() {
        return deposit;
    }

    public void setDeposit(long deposit) {
        this.deposit = deposit;
    }

    public long getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(long pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public int getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }

    public int getIsFinieshed() {
        return isFinieshed;
    }

    public void setIsFinieshed(int isFinieshed) {
        this.isFinieshed = isFinieshed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
