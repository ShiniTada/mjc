package com.epam.esm.service.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;

/**
 * User Dto
 */
public class UserDto extends RepresentationModel<UserDto> {

    private Long id;
    @Email
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
