package com.est.musicreader.dto.yt;

import java.util.ArrayList;


public class ytTrackDTO {    
    private ArrayList<ytSnippetDTO> items;
    private String nextPageToken;

    public ArrayList<ytSnippetDTO> getItems(){
        return items;
    }
    public void setItems(ArrayList<ytSnippetDTO> items){
        this.items = items;
    }   
    
    public boolean getPage(){
        return nextPageToken != null;
    }
    
    public String getNextPageToken(){
        return nextPageToken;
    } 
}
