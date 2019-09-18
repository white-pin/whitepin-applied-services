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
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .sex(user.getSex())
                    .birthday(user.getBirthday())
                    .email(user.getEmail())
                    .telephone(user.getTelephone())
                    .identityYn(user.getIdentityYn())
                    .language(user.getLanguage())
                    .monetaryUnit(user.getMonetaryUnit())
                    .joinDate(user.getJoinDate())
                    .country(user.getCountry())
                    .selfInfo(user.getSelfInfo())
                    .password(user.getPassword())
                    .token(user.getToken())
                    .build();
        }
        return userDTO;
    }

}
