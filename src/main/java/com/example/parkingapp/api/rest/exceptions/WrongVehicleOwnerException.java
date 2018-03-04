package com.example.parkingapp.api.rest.exceptions;

public class WrongVehicleOwnerException extends RuntimeException {
    public WrongVehicleOwnerException(String license, String owner) {
        super("Vehicle with license number '" + license + "' is not owned by '" + owner + "'");
    }
}
