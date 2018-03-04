package com.example.parkingapp.service;

import com.example.parkingapp.api.rest.exceptions.CurrencyNotSupportedException;
import com.example.parkingapp.config.AcceptedCurrencies;
import com.example.parkingapp.domain.CurrencyConversionRate;
import com.example.parkingapp.domain.User;
import com.example.parkingapp.repository.CurrencyConversionRateRepository;
import com.example.parkingapp.service.rate.ParkingRate;
import com.example.parkingapp.service.rate.ParkingRateFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class ParkingCostCalculator {
    public static final AcceptedCurrencies DEFAULT_CURRENCY = AcceptedCurrencies.PLN;

    private final ParkingRateFactory parkingRateFactory;
    private final CurrencyConversionRateRepository conversionRateRepository;

    public ParkingCostCalculator(ParkingRateFactory parkingRateFactory, CurrencyConversionRateRepository conversionRateRepository) {
        this.parkingRateFactory = parkingRateFactory;
        this.conversionRateRepository = conversionRateRepository;
    }

    public BigDecimal calculateCost(long totalHours, AcceptedCurrencies currency, User user) {
        ParkingRate parkingRate = parkingRateFactory.getParkingRate(user);
        Optional<CurrencyConversionRate> conversionRate = conversionRateRepository.findById(currency);
        return conversionRate.map(currencyRate -> parkingRate.calculate(currencyRate, totalHours))
            .<CurrencyNotSupportedException>orElseThrow(() -> {
                throw new CurrencyNotSupportedException(currency.getUnit());
            });
    }
}
