package com.natalia.barros.insurance_product_api.service;

import com.natalia.barros.insurance_product_api.domain.Product;
import com.natalia.barros.insurance_product_api.dto.ProductRequest;
import com.natalia.barros.insurance_product_api.dto.ProductResponse;
import com.natalia.barros.insurance_product_api.factory.TaxStrategyFactory;
import com.natalia.barros.insurance_product_api.repository.ProductRepository;
import com.natalia.barros.insurance_product_api.mapper.ProductMapper;
import com.natalia.barros.insurance_product_api.strategy.TaxStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final TaxStrategyFactory strategyFactory;
    private final ProductMapper productMapper;

    ProductService(ProductRepository productRepository,
                   TaxStrategyFactory strategyFactory,
                   ProductMapper productMapper)
    {
        this.productRepository = productRepository;
        this.strategyFactory = strategyFactory;
        this.productMapper = productMapper;
    }

    public ProductResponse create(ProductRequest request){

        log.info(
                "Creating product: nome={}, categoria={}, precoBase={}",
                request.nome(),
                request.categoria(),
                request.precoBase()
        );

        Product product = productMapper.toEntity(request);

        TaxStrategy strategy = strategyFactory.getStrategy(product.getCategoria());

        product.calculateTariffPrice(strategy);

        Product saved = productRepository.save(product);

        log.info(
                "Product created successfully: id={}, precoTarifado={}",
                saved.getId(),
                saved.getPrecoTarifado()
        );

        return productMapper.toResponse(saved);

    }
}
