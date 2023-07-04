package com.service.users.service.impl;

import com.service.users.entities.Role;
import com.service.users.entities.User;
import com.service.users.repository.UserRepository;
import com.service.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        user.setRole(Role.ROLE_EMPLOYEE);
        return userRepository.save(user);
    }

    @Override
    public User createAdmin(User user) {
        user.setRole(Role.ROLE_ADMIN);
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public User update(long id, User user) {
        User foundUser = userRepository.findById(id).orElseThrow(RuntimeException::new);
        boolean needUpdate = false;

        if (StringUtils.hasLength(user.getUsername())) {
            foundUser.setUsername(user.getUsername());
            needUpdate = true;
        }
        if (StringUtils.hasLength(user.getEmail())) {
            foundUser.setEmail(user.getEmail());
            needUpdate = true;
        }
        if (StringUtils.hasLength(user.getPassword())) {
            foundUser.setPassword(user.getPassword());
            needUpdate = true;
        }
        if (StringUtils.hasLength(user.getAddress())) {
            foundUser.setAddress(user.getAddress());
            needUpdate = true;
        }

        if (needUpdate) {
            userRepository.save(foundUser);
        }

        return foundUser;
    }


}
