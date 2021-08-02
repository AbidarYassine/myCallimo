package com.rest.ai.myCallimo.exception.offre;

public class AlreadyAffectedException extends RuntimeException{
    public AlreadyAffectedException() {
    }

    public AlreadyAffectedException(String message) {
        super(message);
    }
}
