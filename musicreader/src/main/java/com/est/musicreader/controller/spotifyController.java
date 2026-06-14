package com.est.musicreader.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.est.musicreader.dto.spotify.AccessResponseDTO;
import com.est.musicreader.service.spotify.OAuthSpotify;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/spotify")
public class spotifyController {

    private final OAuthSpotify oauthSpotify;

    public spotifyController(OAuthSpotify createPlaylist) {
        this.oauthSpotify = createPlaylist;
    }

    @GetMapping("/authorize")
    public Mono<ResponseEntity<String>> authorize() throws NoSuchAlgorithmException {
        return oauthSpotify.getAuthorizationCode()
                .map(url -> ResponseEntity
                        .status(HttpStatus.FOUND)
                        .location(URI.create(url))
                        .build());
    }

    @GetMapping("/callback")
    public Mono<AccessResponseDTO> getTokens(@RequestParam String code) {        
        return oauthSpotify.getAccesTokens(code);
    }

    @PostMapping("/create")
    public String create(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
}
