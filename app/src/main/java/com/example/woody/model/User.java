package com.example.woody.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("enabled")
    @Expose
    private boolean enabled;

    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("createUpdate")
    @Expose
    private String createUpdate;
    private List<Wood> listFavouriteWood=new ArrayList<>();
    private List<IdentifyWoodHistory> listIdentify=new ArrayList<>();

    public User(int id, String name, String email, String password, String address, String phone, String role, boolean enabled, String provider, String createAt, String createUpdate, List<Wood> listFavouriteWood, List<IdentifyWoodHistory> listIdentify) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.enabled = enabled;
        this.provider = provider;
        this.createAt = createAt;
        this.createUpdate = createUpdate;
        this.listFavouriteWood = listFavouriteWood;
        this.listIdentify = listIdentify;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCreateUpdate() {
        return createUpdate;
    }

    public void setCreateUpdate(String createUpdate) {
        this.createUpdate = createUpdate;
    }

    public List<Wood> getListFavouriteWood() {
        return listFavouriteWood;
    }

    public void setListFavouriteWood(List<Wood> listFavouriteWood) {
        this.listFavouriteWood = listFavouriteWood;
    }

    public List<IdentifyWoodHistory> getListIdentify() {
        return listIdentify;
    }

    public void setListIdentify(List<IdentifyWoodHistory> listIdentify) {
        this.listIdentify = listIdentify;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
