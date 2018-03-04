package com.example.parkingapp.service.rate;

import com.example.parkingapp.domain.CurrencyConversionRate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RegularParkingRate implements ParkingRate {
    private static final BigDecimal TWO = new BigDecimal("2");
    @Override
    public BigDecimal calculate(CurrencyConversionRate currency, long hours) {
        BigDecimal rate = currency.getConversionRate();
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal previousHourCost = BigDecimal.ZERO;

        for (long i = 0; i < hours; i++) {
            if (i == 0) {
                sum = sum.add(BigDecimal.ONE);
            } else if (i == 1) {
                previousHourCost = new BigDecimal(2);
                sum = sum.add(previousHourCost);
            } else {
                previousHourCost = previousHourCost.multiply(TWO);
                sum = sum.add(previousHourCost);
            }
        }
        return sum.multiply(rate);
    }
}
