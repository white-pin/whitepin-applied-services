package com.github.airbnb.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.airbnb.dto.ProductDTO;
import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.entity.ProductEntity;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.service.security.AirbnbUserDetailsImpl;

@Component
public class ProductEntityToProductDTO implements Converter<ProductEntity, ProductDTO> {

    @Override
    public ProductDTO convert(ProductEntity product) {
        return ProductDTO.builder()
                   .id(String.valueOf(product.getId()))
                   .info(product.getInfo())
                   .joinDate(product.getJoinDate())
                   .language(product.getLanguage())
                   .name(product.getName())
                   .title(product.getTitle())
                   .address(product.getAddress())
                .build();
    }

}
