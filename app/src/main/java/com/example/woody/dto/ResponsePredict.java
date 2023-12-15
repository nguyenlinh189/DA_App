package com.example.woody.dto;

public class ResponsePredict {
    private String des;
    private String prob;
    private String woodID;

    public ResponsePredict(String des, String prob, String woodID) {
        this.des = des;
        this.prob = prob;
        this.woodID = woodID;
    }

    public ResponsePredict() {
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getProb() {
        return prob;
    }

    public void setProb(String prob) {
        this.prob = prob;
    }

    public String getWoodID() {
        return woodID;
    }

    public void setWoodID(String woodID) {
        this.woodID = woodID;
    }
}
