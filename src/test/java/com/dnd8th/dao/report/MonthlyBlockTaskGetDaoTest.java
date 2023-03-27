package com.dnd8th.dao.report;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd8th.common.ReportTest;
import com.dnd8th.dto.report.MonthlyBlockAndTaskGetDTO;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MonthlyBlockTaskGetDaoTest extends ReportTest {

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
    }

    @Test
    @DisplayName("월별 블록이 없는 경우, 빈 리스트를 반환한다.")
    void getMonthlyBlockWhenNoBlock() {
        //given
        String userEmail = "test@gmail.com";
        Integer month = 2;

        //when
        List<MonthlyBlockAndTaskGetDTO> monthlyBlock = monthlyBlockTaskGetDao.getMonthlyBlockAndTask(
                userEmail,
                month);

        //then
        assertThat(monthlyBlock.size()).isEqualTo(0);
    }
}
