package com.rest.ai.myCallimo.exception.offre;

public class OffreNotFoundException extends RuntimeException{
    public OffreNotFoundException() {
    }

    public OffreNotFoundException(String message) {
        super(message);
    }
}
