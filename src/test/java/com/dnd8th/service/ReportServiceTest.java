package com.dnd8th.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.common.ReportTest;
import com.dnd8th.dto.report.ReportBlockGetResponse;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.report.MonthInputInvalidException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportServiceTest extends ReportTest {

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
        ReportBlockGetResponse mostTaskRateBlock = reportService.getMostTaskRateBlock(
                userEmail, month);

        //then
        assertThat(mostTaskRateBlock.getContent()).isEqualTo("content2");
    }

    @Test
    @DisplayName("충분한 블록 및 태스크가 없을 때, null 값을 반환한다")
    void getMonthlyHighestAchievementNull() {
        //given
        String userEmail = "test2@gmail.com";
        Integer month = 3;

        //when
        ReportBlockGetResponse mostTaskRateBlock = reportService.getMostTaskRateBlock(
                userEmail, month);

        //then
        assertThat(mostTaskRateBlock.getContent()).isNull();
    }

    @Test
    @DisplayName("가장 많은 블록의 블록 제목을 가져온다.")
    void getMonthlyMostMadeBlock() {
        //given
        String userEmail = "test@gmail.com";
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Integer month = 3;
        blockRepository.save(Block.builder()
                .user(user)
                .title("content2")
                .blockLock(true)
                .blockColor("#111111")
                .date(dateParser.parseDate("2021-03-02"))
                .emotion("😁")
                .build()
        );

        //when
        ReportBlockGetResponse mostMadeBlock = reportService.getMostMadeBlock(userEmail, month);

        //then
        assertThat(mostMadeBlock.getContent()).isEqualTo("content2");
    }

    @Test
    @DisplayName("블록이 없을 경우, null 값을 반환한다.")
    void getMonthlyMostMadeBlockNull() {
        //given
        String userEmail = "test@gmail.com";
        Integer month = 4;

        //when
        ReportBlockGetResponse mostMadeBlock = reportService.getMostMadeBlock(userEmail, month);

        //then
        assertThat(mostMadeBlock.getContent()).isNull();
    }

    @Test
    @DisplayName("잘못된 월별 입력에 대해서 예외를 발생시킨다.")
    void getMonthlyBlockWException() {
        //given
        String userEmail = "test@gmail.com";
        Integer month = 13;

        //when & then
        assertThatThrownBy(() -> reportService.getMostMadeBlock(userEmail, month))
                .isInstanceOf(MonthInputInvalidException.class);
    }

    @Test
    @DisplayName("가장 저조한 달성률의 블록을 가져온다.")
    void getMonthlyLowestAchievementBlock() {
        //given
        String userEmail = "test@gmail.com";
        Integer month = 3;

        //when
        ReportBlockGetResponse mostMadeBlock = reportService.getLeastTaskRateBlock(userEmail,
                month);

        //then
        assertThat(mostMadeBlock.getContent()).isEqualTo("content1");
    }

    @Test
    @DisplayName("충분한 블록 및 태스크가 없을 때, null 값을 반환한다")
    void getMonthlyLeastAchievementNull() {
        //given
        String userEmail = "test2@gmail.com";
        Integer month = 3;

        //when
        ReportBlockGetResponse mostTaskRateBlock = reportService.getLeastTaskRateBlock(
                userEmail, month);

        //then
        assertThat(mostTaskRateBlock.getContent()).isNull();
    }
}
