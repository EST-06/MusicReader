package com.est.musicreader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/yt")
public class ytController {
    
    @GetMapping("/getPlayList/{id}")
    public void getPlayList(@PathVariable("id") String id){
        
    }
}
