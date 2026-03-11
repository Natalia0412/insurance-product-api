package com.natalia.barros.insurance_product_api.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {
    private static final int SCALE = 2;

    private MoneyUtils() {}

    public static BigDecimal round(BigDecimal value) {
        return value.setScale(SCALE, RoundingMode.HALF_UP);
    }
}
