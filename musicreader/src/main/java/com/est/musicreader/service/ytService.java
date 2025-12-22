package com.est.musicreader.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ytService {
    private WebClient webClient;

    public ytService(WebClient.Builder webclient) {
        this.webClient = webclient.baseURL;
    }

    public Mono<ytTrackDTO> getSongs(String id) {

    }
}
