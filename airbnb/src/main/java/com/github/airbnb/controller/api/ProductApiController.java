package com.github.airbnb.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.common.ResponseCode;
import com.github.airbnb.dto.ProductDTO;
import com.github.airbnb.dto.ProductDTO.EvaluationDTO;
import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.entity.ProductEntity;
import com.github.airbnb.entity.TradeEntity;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.ProductRepository;
import com.github.airbnb.repository.UserRepository;

import io.swagger.annotations.ApiOperation;

@RestController
public class ProductApiController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Converter<ProductEntity, ProductDTO> productConverter;

    @Autowired
    private Converter<TradeEntity, EvaluationDTO> tradeConverter;

    @GetMapping(value = "/products")
    @ApiOperation(value = "products", notes = "상품 전체 조회")
    public ResponseEntity<List<ProductDTO>> products() {
        List<ProductEntity> allproduct = productRepository.findAll();
        List<ProductDTO> products = new ArrayList<ProductDTO>(allproduct.size());

        for (ProductEntity productEntity : allproduct) {
        	products.add(productConverter.convert(productEntity));
        }

        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/products/{productId}")
    @ApiOperation(value = "productDetail", notes = "상품 상세 페이지 데이터 조회 ( 상품 정보, 상품 평가 리스트 )")
    public ResponseEntity productDetail(@PathVariable("productId") String id) {
        try {
            final Optional<ProductEntity> productOptional = productRepository.findById(Long.valueOf(id));
            if (!productOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            ProductEntity productEntity = productOptional.get();
            ProductDTO product = productConverter.convert(productEntity);

            List<TradeEntity> tradeEntitys = productEntity.getTrades();
            List<EvaluationDTO> evaluations = new ArrayList<EvaluationDTO>(tradeEntitys.size());
            for (TradeEntity tradeEntity : tradeEntitys) {
                evaluations.add(tradeConverter.convert(tradeEntity));
            }

            product.setEvaluations(evaluations);
            product.setResponseCode(ResponseCode.SUCCESSFUL);

            return ResponseEntity.ok().body(product);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
