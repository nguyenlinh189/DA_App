package com.example.woody.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Preservation implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("vietnamese")
    @Expose
    private String vietnamese;
    @SerializedName("english")
    @Expose
    private String english;
    @SerializedName("acronym")
    @Expose
    private String acronym;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;

    public Preservation(int id, String vietnamese, String english, String acronym, String updateAt) {
        this.id = id;
        this.vietnamese = vietnamese;
        this.english = english;
        this.acronym = acronym;
        this.updateAt = updateAt;
    }

    public Preservation(String vietnamese, String acronym) {
        this.acronym=acronym;
        this.vietnamese = vietnamese;
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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        if(acronym==null)
            return vietnamese;
        else
            return acronym+" - "+vietnamese;
    }
}
