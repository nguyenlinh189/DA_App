package com.example.woody.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class PlantFamily implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("vietnamese")
    @Expose
    private String vietnamese;
    @SerializedName("english")
    @Expose
    private String english;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;

    public PlantFamily(String english) {
        this.english = english;
    }

    public PlantFamily(int id, String vietnamese, String english, String updateAt) {
        this.id = id;
        this.vietnamese = vietnamese;
        this.english = english;
        this.updateAt = updateAt;
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

    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        if(vietnamese!=null)
            return vietnamese +" ("+english+")";
        else
            return english;
    }
}
