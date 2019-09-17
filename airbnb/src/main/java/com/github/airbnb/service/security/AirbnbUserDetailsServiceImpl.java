package com.github.airbnb.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.UserRepository;

@Service
public class AirbnbUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private Converter<UserEntity, UserDetails> userUserDetailsConverter;

    @Autowired
    @Qualifier(value = "userEntityToUserDetails")
    public void setUserUserDetailsConverter(Converter<UserEntity, UserDetails> userUserDetailsConverter) {
        this.userUserDetailsConverter = userUserDetailsConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userUserDetailsConverter.convert(userRepository.findByEmail(email));
    }
}