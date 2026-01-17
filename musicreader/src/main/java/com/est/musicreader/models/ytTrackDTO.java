package com.est.musicreader.models;

import java.util.ArrayList;


public class ytTrackDTO {    
    private ArrayList<ytSnippet> items;
    private String nextPageToken;

    public ArrayList<ytSnippet> getItems(){
        return items;
    }
    public void setItems(ArrayList<ytSnippet> items){
        this.items = items;
    }   
    
    public boolean getPage(){
        return nextPageToken != null;
    }
    
    public String getNextPageToken(){
        return nextPageToken;
    }

   
}
