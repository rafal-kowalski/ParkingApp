package com.example.parkingapp.service.rate;

import com.example.parkingapp.domain.CurrencyConversionRate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VipParkingRate implements ParkingRate {
    private static final BigDecimal ONE_AND_HALF = new BigDecimal("1.5");
    @Override
    public BigDecimal calculate(CurrencyConversionRate currency, long hours) {
        BigDecimal rate = currency.getConversionRate();
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal previousHourCost = BigDecimal.ZERO;

        for (long i = 1; i < hours; i++) {
            if (i == 1) {
                previousHourCost = sum.add(rate.multiply(ONE_AND_HALF));
                sum = previousHourCost;
            } else {
                sum = sum.add(rate.multiply(previousHourCost));
                previousHourCost = previousHourCost.multiply(ONE_AND_HALF);
            }
        }
        return sum;
    }
}
