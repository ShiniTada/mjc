package com.epam.esm.service.dto;

/**
 * Response token dto
 */
public class ResponseTokenDto {
    private String token;
    private String email;
    private long tokenValidity;

    public ResponseTokenDto() {
    }

    public ResponseTokenDto(String token, String email, long tokenValidity) {
        this.token = token;
        this.email = email;
        this.tokenValidity = tokenValidity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(long tokenValidity) {
        this.tokenValidity = tokenValidity;
    }
}
