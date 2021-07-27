package com.epam.esm.service.converter;

import com.epam.esm.repository.entity.User;
import com.epam.esm.service.dto.UserDto;
import org.springframework.stereotype.Component;

/**
 * User converter.
 */
@Component
public class UserConverter {
    /**
     * User convert to UserDto
     *
     * @param user user
     * @return userDto
     */
    public UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    /**
     * UserDto convert to user
     *
     * @param userDto userDto
     * @return user
     */
    public User convertToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
