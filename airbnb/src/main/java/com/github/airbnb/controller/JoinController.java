package com.github.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.JoinDTO;
import com.github.airbnb.service.JoinService;

@RestController
@RequestMapping(value = "/join")
public class JoinController {

    @Autowired
    private JoinService joinService;
    
    @PostMapping(value = "/user")
    public ResponseEntity<JoinDTO> joinUser(@RequestParam JoinDTO joinDto) {
        //TODO:: 회원가입
        // joinService.
        return ResponseEntity.ok().body(new JoinDTO());
    }
    
}
