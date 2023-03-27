package com.dnd8th.dao.report;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd8th.dto.report.MonthlyBlockAndTaskGetDTO;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Role;
import com.dnd8th.entity.Task;
import com.dnd8th.entity.User;
import com.dnd8th.repository.BlockRepository;
import com.dnd8th.repository.TaskRepository;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.util.DateParser;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MonthlyBlockTaskGetDaoTest {

    @Autowired
    MonthlyBlockTaskGetDao monthlyBlockTaskGetDao;
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
            savedBlock.getId();

            for (int j = 0; j < 3; j++) {
                taskRepository.save(Task.builder()
                        .block(savedBlock)
                        .status(true)
                        .contents("task" + j)
                        .build()
                );
            }
        }
    }

    @Test
    @DisplayName("월별 블록을 정상적으로 조회할 수 있다.")
    void getMonthlyBlock() {
        //given
        String userEmail = "test@gmail.com";
        Integer month = 3;

        //when
        List<MonthlyBlockAndTaskGetDTO> monthlyBlock = monthlyBlockTaskGetDao.getMonthlyBlockAndTask(
                userEmail,
                month);

        //then
        assertThat(monthlyBlock.size()).isEqualTo(8);
        for (MonthlyBlockAndTaskGetDTO block : monthlyBlock) {
            assertThat(block.getTasks().size()).isEqualTo(3);
        }
    }
}
