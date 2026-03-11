package com.natalia.barros.insurance_product_api.factory;

import static org.junit.jupiter.api.Assertions.*;

import com.natalia.barros.insurance_product_api.domain.Category;
import com.natalia.barros.insurance_product_api.strategy.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;

class TaxStrategyFactoryTest {

    private final TaxStrategyFactory factory = new TaxStrategyFactory();

    @ParameterizedTest
    @MethodSource("strategyProvider")
    void shouldCalculateTaxCorrectly(
            TaxStrategy strategy,
            BigDecimal basePrice,
            BigDecimal expectedPrice) {

        BigDecimal result = strategy.calculate(basePrice);

        assertEquals(expectedPrice, result);
    }

    static Stream<Object[]> strategyProvider() {
        return Stream.of(
                new Object[]{new AutoTaxStrategy(), new BigDecimal("1000"), new BigDecimal("1105.00")},
                new Object[]{new VidaTaxStrategy(), new BigDecimal("1000"), new BigDecimal("1032.00")},
                new Object[]{new ViagemTaxStrategy(), new BigDecimal("1000"), new BigDecimal("1070.00")},
                new Object[]{new ResidencialTaxStrategy(), new BigDecimal("1000"), new BigDecimal("1070.00")},
                new Object[]{new PatrimonialTaxStrategy(), new BigDecimal("1000"), new BigDecimal("1080.00")}
        );
    }

    @Test
    void shouldReturnAutoStrategy() {

        var strategy = factory.getStrategy(Category.AUTO);

        assertNotNull(strategy);
    }




}