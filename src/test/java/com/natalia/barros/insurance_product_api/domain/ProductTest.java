package com.natalia.barros.insurance_product_api.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void shouldSetAndGetFields() {

        Product product = new Product();

        UUID id = UUID.randomUUID();
        BigDecimal precoBase = new BigDecimal("100");
        BigDecimal precoTarifado = new BigDecimal("110");

        product.setId(id);
        product.setNome("Seguro Auto");
        product.setCategoria(Category.AUTO);
        product.setPrecoBase(precoBase);
        product.setPrecoTarifado(precoTarifado);

        assertEquals(id, product.getId());
        assertEquals("Seguro Auto", product.getNome());
        assertEquals(Category.AUTO, product.getCategoria());
        assertEquals(precoBase, product.getPrecoBase());
        assertEquals(precoTarifado, product.getPrecoTarifado());
    }

}