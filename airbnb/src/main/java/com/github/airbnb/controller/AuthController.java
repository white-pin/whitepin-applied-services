package com.github.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.service.JoinService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JoinService joinService;
    
    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
    
    @PostMapping(value = "/login")
    public ResponseEntity<UserDTO> login(@RequestParam UserDTO userDto) {
        //TODO:: 로그인 로직
        // joinService.
        return ResponseEntity.ok().body(new UserDTO());
    }
    
    @PostMapping(value = "/logout")
    public ResponseEntity<UserDTO> logout(@RequestParam UserDTO userDto) {
        //TODO:: 로그아웃 로직
        // joinService.
        return ResponseEntity.ok().body(new UserDTO());
    }
}
