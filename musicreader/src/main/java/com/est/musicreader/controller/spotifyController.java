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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/spotify")
public class spotifyController {

    private final createPlaylist createPlaylist;

    public spotifyController(createPlaylist createPlaylist) {
        this.createPlaylist = createPlaylist;
    }

    @GetMapping("/create")
    public Mono<ResponseEntity<String>> create() {
        return createPlaylist.getAuthorizationCode()
                .map(url -> ResponseEntity
                        .status(HttpStatus.FOUND)
                        .location(URI.create(url))
                        .build());
    }

    @PostMapping("/callback")
    public Mono<ResponseEntity> getTokens(@RequestParam String code) {        
        return createPlaylist.getAccesTokens(code);
    }

    
    
    @GetMapping("/ping")
    public String ping() {
        return "OK SSL";
    }
    
}
