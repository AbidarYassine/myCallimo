package com.rest.ai.myCallimo.exception.user;

public class UnAuthorizationUser extends RuntimeException {


    private static final long serialVersionUID = -9114529315171400643L;

    public UnAuthorizationUser() {
    }

    public UnAuthorizationUser(String message) {
        super(message);
    }
}
