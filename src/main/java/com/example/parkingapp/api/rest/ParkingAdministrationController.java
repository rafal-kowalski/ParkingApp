package com.example.parkingapp.api.rest;

import com.example.parkingapp.api.rest.vm.ParkingStatisticsVM;
import com.example.parkingapp.api.rest.vm.VehicleStatusVM;
import com.example.parkingapp.service.ParkingAdministrationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/administration")
public class ParkingAdministrationController {
    private final ParkingAdministrationService parkingAdministrationService;

    public ParkingAdministrationController(ParkingAdministrationService parkingAdministrationService) {
        this.parkingAdministrationService = parkingAdministrationService;
    }

    @RequestMapping(value = "/earnings", method = RequestMethod.GET)
    public ParkingStatisticsVM getEarningsForGivenDay(
        @RequestParam(name = "date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return parkingAdministrationService.getEarningsForGivenDay(date);
    }

    @RequestMapping(value = "/{licensePlateNumber}/status", method = RequestMethod.GET)
    public VehicleStatusVM getParkedVehicleStatus(@PathVariable String licensePlateNumber) {
        return parkingAdministrationService.checkVehicleStatus(licensePlateNumber);
    }
}
