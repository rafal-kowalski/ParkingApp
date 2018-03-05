package com.example.parkingapp.domain;

import com.example.parkingapp.config.AcceptedCurrencies;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "currency_conversion_rates")
public class CurrencyConversionRate {
    @Id
    @Enumerated(EnumType.STRING)
    private AcceptedCurrencies id;

    @Column(name = "conversion_rate")
    private BigDecimal conversionRate;

    public AcceptedCurrencies getId() {
        return id;
    }

    public void setId(AcceptedCurrencies id) {
        this.id = id;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyConversionRate that = (CurrencyConversionRate) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
