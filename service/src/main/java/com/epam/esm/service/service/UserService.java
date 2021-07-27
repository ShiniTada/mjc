package com.epam.esm.service.service;

import com.epam.esm.service.dto.RegistrationUserDto;
import com.epam.esm.service.dto.UserDto;

import java.util.List;

/**
 * The interface user service
 */
public interface UserService {
    /**
     * Find all users
     *
     * @param page page
     * @param size size
     * @return userDtos lisr
     */
    List<UserDto> findAllUsers(int page, int size);

    /**
     * Find user by email
     *
     * @param email user email
     * @return userDto
     */
    UserDto findUserByEmail(String email);

    /**
     * Find user by id
     *
     * @param id user id
     * @return userDto
     */
    UserDto findUserById(long id);

    /**
     * Create new user
     *
     * @param registrationUserDto registration user dto
     * @return user dto
     */
    UserDto createUser(RegistrationUserDto registrationUserDto);
}
