package com.natalia.barros.insurance_product_api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.natalia.barros.insurance_product_api.dto.FieldValidationError;
import com.natalia.barros.insurance_product_api.dto.ApiErrorResponse;
import com.natalia.barros.insurance_product_api.dto.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.MessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        log.warn("Validation error occurred: {}", ex.getMessage());

       String validationError =  getMessage("error.validation");

        List<FieldValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError ->
                        new FieldValidationError(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()))
                .toList();


        ValidationErrorResponse response = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                validationError,
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex) {

        log.warn("Business rule violated: {}", ex.getMessage());

        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                getMessage("error.business"),
                ex.getMessage()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {

        log.error("error.unexpected captured", ex);

        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                getMessage("error.internal"),
                getMessage("error.unexpected")
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidEnum(HttpMessageNotReadableException ex) {

        if (ex.getCause() instanceof InvalidFormatException) {
            log.warn("Invalid enum error occurred: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(
                    new ApiErrorResponse(
                            LocalDateTime.now(),
                            HttpStatus.BAD_REQUEST.value(),
                            getMessage("error.validation"),
                            getMessage("error.invalid.category")
                    )
            );
        }

        log.warn("Invalid JSON error occurred: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        getMessage("error.validation"),
                        getMessage("error.json.invalid")
                )
        );
    }

    public String  getMessage (String key ){
        return  messageSource.getMessage(
                key,
                null,
                Locale.getDefault()
        );
    }
}
