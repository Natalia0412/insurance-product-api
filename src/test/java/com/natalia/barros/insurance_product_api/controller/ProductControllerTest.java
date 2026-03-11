package com.natalia.barros.insurance_product_api.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natalia.barros.insurance_product_api.domain.Category;
import com.natalia.barros.insurance_product_api.dto.ProductRequest;
import com.natalia.barros.insurance_product_api.dto.ProductResponse;
import com.natalia.barros.insurance_product_api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {

        ProductRequest request = new ProductRequest(
                "",
                Category.AUTO,
                new BigDecimal("100")
        );

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.errors[0].field").value("nome"))
                .andExpect(jsonPath("$.errors[0].message").value("Nome é obrigatório"));

    }

    @Test
    void shouldReturnValidationMessageWhenNameTooLong() throws Exception {

        String longName = "A".repeat(151);

        ProductRequest request = new ProductRequest(
                longName,
                Category.AUTO,
                new BigDecimal("100")
        );

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Nome deve ter no máximo 150 caracteres"));
    }

    @Test
    void shouldReturnValidationMessageWhenCategoryIsNull() throws Exception {

        String json = """
        {
          "nome": "Seguro Auto",
          "preco_base": 100
        }
        """;

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Categoria é obrigatória"));
    }

    @Test
    void shouldReturnValidationMessageWhenPriceIsNull() throws Exception {

        String json = """
        {
          "nome": "Seguro Auto",
          "categoria": "AUTO"
        }
        """;

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Preço base é obrigatório"));
    }

    @Test
    void shouldReturnValidationMessageWhenPriceIsInvalid() throws Exception {

        ProductRequest request = new ProductRequest(
                "Seguro Auto",
                Category.AUTO,
                new BigDecimal("0.00")
        );

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Preço deve ser maior que zero"));
    }

    @Test
    void shouldIgnorePrecoTarifadoFromRequest() throws Exception {

        String json = """
        {
          "nome": "Seguro Auto",
          "categoria": "AUTO",
          "preco_base": 100,
          "preco_tarifado": 9999
        }
        """;

        ProductResponse response = ProductResponse.builder()
                .id(UUID.randomUUID())
                .nome("Seguro Auto")
                .categoria(Category.AUTO)
                .precoBase(new BigDecimal("100"))
                .precoTarifado(new BigDecimal("110.50"))
                .build();

        Mockito.when(productService.create(Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.preco_tarifado").value(110.50));
    }

    @Test
    void shouldIgnoreUnknownFieldsInRequest() throws Exception {

        String json = """
        {
          "nome": "Seguro Auto",
          "categoria": "AUTO",
          "preco_base": 100,
          "hack": "malicious field"
        }
        """;

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());

        verify(productService).create(argThat(request ->
                request.nome().equals("Seguro Auto") &&
                        request.categoria() == Category.AUTO &&
                        request.precoBase().compareTo(new BigDecimal("100")) == 0
        ));
    }

}

