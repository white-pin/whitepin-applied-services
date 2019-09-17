package com.github.airbnb.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.airbnb.dto.ProductDTO;
import com.github.airbnb.dto.UserDTO;

@Controller
public class ProductController {

    @GetMapping(value = "/products")
    public String products() {
        return "productList";
    }

    @GetMapping(value = "/product")
    public String product() {
        return "productDetail";
    }
}
