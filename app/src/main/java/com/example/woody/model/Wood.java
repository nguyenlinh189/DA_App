package com.example.woody.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wood implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("vietnameName")
    @Expose
    private String vietnameName;
    @SerializedName("scientificName")
    @Expose
    private String scientificName;
    @SerializedName("commercialName")
    @Expose
    private String commercialName;
    @SerializedName("preservation")
    @Expose
    private Preservation preservation;
    @SerializedName("family")
    @Expose
    private PlantFamily family;
    @SerializedName("categoryWood")
    @Expose
    private CategoryWood categoryWood;
    @SerializedName("listImage")
    @Expose
    private List<Image> listImage=new ArrayList<>();
    @SerializedName("specificGravity")
    @Expose
    private int specificGravity;
    @SerializedName("characteristic")
    @Expose
    private String characteristic;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("appendixCites")
    @Expose
    private AppendixCITES appendixCites;
    @SerializedName("listAreas")
    @Expose
    private List<GeographicalArea> listAreas = new ArrayList<GeographicalArea>();
    @SerializedName("color")
    @Expose
    private String color;

    public Wood(int id, String vietnameName, String scientificName, String commercialName, Preservation preservation, PlantFamily family, List<Image> listImage, int specificGravity, String characteristic, String note, AppendixCITES appendixCites, List<GeographicalArea> listAreas, String color) {
        this.id = id;
        this.vietnameName = vietnameName;
        this.scientificName = scientificName;
        this.commercialName = commercialName;
        this.preservation = preservation;
        this.family = family;
        this.listImage = listImage;
        this.specificGravity = specificGravity;
        this.characteristic = characteristic;
        this.note = note;
        this.appendixCites = appendixCites;
        this.listAreas = listAreas;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVietnameName() {
        return vietnameName;
    }

    public void setVietnameName(String vietnameName) {
        this.vietnameName = vietnameName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public Preservation getPreservation() {
        return preservation;
    }

    public void setPreservation(Preservation preservation) {
        this.preservation = preservation;
    }

    public PlantFamily getFamily() {
        return family;
    }

    public void setFamily(PlantFamily family) {
        this.family = family;
    }

    public List<Image> getListImage() {
        return listImage;
    }

    public void setListImage(List<Image> listImage) {
        this.listImage = listImage;
    }

    public int getSpecificGravity() {
        return specificGravity;
    }

    public void setSpecificGravity(int specificGravity) {
        this.specificGravity = specificGravity;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AppendixCITES getAppendixCites() {
        return appendixCites;
    }

    public void setAppendixCites(AppendixCITES appendixCites) {
        this.appendixCites = appendixCites;
    }

    public List<GeographicalArea> getListAreas() {
        return listAreas;
    }

    public void setListAreas(List<GeographicalArea> listAreas) {
        this.listAreas = listAreas;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
