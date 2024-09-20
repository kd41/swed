package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MainNotFoundException extends RuntimeException {

    public MainNotFoundException(String message) {
        super(message);
    }

    public MainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
