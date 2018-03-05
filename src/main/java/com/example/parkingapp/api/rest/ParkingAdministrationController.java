package com.example.parkingapp.api.rest;

import com.example.parkingapp.api.rest.vm.ParkingStatisticsVM;
import com.example.parkingapp.api.rest.vm.VehicleStatusVM;
import com.example.parkingapp.service.ParkingAdministrationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controller for managing parking
 */
@RestController
@RequestMapping("/api/administration")
public class ParkingAdministrationController {
    private final ParkingAdministrationService parkingAdministrationService;

    public ParkingAdministrationController(ParkingAdministrationService parkingAdministrationService) {
        this.parkingAdministrationService = parkingAdministrationService;
    }

    /**
     * Calculates earnings made in given day
     * @param date day to calculate earnings for
     * @return earnings made in given day
     */
    @RequestMapping(value = "/earnings", method = RequestMethod.GET)
    public ParkingStatisticsVM getEarningsForGivenDay(
        @RequestParam(name = "date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return parkingAdministrationService.getEarningsForGivenDay(date);
    }

    /**
     * Checks if vehicle with given license number is currently parked
     * If vehicle is found it returns basic information about vehicle otherwise it throws
     * {@link com.example.parkingapp.api.rest.exceptions.VehicleNotParkedException}
     * @param licensePlateNumber license number of vehicle to check
     * @return vehicle details
     */
    @RequestMapping(value = "/{licensePlateNumber}/status", method = RequestMethod.GET)
    public VehicleStatusVM getParkedVehicleStatus(@PathVariable String licensePlateNumber) {
        return parkingAdministrationService.checkVehicleStatus(licensePlateNumber);
    }
}
