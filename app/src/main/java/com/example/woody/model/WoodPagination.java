package com.example.woody.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WoodPagination implements Serializable {
    @SerializedName("totalPages")
    @Expose
    private int totalPages;
    @SerializedName("totalElements")
    @Expose
    private long totalElements;
    @SerializedName("content")
    @Expose
    private List<Wood>content=new ArrayList<>();

    public WoodPagination(int totalPages, long totalElements, List<Wood> content) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<Wood> getContent() {
        return content;
    }

    public void setContent(List<Wood> content) {
        this.content = content;
    }
}
