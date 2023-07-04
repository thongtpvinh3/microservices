package com.service.users.service.impl;

import com.service.users.common.JwtUtil;
import com.service.users.dto.AuthenticationRequest;
import com.service.users.dto.AuthenticationResponse;
import com.service.users.entities.Role;
import com.service.users.entities.User;
import com.service.users.repository.UserRepository;
import com.service.users.service.AuthenticationService;
import com.service.users.service.UserServiceSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserServiceSecurity userServiceSecurity;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.topic.mail}")
    private String mailTopic;

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
    public User register(User user) {
        user.setRole(Role.ROLE_EMPLOYEE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sendEmail(user);
        return userRepository.save(user);
    }

    @Async
    public void sendEmail(User user) {
        kafkaTemplate.send(mailTopic, user.getEmail());
    }
}