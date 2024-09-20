package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@NoArgsConstructor
public class InternalRollBackableException extends RuntimeException {

    public InternalRollBackableException(String message) {
        super(message);
    }

    public InternalRollBackableException(String message, Throwable cause) {
        super(message, cause);
    }
}
