package com.example.woody.dto;

import java.io.Serializable;

public class RequestImage implements Serializable {
    private String image;

    public RequestImage() {
    }

    public RequestImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
