package com.github.airbnb.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/views/auth")
public class AuthController {

    @GetMapping(value = "/login")
    public ModelAndView login() {
    	ModelAndView mav = new ModelAndView();
        return mav;
    }
}
