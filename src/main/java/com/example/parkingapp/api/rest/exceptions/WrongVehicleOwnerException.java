package com.example.parkingapp.api.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class WrongVehicleOwnerException extends RuntimeException {
    public WrongVehicleOwnerException(String license, String owner) {
        super("Vehicle with license number '" + license + "' is not owned by '" + owner + "'");
    }
}
