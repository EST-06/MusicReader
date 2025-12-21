package com.est.musicreader.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ytAPI {                    
    private final WebClient webClient;

    public ytAPI(WebClient.Builder webClient){
        this.webClient = webClient.baseUrl("https://www.googleapis.com/youtube/v3/playlistItems").build();
    }
    
    public Mono<ytSongsInfoDTO> getSongs(String id){
        return this.webClient.get()
        .uri("list_id", id )
        .
        .retrieve();
    }
}





/*Porque te tatuatis pus no mas*/
 