package com.example.demo.exception;

public class InternalRetryException extends Exception {

    public InternalRetryException(String message) {
        super(message);
    }

    public InternalRetryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalRetryException(Throwable cause) {
        super(cause);
    }
}
