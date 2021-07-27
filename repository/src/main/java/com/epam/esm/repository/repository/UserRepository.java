package com.epam.esm.repository.repository;

import com.epam.esm.repository.entity.User;

import java.util.List;

/**
 * The interface user repository
 */
public interface UserRepository {
    /**
     * Find all users
     *
     * @param page page
     * @param size size
     * @return users list
     */
    List<User> findAllUsers(int page, int size);

    /**
     * Find user by email
     *
     * @param email email
     * @return user
     */
    User findUserByEmail(String email);

    /**
     * Find user by id
     *
     * @param id user id
     * @return user
     */
    User findUserById(Long id);

    User create(User user);
}
