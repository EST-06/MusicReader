package com.est.musicreader.service.spotify;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

@Service
public class createPlaylist {
    private final WebClient webClient;

    @Value("${api.spotify.client.id}")
    private String clientID;
    @Value("${api.spotify.client.secret}")
    private String clientSecret;
    @Value("${api.spotify.redirect.uri}")
    private String redirectUri;

    public createPlaylist(@Qualifier("spotifyWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    private String b64(String sha256) {
        return Base64.getEncoder().encodeToString(sha256.getBytes(StandardCharsets.UTF_8));
    }

    private Mono<String> s256() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        var random = new Random();

        return Mono.fromSupplier(() -> DigestUtils.sha256Hex(random.ints(100, 0, chars.length())
                .mapToObj(chars::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString()));
    }

    public Mono<String> getAuthorizationUrl() {
        return s256().map(data -> {
            String code = b64(data);
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

    public void getTokens(String code){
         
    }
}
