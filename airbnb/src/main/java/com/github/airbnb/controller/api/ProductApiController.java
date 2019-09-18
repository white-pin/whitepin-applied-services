package com.github.airbnb.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.ProductDTO;
import com.github.airbnb.dto.ProductDTO.EvaluationDTO;
import com.github.airbnb.entity.ProductEntity;
import com.github.airbnb.entity.TradeEntity;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.ProductRepository;

@RestController
public class ProductApiController {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private Converter<ProductEntity, ProductDTO> productConverter;
    
    @Autowired
    private Converter<TradeEntity, EvaluationDTO> tradeConverter;
    
    @PostMapping(value = "/products")
    public ResponseEntity<List<ProductDTO>> productList() {
        List<ProductEntity> allproduct = productRepository.findAll();
        List<ProductDTO> products = new ArrayList<ProductDTO>(allproduct.size());
        
        for (ProductEntity productEntity : allproduct) 
            products.add(productConverter.convert(productEntity));
        
        return ResponseEntity.ok().body(products);
    }

    @PostMapping(value = "/product")
    public ResponseEntity<ProductDTO> productDetail(@RequestBody ProductDTO productDto) {
        ProductEntity productEntity = productRepository.findById(Long.valueOf(productDto.getId())).get();
        ProductDTO product = productConverter.convert(productEntity);
        
        List<TradeEntity> tradeEntitys = productEntity.getTrades();
        List<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>(tradeEntitys.size());
        for (TradeEntity tradeEntity : tradeEntitys) 
            evaluations.add(tradeConverter.convert(tradeEntity));
        
        product.setEvaluations(evaluations);
        return ResponseEntity.ok().body(product);
    }
}
