package com.dnd8th.common;

import com.dnd8th.auth.jwt.JwtProviderService;
import com.dnd8th.dao.block.BlockFindDao;
import com.dnd8th.dao.block.BlockUpdateDao;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Role;
import com.dnd8th.entity.User;
import com.dnd8th.repository.BlockRepository;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.service.BlockService;
import com.dnd8th.util.DateParser;
import io.restassured.RestAssured;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public abstract class BlockTest {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected BlockRepository blockRepository;
    @Autowired
    protected JwtProviderService jwtProviderService;
    @Autowired
    protected BlockService blockService;

    @Autowired
    protected DateParser dateParser;
    @Autowired
    protected BlockFindDao blockFindDao;
    @Autowired
    protected BlockUpdateDao blockUpdateDao;
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
                .name("test2")
                .email("test2@gmail.com")
                .role(Role.USER)
                .imagePath("")
                .build());

        for (int i = 1; i < 4; i++) {
            blockRepository.save(Block.builder()
                    .user(testUser)
                    .title("content" + i)
                    .blockLock(true)
                    .blockColor("#111111")
                    .date(dateParser.parseDate("2023-03-0" + i))
                    .emotion("😁")
                    .build()
            );
        }
    }
}
