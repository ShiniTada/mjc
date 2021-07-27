
package com.epam.esm.service.service.impl;

import com.epam.esm.repository.entity.User;
import com.epam.esm.repository.repository.UserRepository;
import com.epam.esm.service.converter.UserConverter;
import com.epam.esm.service.dto.RegistrationUserDto;
import com.epam.esm.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = ApplicationRunner.class)
public class UserServiceImplTest {

    private UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserConverter userConverter;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository, userConverter, bCryptPasswordEncoder);
    }

    @AfterEach
    public void tearDown() {
        userService = null;
    }

    @Test
    public void findAllUsersTest() {
        int page = 1;
        int size = 1;
        when(userRepository.findAllUsers(page, size)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> userService.findAllUsers(page, size));
    }

    @Test
    public void findUserByIdTest1() {
        User user = new User();
        user.setId(1);
        when(userRepository.findUserById(1L)).thenReturn(user);
        when(userConverter.convertToUserDto(user)).thenReturn(any());
        userService.findUserById(1);
        verify(userRepository).findUserById(1L);
    }

    @Test
    public void findUserByIdTest2() {
        when(userRepository.findUserById(1L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(1));
    }

    @Test
    public void findUserByEmailTest1() {
        when(userRepository.findUserByEmail("email@test.com")).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserByEmail("email@test.com"));
    }

    @Test
    public void findUserByEmailTest2() {
        User user = new User();
        user.setId(1);
        when(userRepository.findUserByEmail("email@test.com")).thenReturn(user);
        when(userConverter.convertToUserDto(user)).thenReturn(any());
        userService.findUserByEmail("email@test.com");
        verify(userRepository).findUserByEmail("email@test.com");
    }

    @Test
    public void createUserTest() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setEmail("email@test.com");
        registrationUserDto.setPassword("password");
        when(userRepository.findUserByEmail(registrationUserDto.getEmail())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(registrationUserDto.getPassword())).thenReturn(registrationUserDto.getPassword());
        User newUser = new User();
        when(userRepository.create(any())).thenReturn(newUser);
        when(userConverter.convertToUserDto(newUser)).thenReturn(any());
        userService.createUser(registrationUserDto);
        verify(userRepository).create(any());
    }
}

