package com.dnd8th.controller;

import com.dnd8th.dao.user.UserEmailDao;
import com.dnd8th.entity.UserEntity;
import com.dnd8th.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserEmailDao userEmailDao;

    @PostMapping("/user")
    public ResponseEntity<UserEntity> newEmployee(@RequestBody UserEntity user) {
        UserEntity newUser = userRepository
                .save(user.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserEntity> getUserInfo(
            @PathVariable String email) {
        UserEntity findUser = userEmailDao.findByEmail(email);

        return new ResponseEntity<>(findUser, HttpStatus.OK);
    }


}
