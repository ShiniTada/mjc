package com.epam.esm.web.jwt;

import com.epam.esm.service.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {
    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    public boolean hasUserId(Authentication authentication, long userId) {
        String username = authentication.getName();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
        return jwtUser.getId() == userId;
    }

    public boolean hasUserEmail(Authentication authentication, String userEmail) {
        String userName = authentication.getName();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(userName);
        return jwtUser.getEmail().equals(userEmail);
    }
}
