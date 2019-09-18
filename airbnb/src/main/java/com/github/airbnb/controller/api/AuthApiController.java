package com.github.airbnb.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.UserRepository;

@RestController
@RequestMapping(value = "/auth")
public class AuthApiController {

    @Autowired
    UserRepository userRepository;
    
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO userDto) {
        ResponseDTO responseDTO = new ResponseDTO();
        HttpStatus httpStatus;
        
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAndPassword(email, password);
        if (!userEntityOptional.isPresent()) {
            responseDTO.setMessage("ID/PASSWORD가 잘못되었습니다.");
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            responseDTO.setMessage("로그인에 성공하였습니다.");
            httpStatus = HttpStatus.OK;
        }
        
        return ResponseEntity.status(httpStatus).body(responseDTO);
    }
    
    @PostMapping(value = "/logout")
    public ResponseEntity<UserDTO> logout(@RequestBody UserDTO userDto) {
        //TODO:: 로그아웃 로직
        // joinService.
        return ResponseEntity.ok().body(new UserDTO());
    }
}
