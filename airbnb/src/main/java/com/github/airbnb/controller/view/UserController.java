package com.github.airbnb.controller.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    
    @GetMapping(value = "/edit")
    public String profile() {
        return "profile";
    }
    
    @GetMapping(value = "/show")
    public String show() {
        return "show";
    }
}
