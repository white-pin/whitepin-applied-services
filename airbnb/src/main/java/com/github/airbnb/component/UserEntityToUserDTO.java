package com.github.airbnb.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.entity.UserEntity;

@Component
public class UserEntityToUserDTO implements Converter<UserEntity, UserDTO> {

    @Override
    public UserDTO convert(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO = UserDTO.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        }
        return userDTO;
    }

}
