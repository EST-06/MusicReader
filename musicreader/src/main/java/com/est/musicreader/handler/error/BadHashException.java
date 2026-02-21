package com.est.musicreader.handler.error;

public class BadHashException extends RuntimeException{
    public BadHashException(String message){
        super(message);
    }
    
}
