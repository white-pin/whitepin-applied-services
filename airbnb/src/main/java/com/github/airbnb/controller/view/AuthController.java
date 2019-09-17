package com.github.airbnb.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.airbnb.service.AuthService;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
}
