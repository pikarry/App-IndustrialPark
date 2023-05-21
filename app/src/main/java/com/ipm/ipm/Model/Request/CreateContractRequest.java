package com.ipm.ipm.Model.Request;

import com.google.gson.annotations.SerializedName;

public class CreateContractRequest {
    @SerializedName("idFactory")
    private String idFactory;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    public CreateContractRequest(String idFactory, String startDate, String endDate) {
        this.idFactory = idFactory;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
