package com.dnd8th.dao.report;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd8th.common.ReportTest;
import com.dnd8th.dto.report.MonthlyBlockGetDTO;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MonthlyBlockGetDaoTest extends ReportTest {

    @Test
    @DisplayName("월별 블록을 정상적으로 조회할 수 있다.")
    void getMonthlyBlockList() {
        //given
        String email = "test@gmail.com";
        Integer year = 2023;
        Integer month = 3;

        //when
        List<MonthlyBlockGetDTO> monthlyBlockList = monthlyBlockGetDao.getMonthlyBlockList(email,
                year,
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
        Integer year = 2023;
        Integer month = 11;

        //when
        List<MonthlyBlockGetDTO> monthlyBlockList = monthlyBlockGetDao.getMonthlyBlockList(email,
                year,
                month);

        //then
        assertThat(monthlyBlockList.size()).isEqualTo(0);
    }

}
