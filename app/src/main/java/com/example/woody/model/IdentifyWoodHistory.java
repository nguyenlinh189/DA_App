package com.example.woody.model;

import java.io.Serializable;

public class IdentifyWoodHistory implements Serializable {
    private int id;
    private Wood wood;
    private String path;
    private String result;
    private float prob;

    public IdentifyWoodHistory(int id, Wood wood, String path, String result, float prob) {
        this.id = id;
        this.wood = wood;
        this.path = path;
        this.result = result;
        this.prob = prob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Wood getWood() {
        return wood;
    }

    public void setWood(Wood wood) {
        this.wood = wood;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public float getProb() {
        return prob;
    }

    public void setProb(float prob) {
        this.prob = prob;
    }
}
