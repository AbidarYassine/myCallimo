package com.rest.ai.myCallimo.exception.role;

public class RoleAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 8241559467843570245L;

    public RoleAlreadyExistsException() {
    }

    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
