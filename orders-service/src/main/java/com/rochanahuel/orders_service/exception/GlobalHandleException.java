package com.rochanahuel.orders_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(OrderProcessingException.class)
    public ResponseEntity<Map<String, String>> handleOrderProcessingException(OrderProcessingException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("Error: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("Error: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }
}
