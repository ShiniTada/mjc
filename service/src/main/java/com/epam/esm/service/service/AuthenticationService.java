package com.epam.esm.service.service;

import com.epam.esm.service.dto.RequestTokenDto;
import com.epam.esm.service.dto.ResponseTokenDto;

/**
 * The interface authentication service
 */
public interface AuthenticationService {
    /**
     * Generate token
     *
     * @param requestTokenDto request token dto
     * @return response token dto
     */
    ResponseTokenDto getAuthenticationResult(RequestTokenDto requestTokenDto);
}
