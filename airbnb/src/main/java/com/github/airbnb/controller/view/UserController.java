package com.github.airbnb.controller.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/views/user")
public class UserController {

    @GetMapping(value = "/edit")
    public ModelAndView profile() {
    	ModelAndView mav = new ModelAndView();
        return mav;
    }

    @GetMapping(value = "/show")
    public ModelAndView show() {
    	ModelAndView mav = new ModelAndView();
        return mav;
    }
}
