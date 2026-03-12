package com.natalia.barros.insurance_product_api.domain;

import com.natalia.barros.insurance_product_api.domain.Category;
import com.natalia.barros.insurance_product_api.strategy.TaxStrategy;
import jakarta.persistence.*;
import lombok.*;
import java.math.RoundingMode;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    private String  nome;

    @Enumerated(EnumType.STRING)
    private Category categoria;

    @Column(name = "preco_base")
    private BigDecimal precoBase;

    @Column(name = "preco_tarifado")
    private BigDecimal precoTarifado;

    public void calculateTariffPrice(TaxStrategy strategy){

        this.precoTarifado = strategy.calculate(this.precoBase);
    }


}
