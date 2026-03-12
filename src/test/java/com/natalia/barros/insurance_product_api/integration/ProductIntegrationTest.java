package com.natalia.barros.insurance_product_api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natalia.barros.insurance_product_api.domain.Category;
import com.natalia.barros.insurance_product_api.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAutoInsuranceProduct() throws Exception {

        ProductRequest request = new ProductRequest(
                "Seguro Auto Individual",
                Category.AUTO,
                new BigDecimal("1000")
        );

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Seguro Auto Individual"))
                .andExpect(jsonPath("$.categoria").value("AUTO"))
                .andExpect(jsonPath("$.preco_base").value(1000))
                .andExpect(jsonPath("$.preco_tarifado").value(1105.00));
    }

    @Test
    void shouldReturnValidationErrorWhenNameIsBlank() throws Exception {

        String json = """
                    {
                      "nome": "",
                      "categoria": "AUTO",
                      "preco_base": 100
                    }
                """;

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"));
    }

    @Test
    void shouldReturnErrorWhenCategoryIsInvalid() throws Exception {

        String json = """
                {
                  "nome": "Seguro",
                  "categoria": "INVALID",
                  "preco_base": 100
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"));
    }

    @Test
    void shouldReturnBusinessError() throws Exception {

        String json = """
                {
                  "nome": "Seguro",
                  "categoria": "AUTO",
                  "preco_base": -10
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"));
    }

    @Test
    void shouldReturnJsonInvalidWhenMalformedJson() throws Exception {

        String invalidJson = """
                {
                  "nome": "Seguro Auto",
                  "categoria": "AUTO",
                  "preco_base": 100,
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.message").value("JSON inválido"));
    }

}
