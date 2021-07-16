package com.rest.ai.myCallimo.exception.user;

public class UserAlreadyExist extends RuntimeException {

    private static final long serialVersionUID = 5022517962213434845L;

    public UserAlreadyExist() {
    }

    public UserAlreadyExist(String message) {
        super(message);
    }
}
