package com.service.users.controller;

import com.service.users.entities.User;
import com.service.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Value("${server.port}")
    private int port;

    private final UserService userService;

    @PostMapping("/create-admin")
    public User createAdmin(@RequestBody User user) {
        System.out.println("Load balancer in port: " + port);
        return userService.createAdmin(user);
    }

    @GetMapping("/find-all")
    public List<User> findAll() {
        System.out.println("Load balancer in port: " + port);
        return userService.findAll();
    }

    @PatchMapping("/update/{id}")
    public User update(@PathVariable long id, @RequestBody User user) {
        System.out.println("Load balancer in port: " + port);
        return userService.update(id, user);
    }
}