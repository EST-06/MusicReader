package com.est.musicreader.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.est.musicreader.dto.error.ErrorDTO;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorDTO>> internalError(Exception ex){
        ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value()
        , "Internal Error in: " + ex.getClass().getName()
        , ex.getMessage().toString());

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error));
    }

    
}
