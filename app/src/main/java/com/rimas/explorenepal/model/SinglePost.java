package com.rimas.explorenepal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SinglePost {


    @SerializedName("id")



    @Expose
    private int id;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

