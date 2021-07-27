package com.epam.esm.service.service.impl;


import com.epam.esm.repository.entity.User;
import com.epam.esm.repository.repository.UserRepository;
import com.epam.esm.service.dto.RequestTokenDto;
import com.epam.esm.service.dto.ResponseTokenDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.jwt.JwtTokenProvider;
import com.epam.esm.service.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Authentication service implementation
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    @Value("${jwt.expired}")
    private long validityTokenMillis;
    private static final String USER_NOT_FOUND_MESSAGE = "user.not.found";

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     JwtTokenProvider jwtTokenProvider,
                                     UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseTokenDto getAuthenticationResult(RequestTokenDto requestTokenDto) {
        try {
            String email = requestTokenDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                    requestTokenDto.getPassword()));
            User user = userRepository.findUserByEmail(email);
            if (user == null) {
                throw new ServiceException(USER_NOT_FOUND_MESSAGE);
            }
            String token = jwtTokenProvider.generateToken(email, user.getUserRole());
            return new ResponseTokenDto(token, user.getEmail(), validityTokenMillis);
        } catch (AuthenticationException e) {
            throw new ServiceException(USER_NOT_FOUND_MESSAGE);
        }
    }
}

