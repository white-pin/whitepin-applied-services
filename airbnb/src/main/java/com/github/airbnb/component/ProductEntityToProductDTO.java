package com.github.airbnb.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.airbnb.dto.ProductDTO;
import com.github.airbnb.entity.ProductEntity;

@Component
public class ProductEntityToProductDTO implements Converter<ProductEntity, ProductDTO> {

    @Override
    public ProductDTO convert(ProductEntity product) {
        return ProductDTO.builder()
                   .id(String.valueOf(product.getId()))
                   .info(product.getInfo())
                   .title(product.getTitle())
                   .address(product.getAddress())
                   .price(product.getPrice())
                   .image(product.getImage())
                   .userId(String.valueOf(product.getUserEntity().getId()))
                .build();
    }

}
