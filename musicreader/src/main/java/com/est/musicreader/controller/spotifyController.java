package com.est.musicreader.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.est.musicreader.service.spotify.createPlaylist;

import reactor.core.publisher.Mono;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/spotify")
public class spotifyController {

    private final createPlaylist createPlaylist;

    public spotifyController(createPlaylist createPlaylist) {
        this.createPlaylist = createPlaylist;
    }

    @GetMapping("/create")
    public Mono<ResponseEntity<String>> create() {
        return createPlaylist.getAuthorizationUrl()
                .map(url -> ResponseEntity
                        .status(HttpStatus.FOUND)
                        .location(URI.create(url))
                        .build());
    }

    @GetMapping("/callback")
    public String getMethodName(@RequestParam String code) {        
        return code;
    }
    @GetMapping("/ping")
    public String ping() {
        return "OK SSL";
    }
    
}
