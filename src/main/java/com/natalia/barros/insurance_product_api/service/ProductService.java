package com.natalia.barros.insurance_product_api.service;

import com.natalia.barros.insurance_product_api.domain.Product;
import com.natalia.barros.insurance_product_api.factory.TaxStrategyFactory;
import com.natalia.barros.insurance_product_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final TaxStrategyFactory strategyFactory;

    ProductService(ProductRepository productRepository, TaxStrategyFactory strategyFactory){
        this.productRepository = productRepository;
        this.strategyFactory = strategyFactory;
    }

    public Product create(){
        return null;
    }
}
