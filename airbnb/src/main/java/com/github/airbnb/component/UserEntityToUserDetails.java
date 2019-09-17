package com.github.airbnb.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.service.security.AirbnbUserDetailsImpl;

@Component
public class UserEntityToUserDetails implements Converter<UserEntity, UserDetails> {

    @Override
    public UserDetails convert(UserEntity user) {
        AirbnbUserDetailsImpl userDetails = new AirbnbUserDetailsImpl();
        if (user != null) {
            userDetails.setUsername(user.getEmail());
            userDetails.setPassword(user.getPassword());
        }
        return userDetails;
    }

}
