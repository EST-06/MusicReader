package com.est.musicreader.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.est.musicreader.models.ytSongsInfoDTO;
import com.est.musicreader.service.youtube.ytService;

import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/yt")
public class ytController {
    private final ytService ytService;

    public ytController(ytService ytService){        
        this.ytService = ytService;
    }    
    @GetMapping("/playlist/{id}")
    public Mono<List<ytSongsInfoDTO>> getPlayList(@PathVariable("id") String id){
        return ytService.getSongs(id);
    }
    
}
