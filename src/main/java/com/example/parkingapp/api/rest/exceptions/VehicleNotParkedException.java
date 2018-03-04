package com.example.parkingapp.api.rest.exceptions;

public class VehicleNotParkedException extends RuntimeException {
    public VehicleNotParkedException(String licenseNumber) {
        super("Vehicle with license plate number '" + licenseNumber + "' is not parked");
    }
}
