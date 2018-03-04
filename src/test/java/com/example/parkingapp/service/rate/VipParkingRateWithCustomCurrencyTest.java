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
public class VipParkingRateWithCustomCurrencyTest implements WithAssertions {
    private static CurrencyConversionRate defaultCurrency;

    static {
        defaultCurrency = new CurrencyConversionRate();
        defaultCurrency.setConversionRate(new BigDecimal("0.75"));
        defaultCurrency.setId(AcceptedCurrencies.PLN);
    }

    private VipParkingRate sut;
    private Long input;
    private BigDecimal expected;

    public VipParkingRateWithCustomCurrencyTest(Long input, BigDecimal expected) {
        this.input = input;
        this.expected = expected;
        this.sut = new VipParkingRate();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
            new Object[]{1L, new BigDecimal("0.0")},
            new Object[]{2L, new BigDecimal("1.5")},
            new Object[]{3L, new BigDecimal("3.75")},
            new Object[]{4L, new BigDecimal("7.125")},
            new Object[]{5L, new BigDecimal("12.1875")}
        );
    }

    @Test
    public void shouldCalculateCorrectly() {
        BigDecimal result = sut.calculate(defaultCurrency, input);
        assertThat(result).isEqualByComparingTo(expected);
    }
}
