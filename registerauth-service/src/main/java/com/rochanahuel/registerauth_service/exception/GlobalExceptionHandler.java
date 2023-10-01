package com.rochanahuel.registerauth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("Error: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<Map<String, String>> handleEmailInUseException(EmailInUseException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("Error: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> resp = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    final String fieldError = ((FieldError) error).getField();
                    final String message = error.getDefaultMessage();

                    resp.put(fieldError, message);
                }
        );

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(BadCredentialsException ex) {

        Map<String, String> resp = new HashMap<>();

        resp.put("Error: ", ex.getMessage());

        return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
    }


}
