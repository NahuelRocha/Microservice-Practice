package com.rochanahuel.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleExceptions {

    @ExceptionHandler(MissingTheTokenException.class)
    public ResponseEntity<Map<String, String>> handleMissingTheTokenException(MissingTheTokenException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("Error: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<Map<String, String>> handleTokenInvalidException(TokenInvalidException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("Error: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
    }
}
