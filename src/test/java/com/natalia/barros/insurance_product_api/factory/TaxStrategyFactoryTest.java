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

    @Test
    void shouldReturnAutoStrategy() {
        var strategy = factory.getStrategy(Category.AUTO);
        assertTrue(strategy instanceof AutoTaxStrategy);
    }

    @Test
    void shouldReturnVidaStrategy() {
        var strategy = factory.getStrategy(Category.VIDA);
        assertTrue(strategy instanceof VidaTaxStrategy);
    }

    @Test
    void shouldReturnViagemStrategy() {
        var strategy = factory.getStrategy(Category.VIAGEM);
        assertTrue(strategy instanceof ViagemTaxStrategy);
    }

    @Test
    void shouldReturnResidencialStrategy() {
        var strategy = factory.getStrategy(Category.RESIDENCIAL);
        assertTrue(strategy instanceof ResidencialTaxStrategy);
    }

    @Test
    void shouldReturnPatrimonialStrategy() {
        var strategy = factory.getStrategy(Category.PATRIMONIAL);
        assertTrue(strategy instanceof PatrimonialTaxStrategy);
    }




}