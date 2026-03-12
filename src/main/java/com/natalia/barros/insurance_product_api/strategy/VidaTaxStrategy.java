package com.natalia.barros.insurance_product_api.strategy;

import com.natalia.barros.insurance_product_api.utils.MoneyUtils;

import java.math.BigDecimal;

public class VidaTaxStrategy implements TaxStrategy{


    @Override
    public BigDecimal calculate(BigDecimal basePrice) {
        BigDecimal iof = basePrice.multiply(new BigDecimal("0.01"));
        BigDecimal pis = basePrice.multiply(new BigDecimal("0.022"));
        BigDecimal tax = basePrice .add(iof).add(pis);
        return MoneyUtils.round(tax);
    }
}
