package com.dnd8th.dao.report;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd8th.dto.report.MonthlyBlockGetDTO;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Role;
import com.dnd8th.entity.User;
import com.dnd8th.repository.BlockRepository;
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
class MonthlyBlockGetDaoTest {

    @Autowired
    private MonthlyBlockGetDao monthlyBlockGetDao;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private DateParser dateParser;

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
        }
    }


    @Test
    @DisplayName("월별 블록을 정상적으로 조회할 수 있다.")
    void getMonthlyBlockList() {
        //given
        String email = "test@gmail.com";
        Integer month = 3;

        //when
        List<MonthlyBlockGetDTO> monthlyBlockList = monthlyBlockGetDao.getMonthlyBlockList(email,
                month);

        //then
        assertThat(monthlyBlockList.size()).isEqualTo(8);
        assertThat(monthlyBlockList.get(0).getTitle()).isEqualTo("content1");
    }

    @Test
    @DisplayName("해당 월의 블록이 없는 경우 빈 리스트를 반환한다.")
    void getMonthlyBlockListNoBlock() {
        //given
        String email = "test@gmail.com";
        Integer month = 11;

        //when
        List<MonthlyBlockGetDTO> monthlyBlockList = monthlyBlockGetDao.getMonthlyBlockList(email,
                month);

        //then
        assertThat(monthlyBlockList.size()).isEqualTo(0);
    }

}
