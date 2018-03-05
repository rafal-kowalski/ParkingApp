package com.example.parkingapp.api.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class WrongPinException extends RuntimeException {
    public WrongPinException() {
        super("Provided PIN is incorrect");
    }
}
