package com.github.airbnb.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/whitepin")
public class WhitepinController {
    
    @GetMapping(value = "/login")
    public String whitepinlogin() {
        return "whitepinLogin";
    }
    
    @GetMapping(value = "/evaluation")
    public String whitepinEvaluation() {
        return "whitepinEvaluataion";
    }
    
}
