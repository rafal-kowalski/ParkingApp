package com.example.parkingapp.api.rest.exceptions;

public class CurrencyNotSupportedException extends RuntimeException {
    public CurrencyNotSupportedException(String currency) {
        super("Currency '" + currency + "' is currently not supported");
    }
}
