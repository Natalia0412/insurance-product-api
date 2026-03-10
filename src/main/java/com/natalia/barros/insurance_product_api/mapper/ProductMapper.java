package com.natalia.barros.insurance_product_api.mapper;

import com.natalia.barros.insurance_product_api.domain.Product;
import com.natalia.barros.insurance_product_api.dto.ProductRequest;
import com.natalia.barros.insurance_product_api.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    Product toEntity(ProductRequest request);
}
