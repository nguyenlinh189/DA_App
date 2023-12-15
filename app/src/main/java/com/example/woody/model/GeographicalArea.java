package com.example.woody.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeographicalArea implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("vietnamese")
    @Expose
    private String vietnamese;
    @SerializedName("english")
    @Expose
    private String english;

    public GeographicalArea(String english) {
        this.english = english;
    }

    public GeographicalArea(int id, String vietnamese, String english) {
        this.id = id;
        this.vietnamese = vietnamese;
        this.english = english;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnameese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @Override
    public String toString() {
        return english;
    }
}
