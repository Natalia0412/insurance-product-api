package com.natalia.barros.insurance_product_api.controller;

import com.natalia.barros.insurance_product_api.domain.Product;
import com.natalia.barros.insurance_product_api.dto.ProductRequest;
import com.natalia.barros.insurance_product_api.dto.ProductResponse;
import com.natalia.barros.insurance_product_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request) {

        ProductResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
