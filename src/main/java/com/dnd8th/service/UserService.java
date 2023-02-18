package com.dnd8th.service;

import com.dnd8th.entity.User;
//import com.dnd8th.error.exception.user.UserNotFoundException;
import com.dnd8th.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        User findUser = userRepository.findById(id).orElseThrow();

        return findUser;
    }
}
