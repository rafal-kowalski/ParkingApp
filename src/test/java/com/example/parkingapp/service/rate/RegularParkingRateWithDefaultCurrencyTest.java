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
public class RegularParkingRateWithDefaultCurrencyTest implements WithAssertions {
    private static CurrencyConversionRate defaultCurrency;

    static {
        defaultCurrency = new CurrencyConversionRate();
        defaultCurrency.setConversionRate(BigDecimal.ONE);
        defaultCurrency.setId(AcceptedCurrencies.PLN);
    }

    private RegularParkingRate sut;
    private Long input;
    private BigDecimal expected;

    public RegularParkingRateWithDefaultCurrencyTest(Long input, BigDecimal expected) {
        this.input = input;
        this.expected = expected;
        this.sut = new RegularParkingRate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
            new Object[]{1L, new BigDecimal("1.0")},
            new Object[]{2L, new BigDecimal("3.0")},
            new Object[]{3L, new BigDecimal("7.0")},
            new Object[]{4L, new BigDecimal("15.0")},
            new Object[]{5L, new BigDecimal("31.0")}
        );
    }

    @Test
    public void shouldCalculateCorrectly() {
        BigDecimal result = sut.calculate(defaultCurrency, input);
        assertThat(result).isEqualByComparingTo(expected);
    }
}
