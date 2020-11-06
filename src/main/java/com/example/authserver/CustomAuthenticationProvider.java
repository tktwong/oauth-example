package com.example.authserver;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private Map<String, UserEntity> userMap;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity user = shouldAuthenticateAgainstThirdPartySystem(name, password);
        if (user != null) {
            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthenticationToken(
                    name, password, new ArrayList<>());
        } else {
            return null;
        }
    }

    private UserEntity shouldAuthenticateAgainstThirdPartySystem(String name, String password) {
        UserEntity user = userMap.get(name);
        if (user != null && StringUtils.equals("password", password)) {
            return user;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
