package com.dnd8th.service;

import com.dnd8th.dto.block.BlockCalendarResponse;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Role;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.user.UserNotFoundException;
import com.dnd8th.repository.BlockRepository;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.util.DateParser;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class BlockServiceTest {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected BlockRepository blockRepository;
    @Autowired
    protected  BlockService blockService;
    @Autowired
    protected DateParser dateParser;

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

        for (int i = 1; i < 6; i++) {
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

    @Test
    @DisplayName("13일 간의 블럭 결과를 가져올 때 잘못된 이메일을 에러를 낸다.")
    void getInA13DayBlocksReturnErrorIfWrongEmailIsGiven() {
        //given
        String userEmail = "wrongTest@gmail.com";
        String date = "2023-03-04";

        //when & then
        assertThatThrownBy(() -> blockService.getBlockWeek(userEmail,date))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("13일 간의 블럭 결과를 가져온다.")
    void getInA13DayBlocks() {
        //given
        String userEmail = "test@gmail.com";
        String date = "2023-03-04";

        //when
        List<BlockCalendarResponse> blockCalendarResponse = blockService.getBlockWeek(
                userEmail, date
        );

        //then
        assertThat(blockCalendarResponse.size()).isEqualTo(13);
    }

    @Test
    @DisplayName("30일 간의 블럭 결과를 가져올 때 잘못된 이메일을 에러를 낸다.")
    void getInMonthBlocksReturnErrorIfWrongEmailIsGiven() {
        //given
        String userEmail = "wrongTest@gmail.com";
        String date = "2023-03-04";

        //when & then
        assertThatThrownBy(() -> blockService.getBlockMonth(userEmail,date))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("30일 간의 블럭 결과를 가져온다.")
    void getInAMonthBlocks() {
        //given
        String userEmail = "test@gmail.com";
        String date = "2023-03-04";

        //when
        List<BlockCalendarResponse> blockCalendarResponse = blockService.getBlockMonth(
                userEmail, date
        );

        //then
        assertThat(blockCalendarResponse.size()).isEqualTo(31);
    }
}
