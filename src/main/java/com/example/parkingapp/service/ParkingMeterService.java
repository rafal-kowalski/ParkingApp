package com.example.parkingapp.service;

import com.example.parkingapp.api.rest.exceptions.VehicleAlreadyParkedException;
import com.example.parkingapp.api.rest.exceptions.VehicleNotParkedException;
import com.example.parkingapp.api.rest.exceptions.WrongPinException;
import com.example.parkingapp.api.rest.exceptions.WrongVehicleOwnerException;
import com.example.parkingapp.api.rest.vm.LicensePlateVM;
import com.example.parkingapp.api.rest.vm.ParkingMeterStartedVM;
import com.example.parkingapp.api.rest.vm.VehicleStatusVM;
import com.example.parkingapp.config.AcceptedCurrencies;
import com.example.parkingapp.config.ParkingRecordStatus;
import com.example.parkingapp.domain.ParkingRecord;
import com.example.parkingapp.domain.ParkingRecordID;
import com.example.parkingapp.domain.User;
import com.example.parkingapp.repository.ParkingRecordRepository;
import com.example.parkingapp.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class ParkingMeterService {
    private static final Logger log = LoggerFactory.getLogger(ParkingMeterService.class);

    private final UserRepository userRepository;
    private final ParkingRecordRepository parkingRecordRepository;
    private final PasswordEncoder passwordEncoder;
    private final ParkingCostCalculator costCalculator;

    public ParkingMeterService(UserRepository userRepository, ParkingRecordRepository parkingRecordRepository, PasswordEncoder passwordEncoder, ParkingCostCalculator costCalculator) {
        this.userRepository = userRepository;
        this.parkingRecordRepository = parkingRecordRepository;
        this.passwordEncoder = passwordEncoder;
        this.costCalculator = costCalculator;
    }

    /**
     * Starts the parking meter
     */
    public ParkingMeterStartedVM startParkingMeter(String licenseNumber) {
        Optional<ParkingRecord> alreadyParked = parkingRecordRepository.findByLicensePlateAndStatus(licenseNumber, ParkingRecordStatus.STARTED);
        alreadyParked.map(pr -> {
            throw new VehicleAlreadyParkedException(licenseNumber);
        });

        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .map(user -> startForAuthenticated(licenseNumber, user))
            .orElseGet(() -> startForAnonymous(licenseNumber));
    }

    /**
     * Stops parking meter
     */
    public VehicleStatusVM stopParkingMeter(LicensePlateVM license, String currencyUnit) {
        ParkingRecord parkingRecord = getParkingRecord(license);

        raiseExceptionIfNotAuthorized(license, parkingRecord);

        parkingRecord.setEndDate(LocalDateTime.now());
        parkingRecord.setStatus(ParkingRecordStatus.STOPPED);
        parkingRecord.setCurrency(AcceptedCurrencies.valueOf(currencyUnit));

        long between = ChronoUnit.HOURS.between(parkingRecord.getId().getStartDate(), parkingRecord.getEndDate());
        BigDecimal totalCost = costCalculator.calculateCost(between, parkingRecord.getCurrency(), parkingRecord.getUser());
        parkingRecord.setPayment(totalCost);

        ParkingRecord savedRecord = parkingRecordRepository.save(parkingRecord);

        VehicleStatusVM vehicleStatus = new VehicleStatusVM();
        vehicleStatus.setLicensePlate(savedRecord.getId().getLicensePlate());
        vehicleStatus.setStartDate(savedRecord.getId().getStartDate());
        vehicleStatus.setEndDate(savedRecord.getEndDate());
        vehicleStatus.setCurrencyUnit(savedRecord.getCurrency());
        vehicleStatus.setParkingCost(savedRecord.getPayment());
        vehicleStatus.setParkingTimeInHours(between);

        return vehicleStatus;
    }

    /**
     * Checks vehicle details
     */
    public VehicleStatusVM checkVehicleDetails(LicensePlateVM license, String currencyUnit) {
        ParkingRecord parkingRecord = getParkingRecord(license);

        raiseExceptionIfNotAuthorized(license, parkingRecord);

        VehicleStatusVM vehicleStatus = new VehicleStatusVM();
        vehicleStatus.setLicensePlate(parkingRecord.getId().getLicensePlate());
        vehicleStatus.setStartDate(parkingRecord.getId().getStartDate());
        vehicleStatus.setCurrencyUnit(AcceptedCurrencies.valueOf(currencyUnit));

        long between = ChronoUnit.HOURS.between(vehicleStatus.getStartDate(), LocalDateTime.now());

        vehicleStatus.setParkingTimeInHours(between);

        BigDecimal totalCost = costCalculator.calculateCost(between, AcceptedCurrencies.valueOf(currencyUnit), parkingRecord.getUser());
        vehicleStatus.setParkingCost(totalCost);

        return vehicleStatus;
    }

    /**
     * Returns parking record for given license plate if it exists, otherwise throws {@link com.example.parkingapp.api.rest.exceptions.VehicleNotParkedException}
     */
    private ParkingRecord getParkingRecord(LicensePlateVM license) {
        Optional<ParkingRecord> byLicensePlate = parkingRecordRepository.findByLicensePlateAndStatus(license.getNumber(), ParkingRecordStatus.STARTED);
        //had to explicitly pass exception type due to
        //'Error:(59, 65) java: unreported exception java.lang.Throwable; must be caught or declared to be thrown'
        //Running java 1.8.0_162
        return byLicensePlate.<VehicleNotParkedException>orElseThrow(() -> {
            throw new VehicleNotParkedException(license.getNumber());
        });
    }

    /**
     * Throws exception if user is not authorized to modify given parked vehicle
     */
    private void raiseExceptionIfNotAuthorized(LicensePlateVM license, ParkingRecord parkingRecord) {
        if (SecurityUtils.isAuthenticated()) {
            boolean isOwner = SecurityUtils.getCurrentUserLogin()
                .filter(login -> login.equals(parkingRecord.getUser().getLogin()))
                .isPresent();
            if (!isOwner) {
                throw new WrongVehicleOwnerException(license.getNumber(), SecurityUtils.getCurrentUserLogin().get());
            }
        } else {
            boolean isOwner = passwordEncoder.matches(license.getPinCode(), parkingRecord.getPinCode());
            if (!isOwner) {
                throw new WrongPinException();
            }
        }
    }

    /**
     * Starts parking meter for authenticated user
     */
    private ParkingMeterStartedVM startForAuthenticated(String licenseNumber, User user) {
        ParkingRecord parkingRecord = new ParkingRecord();
        ParkingMeterStartedVM meterStartedVM = new ParkingMeterStartedVM();

        setCommonFields(parkingRecord, meterStartedVM, licenseNumber);

        parkingRecord.setUser(user);

        parkingRecordRepository.save(parkingRecord);

        return meterStartedVM;
    }

    /**
     * Starts parking meter for anonymous user attaching PIN code to response
     */
    private ParkingMeterStartedVM startForAnonymous(String licenseNumber) {
        ParkingRecord parkingRecord = new ParkingRecord();
        ParkingMeterStartedVM meterStartedVM = new ParkingMeterStartedVM();
        String pinCode = RandomStringUtils.randomNumeric(5); //generate pin code for anonymous access

        setCommonFields(parkingRecord, meterStartedVM, licenseNumber);

        parkingRecord.setPinCode(passwordEncoder.encode(pinCode));
        meterStartedVM.setPinCode(pinCode);

        parkingRecordRepository.save(parkingRecord);

        return meterStartedVM;
    }

    /**
     * Sets fields common to both authenticated and anonymous users
     */
    private void setCommonFields(ParkingRecord parkingRecord, ParkingMeterStartedVM meterStartedVM, String licenseNumber) {
        ParkingRecordID id = new ParkingRecordID();
        LocalDateTime now = LocalDateTime.now();

        id.setLicensePlate(licenseNumber);
        id.setStartDate(now);
        parkingRecord.setId(id);
        parkingRecord.setStatus(ParkingRecordStatus.STARTED);

        meterStartedVM.setLicensePlateNumber(licenseNumber);
        meterStartedVM.setStartDate(now);
    }
}
