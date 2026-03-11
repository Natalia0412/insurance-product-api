package com.natalia.barros.insurance_product_api.strategy;

import com.natalia.barros.insurance_product_api.utils.MoneyUtils;

import java.math.BigDecimal;

public class ResidencialTaxStrategy implements TaxStrategy {

    @Override
    public BigDecimal calculate(BigDecimal basePrice) {
        BigDecimal iof = basePrice.multiply(new BigDecimal("0.04"));
        BigDecimal cofins = basePrice.multiply(new BigDecimal("0.03"));
        BigDecimal tax =  basePrice .add(iof).add(cofins);
        return MoneyUtils.round(tax);
    }
}
