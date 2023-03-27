package com.dnd8th.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd8th.auth.jwt.JwtProviderService;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Role;
import com.dnd8th.entity.Task;
import com.dnd8th.entity.User;
import com.dnd8th.repository.BlockRepository;
import com.dnd8th.repository.TaskRepository;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.util.DateParser;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ReportApiTest {

    @Autowired
    ReportApi reportApi;
    @Autowired
    JwtProviderService jwtProviderService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BlockRepository blockRepository;
    @Autowired
    DateParser dateParser;
    @Autowired
    TaskRepository taskRepository;

    @LocalServerPort
    private int port;

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
                    .date(dateParser.parseDate("2021-03-0" + i))
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
    }

    @Test
    @DisplayName("정상적으로 가장 높은 달성률의 블록을 확인할 수 있다.")
    void getHighestAchievementBlock() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token;
        Integer month = 3;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/most-task-rate/" + month)
                .then()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("정상적으로 가장 많이 만든 블록을 확인할 수 있다.")
    void getMostMadeBlock() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token;
        Integer month = 3;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/most-made-block/" + month)
                .then()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(200);
    }

}
