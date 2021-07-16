package com.rest.ai.myCallimo.exception.role;

public class RoleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8241559467843570245L;

    public RoleNotFoundException() {
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}
