package com.natalia.barros.insurance_product_api.service;

import com.natalia.barros.insurance_product_api.domain.Category;
import com.natalia.barros.insurance_product_api.domain.Product;
import com.natalia.barros.insurance_product_api.dto.ProductRequest;
import com.natalia.barros.insurance_product_api.dto.ProductResponse;
import com.natalia.barros.insurance_product_api.factory.TaxStrategyFactory;
import com.natalia.barros.insurance_product_api.mapper.ProductMapper;
import com.natalia.barros.insurance_product_api.repository.ProductRepository;
import com.natalia.barros.insurance_product_api.strategy.AutoTaxStrategy;
import com.natalia.barros.insurance_product_api.strategy.TaxStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private TaxStrategyFactory strategyFactory;

    @Mock
    private ProductMapper mapper;

    @Mock
    private TaxStrategy strategy;

    private ProductService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new ProductService(repository, strategyFactory, mapper);
    }

    @Test
    void shouldCreateProductSuccessfully() {

        ProductRequest request = new ProductRequest(
                "Seguro Auto",
                Category.AUTO,
                new BigDecimal("1000")
        );

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .nome("Seguro Auto")
                .categoria(Category.AUTO)
                .precoBase(new BigDecimal("1000"))
                .build();

        Product savedProduct = Product.builder()
                .id(UUID.randomUUID())
                .nome("Seguro Auto")
                .categoria(Category.AUTO)
                .precoBase(new BigDecimal("1000"))
                .precoTarifado(new BigDecimal("1105"))
                .build();

        ProductResponse response =
                new ProductResponse(
                        savedProduct.getId(),
                        savedProduct.getNome(),
                        savedProduct.getCategoria(),
                        savedProduct.getPrecoBase(),
                        savedProduct.getPrecoTarifado()
                );


        when(mapper.toEntity(request)).thenReturn(product);
        when(strategyFactory.getStrategy(Category.AUTO)).thenReturn(new AutoTaxStrategy());
        when(strategy.calculate(any())).thenReturn(new BigDecimal("1105"));
        when(repository.save(product)).thenReturn(savedProduct);
        when(mapper.toResponse(savedProduct)).thenReturn(response);

        ProductResponse result = service.create(request);

        assertNotNull(result);
        assertEquals("Seguro Auto", result.nome());

        verify(repository).save(product);
        verify(strategyFactory).getStrategy(Category.AUTO);
    }


}