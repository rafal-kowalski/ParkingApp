package com.example.parkingapp.api.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class VehicleAlreadyParkedException extends RuntimeException {
    public VehicleAlreadyParkedException(String licenseNumber) {
        super("Vehicle with license plate number '" + licenseNumber + "' is already parked");
    }
}
