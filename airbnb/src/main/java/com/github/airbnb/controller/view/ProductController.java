package com.github.airbnb.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.github.airbnb.dto.ProductDTO;
import com.github.airbnb.dto.UserDTO;

@Controller
@RequestMapping(value = "/views")
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
