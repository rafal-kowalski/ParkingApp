package com.example.parkingapp.domain;

import com.example.parkingapp.config.AcceptedCurrencies;

import java.math.BigDecimal;
import java.util.Objects;

public class Earnings {
    private BigDecimal value;
    private AcceptedCurrencies currency;

    public Earnings() {
    }

    public Earnings(BigDecimal value, AcceptedCurrencies currency) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public AcceptedCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(AcceptedCurrencies currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earnings earnings = (Earnings) o;
        return Objects.equals(value, earnings.value) &&
            currency == earnings.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return "Earnings{" +
            "value=" + value +
            ", currency=" + currency +
            '}';
    }
}
