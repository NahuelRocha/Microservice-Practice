package com.rochanahuel.gateway.exception;


public class MissingTheTokenException extends RuntimeException {

    public MissingTheTokenException(String message) {
        super(message);
    }
}
