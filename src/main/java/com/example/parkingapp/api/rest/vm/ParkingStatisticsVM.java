package com.example.parkingapp.api.rest.vm;

import java.math.BigDecimal;

/**
 * View Model for parking statistics
 */
public class ParkingStatisticsVM {
    private BigDecimal earnings;

    public ParkingStatisticsVM(BigDecimal earnings) {
        this.earnings = earnings;
    }

    public BigDecimal getEarnings() {
        return earnings;
    }

    public void setEarnings(BigDecimal earnings) {
        this.earnings = earnings;
    }

    @Override
    public String toString() {
        return "ParkingStatisticsVM{" +
            "earnings=" + earnings +
            '}';
    }
}
