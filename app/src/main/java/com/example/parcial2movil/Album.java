package com.example.parcial2movil;

import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
