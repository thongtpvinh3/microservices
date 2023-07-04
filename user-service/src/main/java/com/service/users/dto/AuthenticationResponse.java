package com.service.users.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private final String token;

    private final String username;

    public AuthenticationResponse(String jwtToken, String username) {
        this.token = jwtToken;
        this.username = username;
    }
}
