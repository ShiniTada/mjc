package com.epam.esm.service.jwt;

import com.epam.esm.repository.entity.User;
import com.epam.esm.repository.repository.UserRepository;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * JwtUserDetailsService - implementation UserDetailsService
 */
@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private static final String USER_NOT_FOUND_MESSAGE = "user.not.found";

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ServiceException(USER_NOT_FOUND_MESSAGE);
        }
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                getAuthority(user)
        );
    }

    private List<GrantedAuthority> getAuthority(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().toString()));
        return authorities;
    }
}
