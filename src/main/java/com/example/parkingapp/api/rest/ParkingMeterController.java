package com.example.parkingapp.api.rest;

import com.example.parkingapp.api.rest.vm.LicensePlateVM;
import com.example.parkingapp.api.rest.vm.ParkingMeterStartedVM;
import com.example.parkingapp.api.rest.vm.VehicleStatusVM;
import com.example.parkingapp.service.ParkingMeterService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for managing parking meter
 */
@RestController
@RequestMapping("/api/parking-meter")
public class ParkingMeterController {
    private final ParkingMeterService parkingMeterService;

    public ParkingMeterController(ParkingMeterService parkingMeterService) {
        this.parkingMeterService = parkingMeterService;
    }

    /**
     * Starts the parking meter for given vehicle
     * @param license contains license number belonging to vehicle
     * @return details for parked vehicle
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ParkingMeterStartedVM startParkingMeter(@RequestBody @Valid LicensePlateVM license) {
        return parkingMeterService.startParkingMeter(license.getNumber());
    }

    /**
     * Stops the parking meter for given vehicle
     * @param license vehicle license number
     * @param currency payment currency
     * @return parking summary for given vehicle
     */
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public VehicleStatusVM stopParkingMeter(
        @RequestBody @Valid LicensePlateVM license,
        @RequestParam(name = "currency", defaultValue = "PLN") String currency) {
        return parkingMeterService.stopParkingMeter(license, currency);
    }

    /**
     * Gets parking details for given vehicle
     * @param licensePlateNumber vehicle license number
     * @param pin pin code for authentication in case of anonymous users
     * @param currency payment currency
     * @return parking summary for given vehicle
     */
    @RequestMapping(value = "/{licensePlateNumber}/details", method = RequestMethod.GET)
    public VehicleStatusVM getParkedVehicleDetails(
        @PathVariable String licensePlateNumber,
        @RequestParam(name = "pin", required = false) String pin,
        @RequestParam(name = "currency", defaultValue = "PLN") String currency) {
        return parkingMeterService.checkVehicleDetails(new LicensePlateVM(licensePlateNumber, pin), currency);
    }
}
