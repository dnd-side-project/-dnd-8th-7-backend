package com.dnd8th.repository;

import com.dnd8th.entity.Role;
import com.dnd8th.entity.User;
import com.dnd8th.util.DateParser;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class RepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected BlockRepository blockRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    protected KeepRepository keepRepository;

    @Autowired
    protected DateParser dateParser;

    @Autowired
    protected EntityManager entityManager;

    @BeforeEach
    void userSetup() {
        userRepository.save(User.builder()
                .name("test")
                .email("test@gmail.com")
                .role(Role.USER)
                .imagePath("")
                .build());
    }
}
