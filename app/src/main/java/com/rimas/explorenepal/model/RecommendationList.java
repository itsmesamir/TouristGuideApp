package com.rimas.explorenepal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecommendationList {


    @SerializedName("id")



    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("lat")
    @Expose
    private Double lat;

    @SerializedName("long")
    @Expose
    private Double _long;

    @SerializedName("image")
    @Expose
    private String image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLong() {
        return _long;
    }

    public void setLong(Double _long) {
        this._long = _long;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}

