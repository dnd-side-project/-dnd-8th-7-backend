package com.dnd8th.service;

import com.dnd8th.common.BlockTest;
import com.dnd8th.dto.block.BlockCalendarResponse;
import com.dnd8th.error.exception.user.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class BlockServiceTest extends BlockTest {

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
