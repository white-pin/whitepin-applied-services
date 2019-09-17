package com.github.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.ResponseDTO;

@RestController
@RequestMapping(value = "/score")
public class ScoreController {
    
//    @Autowired
//    private ScoreService scoreService;
    
    @RequestMapping(value = "/enroll")
    public ResponseEntity<ResponseDTO> scoreEnroll() {
        return ResponseEntity.ok().body(new ResponseDTO());
    }

}
