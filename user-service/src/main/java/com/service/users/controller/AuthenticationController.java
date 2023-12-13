package com.service.users.controller;

import com.service.users.common.ResponseObject;
import com.service.users.dto.AuthenticationRequest;
import com.service.users.request.RegisterRequest;
import com.service.users.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    @Value("${server.port}")
    private int port;

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authentication(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws Exception {
        log.info("Load balancer in port: {}", port);
        return ResponseEntity.ok(new ResponseObject("Log in success!",
                HttpStatus.OK, authenticationService.authenticate(authenticationRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        log.info("Load balancer in port: {}", port);
        return ResponseEntity.ok(new ResponseObject("Register success",
                HttpStatus.OK, authenticationService.register(request)));
    }

}
