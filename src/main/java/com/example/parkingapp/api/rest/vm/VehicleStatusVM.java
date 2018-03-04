package com.example.parkingapp.api.rest.vm;

import com.example.parkingapp.config.AcceptedCurrencies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * View Model object for storing parked vehicle status
 */
public class VehicleStatusVM {
    private String licensePlate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long parkingTimeInHours;
    private BigDecimal parkingCost;
    private AcceptedCurrencies currencyUnit;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public long getParkingTimeInHours() {
        return parkingTimeInHours;
    }

    public void setParkingTimeInHours(long parkingTimeInHours) {
        this.parkingTimeInHours = parkingTimeInHours;
    }

    public BigDecimal getParkingCost() {
        return parkingCost;
    }

    public void setParkingCost(BigDecimal parkingCost) {
        this.parkingCost = parkingCost;
    }

    public AcceptedCurrencies getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(AcceptedCurrencies currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleStatusVM that = (VehicleStatusVM) o;
        return parkingTimeInHours == that.parkingTimeInHours &&
            Objects.equals(licensePlate, that.licensePlate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(parkingCost, that.parkingCost) &&
            Objects.equals(currencyUnit, that.currencyUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, startDate, endDate, parkingTimeInHours, parkingCost, currencyUnit);
    }

    @Override
    public String toString() {
        return "VehicleStatusVM{" +
            "licensePlate='" + licensePlate + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", parkingTimeInHours=" + parkingTimeInHours +
            ", parkingCost=" + parkingCost +
            ", currencyUnit='" + currencyUnit + '\'' +
            '}';
    }
}
