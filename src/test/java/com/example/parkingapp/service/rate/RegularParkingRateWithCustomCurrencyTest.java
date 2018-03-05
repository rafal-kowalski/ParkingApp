package com.example.parkingapp.service.rate;

import com.example.parkingapp.config.AcceptedCurrencies;
import com.example.parkingapp.domain.CurrencyConversionRate;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RegularParkingRateWithCustomCurrencyTest implements WithAssertions {
    private static CurrencyConversionRate customCurrency;

    static {
        customCurrency = new CurrencyConversionRate();
        customCurrency.setConversionRate(new BigDecimal("0.75"));
        customCurrency.setId(AcceptedCurrencies.PLN);
    }

    private RegularParkingRate sut;
    private Long input;
    private BigDecimal expected;

    public RegularParkingRateWithCustomCurrencyTest(Long input, BigDecimal expected) {
        this.input = input;
        this.expected = expected;
        this.sut = new RegularParkingRate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
            new Object[]{1L, new BigDecimal("0.75")},
            new Object[]{2L, new BigDecimal("2.25")},
            new Object[]{3L, new BigDecimal("5.25")},
            new Object[]{4L, new BigDecimal("11.25")},
            new Object[]{5L, new BigDecimal("23.25")}
        );
    }

    @Test
    public void shouldCalculateCorrectly() {
        BigDecimal result = sut.calculate(customCurrency, input);
        assertThat(result).isEqualByComparingTo(expected);
    }
}
