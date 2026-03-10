package com.natalia.barros.insurance_product_api.dto;

import com.natalia.barros.insurance_product_api.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductResponse {
    private UUID id;
    private String nome;
    private Category categoria;
    private BigDecimal precoBase;
    private BigDecimal precoTarifado;
}
