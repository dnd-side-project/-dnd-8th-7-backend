package com.dnd8th.controller;

import com.dnd8th.entity.UserEntity;
import com.dnd8th.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<UserEntity> newEmployee(@RequestBody UserEntity user) {
        UserEntity newUser = userRepository
                .save(user.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

}
