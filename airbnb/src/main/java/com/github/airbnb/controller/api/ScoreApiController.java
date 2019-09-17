package com.github.airbnb.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.ResponseDTO;

@RestController
@RequestMapping(value = "/score")
public class ScoreApiController {

    //    @Autowired
    //    private ScoreService scoreService;

    @RequestMapping(value = "/enroll")
    public ResponseEntity<ResponseDTO> scoreEnroll() {
        return ResponseEntity.ok().body(new ResponseDTO());
    }

    @RequestMapping(value = "/enrollTemp")
    public ResponseEntity<ResponseDTO> scoreEnrollTemp() {
        return ResponseEntity.ok().body(new ResponseDTO());
    }

}
