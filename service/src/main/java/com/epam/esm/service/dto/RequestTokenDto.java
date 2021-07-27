package com.epam.esm.service.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Request token dto
 */
public class RequestTokenDto {
    @NotBlank(message = "validation.email")
    @Email(message = "validation.email")
    private String email;
    @NotBlank(message = "validation.password")
    @Size(min = 3, max = 200, message = "validation.password")
    private String password;

    public RequestTokenDto() {
    }

    public RequestTokenDto(@NotBlank(message = "validation.email") @Email String email, @NotBlank(message = "validation.password") @Size(min = 3, max = 200) String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
