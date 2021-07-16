package com.rest.ai.myCallimo.exception.user;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3212198807166477661L;

    public UserNotFoundException() {
        super();
    }


    public UserNotFoundException(String message) {
        super(message);
    }
}
