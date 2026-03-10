package com.natalia.barros.insurance_product_api.strategy;

import java.math.BigDecimal;

public class AutoTaxStrategy implements TaxStrategy{
    @Override
    public BigDecimal calculate(BigDecimal basePrice) {
        BigDecimal iof = basePrice.multiply(new BigDecimal("0.055"));
        BigDecimal pis = basePrice.multiply(new BigDecimal("0.04"));
        BigDecimal confins = basePrice.multiply(new BigDecimal(0.01));
        return basePrice .add(iof).add(pis).add(confins);
    }
}
