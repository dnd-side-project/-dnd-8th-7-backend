package com.dnd8th.common;

import com.dnd8th.api.ReportApi;
import com.dnd8th.auth.jwt.JwtProviderService;
import com.dnd8th.dao.report.MonthlyBlockGetDao;
import com.dnd8th.dao.report.MonthlyBlockTaskGetDao;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Role;
import com.dnd8th.entity.Task;
import com.dnd8th.entity.User;
import com.dnd8th.repository.BlockRepository;
import com.dnd8th.repository.TaskRepository;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.service.ReportService;
import com.dnd8th.util.DateParser;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public abstract class ReportTest {

    @Autowired
    protected ReportApi reportApi;
    @Autowired
    protected ReportService reportService;
    @Autowired
    protected JwtProviderService jwtProviderService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected BlockRepository blockRepository;
    @Autowired
    protected DateParser dateParser;
    @Autowired
    protected TaskRepository taskRepository;
    @Autowired
    protected MonthlyBlockGetDao monthlyBlockGetDao;
    @Autowired
    protected MonthlyBlockTaskGetDao monthlyBlockTaskGetDao;

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

        for (int i = 1; i < 9; i++) {
            Block savedBlock = blockRepository.save(Block.builder()
                    .user(testUser)
                    .title("content" + i)
                    .blockLock(true)
                    .blockColor("#111111")
                    .date(dateParser.parseDate("2023-03-0" + i))
                    .emotion("😁")
                    .build()
            );

            for (int j = 0; j < i; j++) {
                if (j == 1) {
                    taskRepository.save(Task.builder()
                            .block(savedBlock)
                            .status(true)
                            .contents("task" + j)
                            .build()
                    );
                } else {
                    taskRepository.save(Task.builder()
                            .block(savedBlock)
                            .status(false)
                            .contents("task" + j)
                            .build()
                    );
                }
            }
        }

        Block savedBlock = blockRepository.save(Block.builder()
                .user(testUser)
                .title("content")
                .blockLock(true)
                .blockColor("#111111")
                .date(dateParser.parseDate("2023-01-01"))
                .emotion("😁")
                .build()
        );

        taskRepository.save(Task.builder()
                .block(savedBlock)
                .status(true)
                .contents("task")
                .build()
        );

        Block savedBlock2 = blockRepository.save(Block.builder()
                .user(testUser)
                .title("content")
                .blockLock(true)
                .blockColor("#111111")
                .date(dateParser.parseDate("2022-12-01"))
                .emotion("😁")
                .build()
        );

        taskRepository.save(Task.builder()
                .block(savedBlock2)
                .status(true)
                .contents("task")
                .build()
        );

        taskRepository.save(Task.builder()
                .block(savedBlock2)
                .status(false)
                .contents("task2")
                .build()
        );
    }
}
