package com.natalia.barros.insurance_product_api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        List<FieldValidationError> errors
) {
}
