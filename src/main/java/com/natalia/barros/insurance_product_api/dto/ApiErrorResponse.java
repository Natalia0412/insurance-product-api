package com.natalia.barros.insurance_product_api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
}
