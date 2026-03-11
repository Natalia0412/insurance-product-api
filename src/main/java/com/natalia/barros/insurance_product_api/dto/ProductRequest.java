package com.natalia.barros.insurance_product_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.natalia.barros.insurance_product_api.domain.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "{product.nome.notblank}")
        @Size(max = 150, message = "{product.nome.size}")
        String nome,

        @NotNull(message = "{product.categoria.notnull}")
        Category categoria,

        @NotNull(message = "{product.preco.notnull}")
        @DecimalMin(value = "0.01", message = "{product.preco.min}")
        @JsonProperty("preco_base")
        BigDecimal precoBase

) {}