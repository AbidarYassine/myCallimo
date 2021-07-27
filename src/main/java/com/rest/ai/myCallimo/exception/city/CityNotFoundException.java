package com.rest.ai.myCallimo.exception.city;

public class CityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5022517962213434845L;

    public CityNotFoundException() {
    }

    public CityNotFoundException(String message) {
        super(message);
    }
}
