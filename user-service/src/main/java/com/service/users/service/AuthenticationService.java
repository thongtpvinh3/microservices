package com.service.users.service;

import com.service.users.dto.AuthenticationRequest;
import com.service.users.dto.AuthenticationResponse;
import com.service.users.entities.User;
import com.service.users.request.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;

    User register(RegisterRequest request);
}

