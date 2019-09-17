package com.github.airbnb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.ProductDTO;
import com.github.airbnb.dto.UserDTO;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    //    @Autowired
    //    private ProductService productService;

    @GetMapping(value = "/list")
    public String productList(@RequestBody UserDTO userDto) {
        //        productService
        return "productList";
    }

    @GetMapping(value = "/detail")
    public String productDetail(@RequestBody ProductDTO productDto) {
        return "productDetail";
    }
}
