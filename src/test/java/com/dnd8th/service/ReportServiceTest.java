package com.dnd8th.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.common.ReportTest;
import com.dnd8th.dto.report.ReportBlockGetResponse;
import com.dnd8th.dto.report.ReportMonthlyComparisonGetResponse;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.report.DayInputInvalidException;
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
        Integer year = 2023;
        Integer month = 13;

        //when & then
        assertThatThrownBy(() -> reportService.getMostTaskRateBlock(userEmail, year, month))
                .isInstanceOf(MonthInputInvalidException.class);
    }

    @Test
    @DisplayName("가장 달성률이 높은 블록 제목을 가져온다.")
    void getMonthlyHighestAchievementBlock() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 3;

        //when
        ReportBlockGetResponse mostTaskRateBlock = reportService.getMostTaskRateBlock(
                userEmail, year, month);

        //then
        assertThat(mostTaskRateBlock.getContent()).isEqualTo("content2");
    }

    @Test
    @DisplayName("충분한 블록 및 태스크가 없을 때, null 값을 반환한다")
    void getMonthlyHighestAchievementNull() {
        //given
        String userEmail = "test2@gmail.com";
        Integer year = 2023;
        Integer month = 3;

        //when
        ReportBlockGetResponse mostTaskRateBlock = reportService.getMostTaskRateBlock(
                userEmail, year, month);

        //then
        assertThat(mostTaskRateBlock.getContent()).isNull();
    }

    @Test
    @DisplayName("가장 많은 블록의 블록 제목을 가져온다.")
    void getMonthlyMostMadeBlock() {
        //given
        String userEmail = "test@gmail.com";
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Integer year = 2023;
        Integer month = 3;
        blockRepository.save(Block.builder()
                .user(user)
                .title("content2")
                .blockLock(true)
                .blockColor("#111111")
                .date(dateParser.parseDate("2023-03-02"))
                .emotion("😁")
                .build()
        );

        //when
        ReportBlockGetResponse mostMadeBlock = reportService.getMostMadeBlock(userEmail, year,
                month);

        //then
        assertThat(mostMadeBlock.getContent()).isEqualTo("content2");
    }

    @Test
    @DisplayName("블록이 없을 경우, null 값을 반환한다.")
    void getMonthlyMostMadeBlockNull() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 4;

        //when
        ReportBlockGetResponse mostMadeBlock = reportService.getMostMadeBlock(userEmail, year,
                month);

        //then
        assertThat(mostMadeBlock.getContent()).isNull();
    }

    @Test
    @DisplayName("잘못된 월별 입력에 대해서 예외를 발생시킨다.")
    void getMonthlyBlockWException() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 13;

        //when & then
        assertThatThrownBy(() -> reportService.getMostMadeBlock(userEmail, year, month))
                .isInstanceOf(MonthInputInvalidException.class);
    }

    @Test
    @DisplayName("가장 저조한 달성률의 블록을 가져온다.")
    void getMonthlyLowestAchievementBlock() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 3;

        //when
        ReportBlockGetResponse mostMadeBlock = reportService.getLeastTaskRateBlock(userEmail, year,
                month);

        //then
        assertThat(mostMadeBlock.getContent()).isEqualTo("content1");
    }

    @Test
    @DisplayName("충분한 블록 및 태스크가 없을 때, null 값을 반환한다")
    void getMonthlyLeastAchievementNull() {
        //given
        String userEmail = "test2@gmail.com";
        Integer year = 2023;
        Integer month = 3;

        //when
        ReportBlockGetResponse mostTaskRateBlock = reportService.getLeastTaskRateBlock(
                userEmail, year, month);

        //then
        assertThat(mostTaskRateBlock.getContent()).isNull();
    }

    @Test
    @DisplayName("특정 년월의 특정일까지의 성취률을 불러온다. 이 때 블록 정보가 없다면 0을 반환한다")
    void getAchievementRate() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 3;
        Integer day = 2;

        //when
        ReportMonthlyComparisonGetResponse monthlyComparison = reportService.getMonthlyComparison(
                userEmail, year,
                month, day);

        //then
        assertThat(monthlyComparison.getLastMonthAchievementRate()).isEqualTo(0);
        assertThat(monthlyComparison.getNowMonthAchievementRate()).isEqualTo(25);
    }

    @Test
    @DisplayName("1월에서 작년 12월의 성취률을 정상적으로 반환한다.")
    void getAchievementRateWithLastYear() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 1;
        Integer day = 1;

        //when
        ReportMonthlyComparisonGetResponse monthlyComparison = reportService.getMonthlyComparison(
                userEmail, year,
                month, day);

        //then
        assertThat(monthlyComparison.getLastMonthAchievementRate()).isEqualTo(50);
        assertThat(monthlyComparison.getNowMonthAchievementRate()).isEqualTo(100);
    }

    @Test
    @DisplayName("특정 년월의 특정일까지의 성취률을 불러올 때, 잘못된 월에 대해 예외를 발생시킨다.")
    void getAchievementRateWithInvalidMonth() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 13;
        Integer day = 2;

        //when & then
        assertThatThrownBy(() -> reportService.getMonthlyComparison(userEmail, year, month, day))
                .isInstanceOf(MonthInputInvalidException.class);
    }

    @Test
    @DisplayName("특정 년월의 특정일까지의 성취률을 불러올 때, 잘못된 일에 대해 예외를 발생시킨다.")
    void getAchievementRateWithInvalidDay() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 3;
        Integer day = 33;

        //when & then
        assertThatThrownBy(() -> reportService.getMonthlyComparison(userEmail, year, month, day))
                .isInstanceOf(DayInputInvalidException.class);
    }
}
