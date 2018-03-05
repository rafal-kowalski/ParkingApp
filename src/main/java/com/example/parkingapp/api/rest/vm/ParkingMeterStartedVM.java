package com.example.parkingapp.api.rest.vm;

import com.example.parkingapp.domain.ParkingRecord;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * View Model for initial response from parking meter when it was started
 */
public class ParkingMeterStartedVM {
    private String licensePlateNumber;
    private LocalDateTime startDate;
    private String pinCode; //used for authentication when driver was anonymous

    public ParkingMeterStartedVM() {
    }

    public ParkingMeterStartedVM(ParkingRecord parkingRecord, String pin) {
        this.licensePlateNumber = parkingRecord.getId().getLicensePlate();
        this.startDate = parkingRecord.getId().getStartDate();
        this.pinCode = pin;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingMeterStartedVM that = (ParkingMeterStartedVM) o;
        return Objects.equals(licensePlateNumber, that.licensePlateNumber) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(pinCode, that.pinCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlateNumber, startDate, pinCode);
    }

    @Override
    public String toString() {
        return "ParkingMeterStartedVM{" +
            "licensePlateNumber='" + licensePlateNumber + '\'' +
            ", startDate=" + startDate +
            ", pinCode='" + pinCode + '\'' +
            '}';
    }
}
