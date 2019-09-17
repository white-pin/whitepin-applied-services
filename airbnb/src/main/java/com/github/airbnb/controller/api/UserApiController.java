package com.github.airbnb.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.UserRepository;

@RestController
@RequestMapping(value = "/user")
public class UserApiController {
    
    @Autowired
    private UserRepository userRepository;
    
    private Converter<UserEntity, UserDTO> productConverter;
    
    @PostMapping(value = "/edit")
    public ResponseEntity<UserDTO> editProfile(@RequestBody UserDTO userDto) {
        UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());
        userDto = productConverter.convert(userEntity);
        return ResponseEntity.ok().body(userDto);
    }
}
