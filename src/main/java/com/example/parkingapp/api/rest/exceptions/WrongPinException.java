package com.example.parkingapp.api.rest.exceptions;

public class WrongPinException extends RuntimeException {
    public WrongPinException() {
        super("Provided PIN is incorrect");
    }
}
