package com.est.musicreader.models;

public class ytSongsInfoDTO {
    private String song;
    private String artist;

    public void setSong(String song){
        this.song = song;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }
    public String getArtist(){
        return artist;
    }
    public String getSong(){
        return song;
    }
}
