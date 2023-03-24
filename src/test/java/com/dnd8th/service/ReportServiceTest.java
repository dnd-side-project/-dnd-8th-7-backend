package com.dnd8th.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.dao.report.MonthlyBlockGetDao;
import com.dnd8th.dto.report.MostTaskRateBlockGetResponse;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Role;
import com.dnd8th.entity.Task;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.report.MonthInputInvalidException;
import com.dnd8th.repository.BlockRepository;
import com.dnd8th.repository.TaskRepository;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.util.DateParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReportServiceTest {

    @Autowired
    MonthlyBlockGetDao monthlyBlockGetDao;
    @Autowired
    ReportService reportService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BlockRepository blockRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    DateParser dateParser;

    @BeforeEach
    void setUp() {
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
    @DisplayName("잘못된 월별 입력에 대해서 예외를 발생시킨다.")
    void getMonthlyBlockWithException() {
        //given
        String userEmail = "test@gmail.com";
        Integer month = 13;

        //when & then
        assertThatThrownBy(() -> reportService.getMostTaskRateBlock(userEmail, month))
                .isInstanceOf(MonthInputInvalidException.class);
    }

    @Test
    @DisplayName("가장 달성률이 높은 블록 제목을 가져온다.")
    void getMonthlyHighestAchievementBlock() {
        //given
        String userEmail = "test@gmail.com";
        Integer month = 3;

        //when
        MostTaskRateBlockGetResponse mostTaskRateBlock = reportService.getMostTaskRateBlock(
                userEmail, month);

        //then
        assertThat(mostTaskRateBlock.getContent()).isEqualTo("content2");
    }

    @Test
    @DisplayName("충분한 블록 및 태스크가 없을 때, null을 반환한다")
    void getMonthlyHighestAchievementNull() {
        //given
        String userEmail = "test2@gmail.com";
        Integer month = 3;

        //when
        MostTaskRateBlockGetResponse mostTaskRateBlock = reportService.getMostTaskRateBlock(
                userEmail, month);

        //then
        assertThat(mostTaskRateBlock.getContent()).isNull();
    }
}
