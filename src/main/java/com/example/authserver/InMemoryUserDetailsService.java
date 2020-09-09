package com.example.authserver;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class InMemoryUserDetailsService implements UserDetailsService {

    @Autowired
    private Map<String, UserEntity> userMap;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userMap.get(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", s));
        }
        log.info("userDetails: {} {}", user.toString(), user.getPassword());
        return User.builder().username(user.getUsername())
                .password(user.getPassword())
                .roles((String []) user.getRoles().toArray())
                .build();
    }
}
