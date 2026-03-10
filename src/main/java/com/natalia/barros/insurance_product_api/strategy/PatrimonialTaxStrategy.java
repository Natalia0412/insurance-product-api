package com.natalia.barros.insurance_product_api.strategy;

import java.math.BigDecimal;

public class PatrimonialTaxStrategy implements TaxStrategy{
    @Override
    public BigDecimal calculate(BigDecimal basePrice) {
        BigDecimal iof = basePrice.multiply(new BigDecimal("0.05"));
        BigDecimal pis = basePrice.multiply(new BigDecimal("0.03"));
        return basePrice .add(iof).add(pis);
    }
}
