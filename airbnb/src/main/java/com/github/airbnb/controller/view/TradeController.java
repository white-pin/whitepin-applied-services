package com.github.airbnb.controller.view;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.ResponseDTO;

@RestController
@RequestMapping(value = "/trade")
public class TradeController {

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDTO> createTrade() {
        return ResponseEntity.ok().body(new ResponseDTO());
    }

    @PostMapping(value = "/close")
    public ResponseEntity<ResponseDTO> closeTrade() {
        return ResponseEntity.ok().body(new ResponseDTO());
    }
}
