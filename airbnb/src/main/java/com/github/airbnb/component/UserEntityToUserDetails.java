package com.github.airbnb.component;

import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.service.security.AirbnbUserDetailsImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserEntityToUserDetails implements Converter<UserEntity, UserDetails> {

    @Override
    public UserDetails convert(UserEntity user) {
        AirbnbUserDetailsImpl userDetails = new AirbnbUserDetailsImpl();
        if (user != null) {
            userDetails.setUsername(user.getEmail());
            userDetails.setPassword(user.getPassword());
            userDetails.setEnabled(true);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
            });
            userDetails.setAuthorities(authorities);
        }
        return userDetails;
    }

}
