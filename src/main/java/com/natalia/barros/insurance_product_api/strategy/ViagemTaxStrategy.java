package com.natalia.barros.insurance_product_api.strategy;

import com.natalia.barros.insurance_product_api.utils.MoneyUtils;

import java.math.BigDecimal;

public class ViagemTaxStrategy implements TaxStrategy{
    @Override
    public BigDecimal calculate(BigDecimal basePrice) {
        BigDecimal iof = basePrice.multiply(new BigDecimal("0.02"));
        BigDecimal pis = basePrice.multiply(new BigDecimal("0.04"));
        BigDecimal cofins = basePrice.multiply(new BigDecimal("0.01"));
        BigDecimal tax = basePrice .add(iof).add(pis).add(cofins);
        return MoneyUtils.round(tax);
    }
}
