package com.est.musicreader.service.spotify;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class CreatePlaylist {

    private final WebClient webClient;

    public CreatePlaylist(@Qualifier("spotifyWebClient") WebClient webClient){
        this.webClient = webClient;
    }
    
    // public Mono<ResponseEntity<Object>> createPlaylist(){
    //     return webClient.post().body(
            
    //     )
    //     return null;
    // }
}
