package com.natalia.barros.insurance_product_api.strategy;

import java.math.BigDecimal;

public interface TaxStrategy {
    BigDecimal calculate(BigDecimal basePrice);
}
