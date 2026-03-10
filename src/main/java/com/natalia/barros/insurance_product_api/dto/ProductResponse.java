package com.natalia.barros.insurance_product_api.dto;

import com.natalia.barros.insurance_product_api.domain.Category;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ProductResponse (
        UUID id,
        String nome,
        Category categoria,
        BigDecimal precoBase,
        BigDecimal precoTarifado
){

}
