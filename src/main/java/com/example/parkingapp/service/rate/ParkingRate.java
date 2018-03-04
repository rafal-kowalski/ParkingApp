package com.example.parkingapp.service.rate;

import com.example.parkingapp.domain.CurrencyConversionRate;

import java.math.BigDecimal;

public interface ParkingRate {
    BigDecimal calculate(CurrencyConversionRate currency, long hours);
}
