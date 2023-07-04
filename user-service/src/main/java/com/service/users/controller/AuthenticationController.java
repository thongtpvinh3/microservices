package com.service.users.controller;

import com.service.users.common.ResponseObject;
import com.service.users.dto.AuthenticationRequest;
import com.service.users.entities.User;
import com.service.users.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    @Value("${server.port}")
    private int port;

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        System.out.println("Load balancer in port: " + port);
        return ResponseEntity.ok(new ResponseObject("Log in success!", HttpStatus.OK, authenticationService.authenticate(authenticationRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        System.out.println("Load balancer in port: " + port);
        return ResponseEntity.ok(new ResponseObject("Register success", HttpStatus.OK, authenticationService.register(user)));
    }

}
