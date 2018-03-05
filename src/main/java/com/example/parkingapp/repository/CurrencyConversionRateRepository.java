package com.example.parkingapp.repository;

import com.example.parkingapp.config.AcceptedCurrencies;
import com.example.parkingapp.domain.CurrencyConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRateRepository extends JpaRepository<CurrencyConversionRate, AcceptedCurrencies> {
}
