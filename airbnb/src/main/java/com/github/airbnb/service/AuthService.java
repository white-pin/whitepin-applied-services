package com.github.airbnb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.airbnb.common.ResponseCode;
import com.github.airbnb.dto.JoinDTO;
import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public ResponseDTO joinUser(JoinDTO joinDto) {
        String email = joinDto.getEmail();
        String password = joinDto.getPassword();

        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAndPassword(email, password);
        if (!userEntityOptional.isPresent()) {
            return ResponseDTO.builder()
                    .code(ResponseCode.FAILED)
                    .message("ID/PASSWORD가 잘못되었습니다.")
                    .build();
        }
        return ResponseDTO.builder()
                .code(ResponseCode.SUCCESSFUL)
                .message("로그인에 성공하였습니다.")
                .build();
    }
}
