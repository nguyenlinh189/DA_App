package com.example.woody.dto;

public class RequestPredict {
    private String image;

    public RequestPredict(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
