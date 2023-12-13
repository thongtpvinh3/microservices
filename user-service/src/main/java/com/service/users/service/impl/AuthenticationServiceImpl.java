package com.service.users.service.impl;

import com.service.users.common.JwtUtil;
import com.service.users.dto.AuthenticationRequest;
import com.service.users.dto.AuthenticationResponse;
import com.service.users.entities.Role;
import com.service.users.entities.User;
import com.service.users.request.RegisterRequest;
import com.service.users.service.AuthenticationService;
import com.service.users.service.EmailService;
import com.service.users.service.UserService;
import com.service.users.service.UserServiceSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final UserServiceSecurity userServiceSecurity;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails user = userServiceSecurity.loadUserByUsername(request.getUsername());
            String jwtToken = jwtUtil.generateToken(user);
            return AuthenticationResponse.builder()
                    .username(request.getUsername())
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password!", e);
        }
    }

    @Override
    public User register(RegisterRequest request) {
        User user = userService.create(
                User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .role(Role.ROLE_EMPLOYEE)
                .build());
        emailService.sendEmail(user.getEmail());
        return user;
    }
}