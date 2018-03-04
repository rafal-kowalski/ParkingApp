package com.example.parkingapp.api.rest.exceptions;

public class VehicleAlreadyParkedException extends RuntimeException {
    public VehicleAlreadyParkedException(String licenseNumber) {
        super("Vehicle with license plate number '" + licenseNumber + "' is already parked");
    }
}
