package com.est.musicreader.service.spotify;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.est.musicreader.dto.spotify.AccessResponseDTO;
import com.est.musicreader.handler.error.BadHashException;
import com.est.musicreader.repository.RedisRepo;

import reactor.core.publisher.Mono;

@Service
public class OAuthSpotify {
    private final WebClient webClient;
    private final RedisRepo redisRepo;

    @Value("${api.spotify.client.id}")
    private String clientID;
    @Value("${api.spotify.client.secret}")
    private String clientSecret;
    @Value("${api.spotify.redirect.uri}")
    private String redirectUri;

    public OAuthSpotify(@Qualifier("spotifyWebClient") WebClient webClient, RedisRepo redisRepo) {
        this.webClient = webClient;
        this.redisRepo = redisRepo;
    }

    private Mono<byte[]> sha256Encoder() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        var random = new Random();
        String codeVerifier = random.ints(100, 0, chars.length())
                .mapToObj(chars::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        redisRepo.saveWithTimeout("Code_Verifier", codeVerifier, 180);
        try {
            var digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            return Mono.just(hashed);
        } catch (Exception e) {
            throw new BadHashException("Digest with SHA-256 had a bad performance");
        }        
        
    }

    private String base64Encoder(byte[] sha256) {
        String codeChallenge = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(sha256);
        return codeChallenge;
    }

    public Mono<AccessResponseDTO> getAccesTokens(String code) {
        String codeVerifier = redisRepo.get("Code_Verifier");
        return webClient.post().uri("/token").header("Content-Type", "application/x-www-form-urlencoded")
                .body(
                        BodyInserters.fromFormData("grant_type", "authorization_code")
                                .with("code", code)
                                .with("redirect_uri", redirectUri)
                                .with("client_id", clientID)
                                .with("code_verifier", codeVerifier))
                .retrieve().bodyToMono(AccessResponseDTO.class)
                .map(body -> {
                    String acces_Token = body.getAccess_token();
                    Integer timeout = body.getExpires_in(); 
                    
                    redisRepo.saveWithTimeout("access_token", acces_Token, timeout);
                    String refresh_Token = body.getRefresh_token();
                    
                    redisRepo.save("refresh_token", refresh_Token);
                    
                    return body;
                });
    }

    public Mono<String> getAuthorizationCode() {
        return sha256Encoder().map(data -> {
            String code = base64Encoder(data);
            return UriComponentsBuilder.fromUriString("https://accounts.spotify.com/authorize")
                    .queryParam("client_id", clientID)
                    .queryParam("response_type", "code")
                    .queryParam("redirect_uri", redirectUri)
                    .queryParam("code_challenge_method", "S256")
                    .queryParam("code_challenge", code)
                    .build()
                    .toUriString();
        });
    }

}
