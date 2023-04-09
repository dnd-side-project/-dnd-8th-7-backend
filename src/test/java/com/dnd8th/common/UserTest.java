package com.dnd8th.common;

import com.dnd8th.entity.Role;
import com.dnd8th.entity.User;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.service.UserService;
import io.restassured.RestAssured;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public abstract class UserTest {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected UserService userService;
    @Autowired
    protected EntityManager entityManager;

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        User testUser = userRepository.save(User.builder()
                .name("test")
                .email("test@gmail.com")
                .role(Role.USER)
                .imagePath("")
                .build()
        );
        User testUser2 = userRepository.save(User.builder()
                .name("test9")
                .email("test9@gmail.com")
                .role(Role.USER)
                .imagePath("")
                .build()
        );
    }
}
