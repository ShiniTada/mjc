package com.epam.esm.web.controller;

import com.epam.esm.service.dto.RegistrationUserDto;
import com.epam.esm.service.dto.RequestTokenDto;
import com.epam.esm.service.dto.ResponseTokenDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.service.AuthenticationService;
import com.epam.esm.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Authentication rest controller.
 */
@RestController
@RequestMapping("/auth")
@Valid
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    /**
     * Instantiates an authentication controller.
     *
     * @param authenticationService authentication Service
     * @param userService           user Service
     */
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    /**
     * Generate JWT
     *
     * @param requestDto request token dto
     * @return response token dto
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/login")
    public ResponseTokenDto login(@RequestBody @Valid RequestTokenDto requestDto) {
        return authenticationService.getAuthenticationResult(requestDto);
    }

    /**
     * Register new User
     *
     * @param newUserDto registration user dto
     * @return user dto
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public UserDto register(@RequestBody @Valid RegistrationUserDto newUserDto) {
        return userService.createUser(newUserDto);
    }
}
