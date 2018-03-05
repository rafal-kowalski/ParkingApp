package com.example.parkingapp.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class ParkingRecordID implements Serializable {
    @NotNull
    @Column(name = "license_plate")
    private String licensePlate;

    @NotNull
    @Column(name = "start_date")
    private LocalDateTime startDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingRecordID that = (ParkingRecordID) o;
        return Objects.equals(licensePlate, that.licensePlate) &&
            Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, startDate);
    }

    @Override
    public String toString() {
        return "ParkingRecordID{" +
            "licensePlate='" + licensePlate + '\'' +
            ", startDate=" + startDate +
            '}';
    }
}
