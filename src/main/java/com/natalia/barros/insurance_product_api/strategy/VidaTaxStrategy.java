package com.natalia.barros.insurance_product_api.strategy;

import java.math.BigDecimal;

public class VidaTaxStrategy implements TaxStrategy{


    @Override
    public BigDecimal calculate(BigDecimal basePrice) {
        BigDecimal iof = basePrice.multiply(new BigDecimal("0.01"));
        BigDecimal pis = basePrice.multiply(new BigDecimal("0.022"));
        return basePrice .add(iof).add(pis);
    }
}
