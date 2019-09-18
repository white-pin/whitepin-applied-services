package com.github.airbnb.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.TradeRepository;
import com.github.airbnb.repository.UserRepository;

@RestController
public class UserApiController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private Converter<UserEntity, UserDTO> userConverter;

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserDTO> userInfo(@PathVariable("id") long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        UserDTO user = userConverter.convert(userEntity);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<ResponseDTO> userUpdate(@RequestBody UserDTO userDto) {
        HttpStatus httpStatus;
        ResponseDTO responseDTO = new ResponseDTO();
        if(StringUtils.isEmpty(userDto.getId())) {
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDTO.setMessage("회원 정보 변경에 실패하였습니다.");
        } else {
            httpStatus = HttpStatus.OK;
            responseDTO.setMessage("회원 정보 변경에 성공하였습니다.");
            
            userRepository.save(UserEntity.builder()
                    .id(Long.valueOf(userDto.getId()))
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .sex(userDto.getSex())
                    .birthday(userDto.getBirthday())
                    .email(userDto.getEmail())
                    .telephone(userDto.getTelephone())
                    .identityYn(userDto.getIdentityYn())
                    .language(userDto.getLanguage())
                    .monetaryUnit(userDto.getMonetaryUnit())
                    .country(userDto.getCountry())
                    .selfInfo(userDto.getSelfInfo())
                    .joinDate(userDto.getJoinDate())
                    .build());
        }
        return new ResponseEntity<ResponseDTO>(responseDTO, httpStatus);
    }

    @GetMapping(value = "/users/score/{id}")
    public ResponseEntity<UserDTO> userScore(@PathVariable("id") long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        //TODO :: 작업해야함
        userEntity.getToken();
        
        UserDTO user = userConverter.convert(userEntity);
        return ResponseEntity.ok().body(user);
    } 
}
