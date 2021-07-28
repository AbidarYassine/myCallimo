package com.rest.ai.myCallimo.exception.jwt;

public class InvalidJwtException extends RuntimeException {
    private static final long serialVersionUID = 8241559467843570245L;

    public InvalidJwtException() {
    }

    public InvalidJwtException(String message) {
        super(message);
    }
}
