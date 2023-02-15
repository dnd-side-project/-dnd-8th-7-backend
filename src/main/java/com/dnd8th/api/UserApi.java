package com.dnd8th.api;

import com.dnd8th.entity.User;
import com.dnd8th.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/api/user/{user-id}")
    public User findUser(@PathVariable("user-id") Long userId) {
        User userFindById = userService.findById(userId);

        return userFindById;
    }
}
