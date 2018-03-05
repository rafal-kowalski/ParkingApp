package com.example.parkingapp.api.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CurrencyNotSupportedException extends RuntimeException {
    public CurrencyNotSupportedException(String currency) {
        super("Currency '" + currency + "' is currently not supported");
    }
}
