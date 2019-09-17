package com.github.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.service.WhitepinService;

@RestController
@RequestMapping(value = "/whitepin")
public class WhitepinController {
    
    @Autowired
    private WhitepinService whitepinService;
    
    @GetMapping(value = "/whitepinInterlocking")
    public String whitepinInterlocking() {
        return "whitepinInterlocking";
    }
    
    @PostMapping(value = "/whitepinLogin")
    public ResponseEntity<ResponseDTO> whitepinLogin(UserDTO userDto) {
        //TODO :: whitepin login 로직
        // whitepinService.
        return ResponseEntity.ok().body(new ResponseDTO());
    }
}
