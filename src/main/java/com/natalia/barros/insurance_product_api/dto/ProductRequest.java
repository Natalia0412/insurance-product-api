package com.natalia.barros.insurance_product_api.dto;

import com.natalia.barros.insurance_product_api.domain.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150)
        String nome,

        @NotNull(message = "Categoria é obrigatória")
        Category categoria,

        @NotNull(message = "Preço base é obrigatório")
        @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
        BigDecimal precoBase

) {}