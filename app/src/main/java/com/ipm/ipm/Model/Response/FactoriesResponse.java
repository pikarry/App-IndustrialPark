package com.ipm.ipm.Model.Response;

import com.google.gson.annotations.SerializedName;
import com.ipm.ipm.Model.Factory;
import com.ipm.ipm.Model.Industrial;

import java.util.List;

public class FactoriesResponse {
    @SerializedName("page")
    private String page;

    @SerializedName("total_page")
    private int total_page;

    @SerializedName("per_page")
    private int per_page;

    @SerializedName("factories")
    private List<Factory> factories;

    public FactoriesResponse(String page, int total_page, int per_page, List<Factory> factories) {
        this.page = page;
        this.total_page = total_page;
        this.per_page = per_page;
        this.factories = factories;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public void setFactories(List<Factory> factories) {
        this.factories = factories;
    }
}
