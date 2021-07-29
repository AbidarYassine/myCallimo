package com.rest.ai.myCallimo.exception;

public class InvalidOperationException extends RuntimeException{
    public InvalidOperationException() {
    }

    public InvalidOperationException(String message) {
        super(message);
    }
}
