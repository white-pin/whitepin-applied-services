package com.github.airbnb.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.UserDTO;

@RestController
@RequestMapping(value = "/whitepin")
public class WhitepinApiController {
    
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDTO> whitepinLogin(UserDTO userDto) {
        return ResponseEntity.ok().body(new ResponseDTO());
    }
}
