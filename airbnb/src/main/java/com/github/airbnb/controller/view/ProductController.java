package com.github.airbnb.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.airbnb.dto.ProductDTO;
import com.github.airbnb.dto.UserDTO;

@Controller
public class ProductController {

    @GetMapping(value = "/products")
    public ModelAndView products() {
    	ModelAndView mav = new ModelAndView();
        return mav;
    }

    @GetMapping(value = "/product")
    public ModelAndView product() {
    	ModelAndView mav = new ModelAndView();
        return mav;
    }
}
