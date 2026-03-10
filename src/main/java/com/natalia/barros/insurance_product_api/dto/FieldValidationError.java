package com.natalia.barros.insurance_product_api.dto;

public record FieldValidationError(
        String field,
        String message) {}
