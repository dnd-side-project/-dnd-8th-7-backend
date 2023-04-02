package com.dnd8th.dao.block;

import com.dnd8th.common.BlockTest;
import com.dnd8th.dto.block.BlockCalendarGetDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockFindDaoTest extends BlockTest {

    @Test
    @DisplayName("주어진 기간을 정상적으로 조회할 수 있다.")
    void getMonthlyBlockList() {
        //given
        String email = "test@gmail.com";
        Date startedAt = dateParser.parseDate("2023-03-01");
        Date endedAt = dateParser.parseDate("2023-03-04");;

        //when
        List<BlockCalendarGetDTO> blockCalendarGetDTOS = blockFindDao.getBlocksByDate(email, startedAt, endedAt);

        //then
        assertThat(blockCalendarGetDTOS.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("주어진 기간의 블록 데이터가 없는 경우 빈 리스트를 반환한다.")
    void getMonthlyBlockListNoBlock() {
        //given
        String email = "test@gmail.com";
        Date startedAt = dateParser.parseDate("2023-03-05");
        Date endedAt = dateParser.parseDate("2023-03-08");;

        //when
        List<BlockCalendarGetDTO> blockCalendarGetDTOS = blockFindDao.getBlocksByDate(email, startedAt, endedAt);

        //then
        assertThat(blockCalendarGetDTOS.size()).isEqualTo(0);
    }
}
