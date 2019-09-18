package com.github.airbnb.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/whitepin")
public class WhitepinController {
    
    @GetMapping(value = "/login")
    public ModelAndView whitepinlogin() {
    	ModelAndView mav = new ModelAndView();
        return mav;
    }
    
    @GetMapping(value = "/evaluation")
    public ModelAndView whitepinEvaluation() {
    	ModelAndView mav = new ModelAndView();
        return mav;
    }
    
}
