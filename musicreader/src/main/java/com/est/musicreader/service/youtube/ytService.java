package com.est.musicreader.service.youtube;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;


import com.est.musicreader.models.ytSongsInfoDTO;
import com.est.musicreader.models.ytTrackDTO;
import reactor.core.publisher.Mono;


@Service
public class ytService {
    private final WebClient webClient;
    @Value("${api.ytreader.key}")
    private String ytKey;

    public ytService(@Qualifier("youtubeWebClient")WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<ytSongsInfoDTO>> getSongs(String id) {
        return getPageToken(id, null).expand(
            response -> {
                if (response.getPage()){
                    String page = response.getNextPageToken();
                    return getPageToken(id, page);                   
                }else{
                    return Mono.empty();
                }
            }
        ).flatMapIterable(map -> map.getItems())
                    .map(items -> {                        
                        return new ytSongsInfoDTO(items.getSnippet().getTitle(), items.getSnippet().getVideoOwnerChannelTitle());
                    }).collectList();
    }

    private Mono<ytTrackDTO> getPageToken(String id, String page) {
        return webClient.get().uri(uriBuilder -> {
            uriBuilder.path("/playlistItems")
                    .queryParam("key", ytKey)
                    .queryParam("part", "snippet")
                    .queryParam("playlistId", id)
                    .queryParam("maxResults", 5);
            if ( page != null) {
                uriBuilder.queryParam("pageToken", page);
            }
            return uriBuilder.build();
        }).retrieve()
                .bodyToMono(ytTrackDTO.class);
    }
}
