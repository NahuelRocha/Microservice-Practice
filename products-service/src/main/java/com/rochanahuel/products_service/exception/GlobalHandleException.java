package com.rochanahuel.products_service.exception;

import com.rochanahuel.products_service.dto.ProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("Error: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> resp = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldError = ((FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();

            resp.put(fieldError, message);
        });

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
