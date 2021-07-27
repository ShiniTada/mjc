package com.epam.esm.service.service.impl;

import com.epam.esm.repository.entity.User;
import com.epam.esm.repository.entity.UserRole;
import com.epam.esm.repository.repository.UserRepository;
import com.epam.esm.service.converter.UserConverter;
import com.epam.esm.service.dto.RegistrationUserDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User service implementation
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_IS_NOT_FOUND = "user.not.found";
    private static final String USER_IS_EXIST = "user.exist";
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Instantiates a new user service.
     *
     * @param userRepository user repository
     * @param userConverter  user converter
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<UserDto> findAllUsers(int page, int size) {
        List<User> users = userRepository.findAllUsers(page, size);
        if (CollectionUtils.isNotEmpty(users)) {
            List<UserDto> userDtoList = users.stream().map(userConverter::convertToUserDto).collect(Collectors.toList());
            return userDtoList;
        } else {
            throw new ResourceNotFoundException(USER_IS_NOT_FOUND);
        }
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            return userConverter.convertToUserDto(user);
        } else {
            throw new ResourceNotFoundException(USER_IS_NOT_FOUND);
        }
    }

    @Override
    public UserDto findUserById(long id) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            return userConverter.convertToUserDto(user);
        } else {
            throw new ResourceNotFoundException(USER_IS_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public UserDto createUser(RegistrationUserDto registrationUserDto) {
        User user = userRepository.findUserByEmail(registrationUserDto.getEmail());
        if (user != null) {
            throw new ServiceException(USER_IS_EXIST);
        }
        user = new User();
        String password = bCryptPasswordEncoder.encode(registrationUserDto.getPassword());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(password);
        user.setUserRole(UserRole.USER);
        User newUser = userRepository.create(user);
        return userConverter.convertToUserDto(newUser);
    }
}
