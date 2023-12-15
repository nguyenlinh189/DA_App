package com.example.woody.model;

import java.io.Serializable;

public class Glossary implements Serializable {
    private int id;
    private String vietnamese;
    private String english;
    private String definition;
    private String image;

    public Glossary(int id, String vietnamese, String english, String definition, String image) {
        this.id = id;
        this.vietnamese = vietnamese;
        this.english = english;
        this.definition = definition;
        this.image = image;
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

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String toString(){
        return this.getVietnamese();
    }
}
