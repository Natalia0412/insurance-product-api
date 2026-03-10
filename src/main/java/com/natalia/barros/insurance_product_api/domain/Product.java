package com.natalia.barros.insurance_product_api.domain;

import com.natalia.barros.insurance_product_api.strategy.TaxStrategy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    private UUID id;

    private String  nome;

    private String categoria;

    @Column(name = "preco_base")
    private BigDecimal precoBase;

    @Column(name = "preco_tarifado")
    private BigDecimal precoTarifado;

    public void calculateTariffPrice(TaxStrategy strategy){
        this.precoTarifado = strategy.calculate(this.precoBase);
    }
}
