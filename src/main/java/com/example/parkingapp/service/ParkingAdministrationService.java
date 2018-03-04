package com.example.parkingapp.service;

import com.example.parkingapp.api.rest.exceptions.VehicleNotParkedException;
import com.example.parkingapp.api.rest.vm.ParkingStatisticsVM;
import com.example.parkingapp.api.rest.vm.VehicleStatusVM;
import com.example.parkingapp.config.ParkingRecordStatus;
import com.example.parkingapp.domain.ParkingRecord;
import com.example.parkingapp.repository.ParkingRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ParkingAdministrationService {
    private final ParkingRecordRepository parkingRecordRepository;

    public ParkingAdministrationService(ParkingRecordRepository parkingRecordRepository) {
        this.parkingRecordRepository = parkingRecordRepository;
    }

    public ParkingStatisticsVM getEarningsForGivenDay(LocalDate day) {
        return new ParkingStatisticsVM(parkingRecordRepository.sumAllPaymentsWithinDate(day.atStartOfDay(), day.atStartOfDay().plusDays(1)));
    }

    public VehicleStatusVM checkVehicleStatus(String licensePlateNumber) {
        Optional<ParkingRecord> parkingRecord = parkingRecordRepository.findByLicensePlateAndStatus(licensePlateNumber, ParkingRecordStatus.STARTED);
        return parkingRecord.map(this::fromParkingRecord)
            .<VehicleNotParkedException>orElseThrow(() -> {
                throw new VehicleNotParkedException(licensePlateNumber);
            });
    }

    private VehicleStatusVM fromParkingRecord(ParkingRecord record) {
        VehicleStatusVM vehicleStatusVM = new VehicleStatusVM();
        vehicleStatusVM.setStartDate(record.getId().getStartDate());
        vehicleStatusVM.setLicensePlate(record.getId().getLicensePlate());
        return vehicleStatusVM;
    }
}
