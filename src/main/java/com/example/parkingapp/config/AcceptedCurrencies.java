package com.example.parkingapp.config;

public enum AcceptedCurrencies {
    PLN("PLN");

    private final String unit;

    AcceptedCurrencies(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return unit;
    }
}
