package com.dnd8th.common;

import com.dnd8th.auth.jwt.JwtProviderService;
import com.dnd8th.entity.Role;
import com.dnd8th.entity.User;
import com.dnd8th.repository.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public abstract class AuthTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JwtProviderService jwtProviderService;

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
    }
}
