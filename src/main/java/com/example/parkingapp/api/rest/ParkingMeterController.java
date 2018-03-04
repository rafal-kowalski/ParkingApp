package com.example.parkingapp.api.rest;

import com.example.parkingapp.api.rest.vm.LicensePlateVM;
import com.example.parkingapp.api.rest.vm.ParkingMeterStartedVM;
import com.example.parkingapp.api.rest.vm.VehicleStatusVM;
import com.example.parkingapp.service.ParkingMeterService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/parking-meter")
public class ParkingMeterController {
    private final ParkingMeterService parkingMeterService;

    public ParkingMeterController(ParkingMeterService parkingMeterService) {
        this.parkingMeterService = parkingMeterService;
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ParkingMeterStartedVM startParkingMeter(@RequestBody @Valid LicensePlateVM license) {
        return parkingMeterService.startParkingMeter(license.getNumber());
    }

    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public VehicleStatusVM stopParkingMeter(
        @RequestBody @Valid LicensePlateVM license,
        @RequestParam(name = "currency", defaultValue = "PLN") String currency) {
        return parkingMeterService.stopParkingMeter(license, currency);
    }

    @RequestMapping(value = "/{licensePlateNumber}/details", method = RequestMethod.GET)
    public VehicleStatusVM getStatus(
        @PathVariable String licensePlateNumber,
        @RequestParam(name = "pin", required = false) String pin,
        @RequestParam(name = "currency", defaultValue = "PLN") String currency) {
        return parkingMeterService.checkVehicleDetails(new LicensePlateVM(licensePlateNumber, pin), currency);
    }
}
