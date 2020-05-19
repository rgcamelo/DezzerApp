package com.example.parcial2movil;

import com.google.gson.annotations.SerializedName;

public class Artista {

    @SerializedName("name")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
