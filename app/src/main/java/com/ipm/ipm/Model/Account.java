package com.ipm.ipm.Model;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("_id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("gender")
    private String gender;

    @SerializedName("avt")
    private String avt;

    @SerializedName("role")
    private String role;

    public Account(String id, String email, String password, String phone, String address, String fullname, String gender, String avt, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.fullname = fullname;
        this.gender = gender;
        this.avt = avt;
        this.role = role;
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Account(String phone, String address, String fullname, String gender) {
        this.phone = phone;
        this.address = address;
        this.fullname = fullname;
        this.gender = gender;
    }

    public Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
