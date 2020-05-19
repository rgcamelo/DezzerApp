package com.example.parcial2movil;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cancion implements Serializable {

    @SerializedName("title")
    String title;

    Artista artist;

    Album album;

    @SerializedName("duration")
    String duration;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Artista getArtist() {
        return artist;
    }

    public void setArtist(Artista artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
