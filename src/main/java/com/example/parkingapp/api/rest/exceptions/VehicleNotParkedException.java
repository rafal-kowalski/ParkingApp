package com.example.parkingapp.api.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VehicleNotParkedException extends RuntimeException {
    public VehicleNotParkedException(String licenseNumber) {
        super("Vehicle with license plate number '" + licenseNumber + "' is not parked");
    }
}
