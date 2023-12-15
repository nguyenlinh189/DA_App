package com.example.woody.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("nameImg")
    @Expose
    private String nameImg;

    public Image(int id, String path, String nameImg) {
        this.id = id;
        this.path = path;
        this.nameImg = nameImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNameImg() {
        return nameImg;
    }

    public void setNameImg(String nameImg) {
        this.nameImg = nameImg;
    }
}
