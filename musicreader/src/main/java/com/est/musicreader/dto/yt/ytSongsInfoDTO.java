package com.est.musicreader.dto.yt;

public class ytSongsInfoDTO {
    private String title;
    private String videoOwnerChannelTitle;

    public ytSongsInfoDTO(String song, String artist) {
        this.title = song;
        this.videoOwnerChannelTitle = artist;
    }

    public String getVideoOwnerChannelTitle() {
        return videoOwnerChannelTitle;
    }
    
    public String getTitle() {
        return title;
    }
}
