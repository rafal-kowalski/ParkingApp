package com.example.parkingapp.api.rest.vm;

import com.example.parkingapp.domain.Earnings;

import java.util.Set;

/**
 * View Model for parking statistics
 */
public class ParkingStatisticsVM {
    private Set<Earnings> earnings;

    public ParkingStatisticsVM(Set<Earnings> earnings) {
        this.earnings = earnings;
    }

    public Set<Earnings> getEarnings() {
        return earnings;
    }

    public void setEarnings(Set<Earnings> earnings) {
        this.earnings = earnings;
    }

    @Override
    public String toString() {
        return "ParkingStatisticsVM{" +
            "earnings=" + earnings +
            '}';
    }
}
