package com.github.airbnb.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * html page 관련 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/views/**")
public class PageController {

    // tag : product
    @GetMapping("/products/{id}")
    public String getProductDetailPages(@PathVariable("id") String id) {
        logger.info("Request product detail page with {}.", id);
        return "product-detail";
    }

    // -- tag : product
}
