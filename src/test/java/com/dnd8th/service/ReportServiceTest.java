package com.dnd8th.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.dnd8th.common.ReportTest;
import com.dnd8th.dto.block.BlockCreateRequest;
import com.dnd8th.dto.report.MonthlyBlockReportGetResponse;
import com.dnd8th.dto.report.MonthlyDayComparisonGetResponse;
import com.dnd8th.dto.report.ReportBlockGetResponse;
import com.dnd8th.dto.report.ReportMonthlyComparisonGetResponse;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.report.DayInputInvalidException;
import com.dnd8th.error.exception.report.MonthInputInvalidException;
import com.dnd8th.error.exception.report.SortInputInvalidException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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

    @Test
    @DisplayName("특정 년월의 요일 간의 성취률을 불러올 수 있다.")
    void getAchievementRateByDay() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 3;

        //when
        MonthlyDayComparisonGetResponse monthlyDayComparison = reportService.getMonthlyDayComparison(
                userEmail, year,
                month);

        //then
        assertThat(monthlyDayComparison.getSunday()).isEqualTo(20);
        assertThat(monthlyDayComparison.getMonday()).isEqualTo(16);
        assertThat(monthlyDayComparison.getTuesday()).isEqualTo(14);
        assertThat(monthlyDayComparison.getWednesday()).isEqualTo(6);
        assertThat(monthlyDayComparison.getThursday()).isEqualTo(50);
        assertThat(monthlyDayComparison.getFriday()).isEqualTo(33);
        assertThat(monthlyDayComparison.getSaturday()).isEqualTo(25);
    }

    @Test
    @DisplayName("특정 년월의 요일 간의 성취률을 불러올 수 있다. 단 블럭 정보가 없는 경우 0을 반환한다")
    void getAchievementRateByDayWithNoBlock() {
        //given
        String userEmail = "test@gmail.com";
        Integer year = 2023;
        Integer month = 2;

        //when
        MonthlyDayComparisonGetResponse monthlyDayComparison = reportService.getMonthlyDayComparison(
                userEmail, year,
                month);

        //then
        assertThat(monthlyDayComparison.getSunday()).isEqualTo(0);
        assertThat(monthlyDayComparison.getMonday()).isEqualTo(0);
        assertThat(monthlyDayComparison.getTuesday()).isEqualTo(0);
        assertThat(monthlyDayComparison.getWednesday()).isEqualTo(0);
        assertThat(monthlyDayComparison.getThursday()).isEqualTo(0);
        assertThat(monthlyDayComparison.getFriday()).isEqualTo(0);
        assertThat(monthlyDayComparison.getSaturday()).isEqualTo(0);
    }

    @Test
    @DisplayName("블럭 리포트를 불러올 때, 잘못된 월 입력에 대해 예외를 발생시킨다.")
    void getAchievementRateByDayWithInvalidMonth() {
        //given
        String userEmail = "test@gmail.com";
        String sort = "task-rate";
        Integer year = 2023;
        Integer month = 13;

        //when & then
        assertThatThrownBy(() -> reportService.getBlockReport(userEmail, sort, year, month))
                .isInstanceOf(MonthInputInvalidException.class);
    }

    @Test
    @DisplayName("블럭 리포트를 불러올 때, 잘못된 정렬 입력에 대해 예외를 발생시킨다.")
    void getAchievementRateByDayWithInvalidSort() {
        //given
        String userEmail = "test@gmail.com";
        String sort = "task-rate2";
        Integer year = 2023;
        Integer month = 3;

        //when & then
        assertThatThrownBy(() -> reportService.getBlockReport(userEmail, sort, year, month))
                .isInstanceOf(SortInputInvalidException.class);
    }

    @Test
    @DisplayName("특정 연월의 블럭 리포트를 불러올 수 있다.")
    void getBlockReport() {
        //given
        String userEmail = "test@gmail.com";
        String sort = "task-rate";
        Integer year = 2023;
        Integer month = 3;

        //when
        List<MonthlyBlockReportGetResponse> blockReport = reportService.getBlockReport(userEmail,
                sort, year, month);

        //then
        assertThat(blockReport.size()).isEqualTo(8);
        for (MonthlyBlockReportGetResponse monthlyBlockReportGetResponse : blockReport) {
            System.out.println("monthlyBlockReportGetResponse.getTaskRatePercentage() = "
                    + monthlyBlockReportGetResponse.getTaskRatePercentage());
        }
    }

    @Test
    @DisplayName("특정 연월의 블럭 리포트를 불러올 수 있다. 단 블럭 정보가 없는 경우 빈 리스트를 반환한다.")
    void getBlockReportWithNoBlock() {
        //given
        String userEmail = "test@gmail.com";
        String sort = "task-rate";
        Integer year = 2023;
        Integer month = 2;

        //when
        List<MonthlyBlockReportGetResponse> blockReport = reportService.getBlockReport(userEmail,
                sort, year, month);

        //then
        assertThat(blockReport.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("특정 연월의 블럭 리포트를 불러올 수 있다. 단 태스크가 없는 블록의 경우 무시된다.")
    void getBlockReportWithNoTaskBlock() {
        //given
        String userEmail = "test@gmail.com";
        String sort = "task-rate";
        Integer year = 2023;
        Integer month = 3;

        //when
        BlockCreateRequest blockCreateRequest = BlockCreateRequest.builder()
                .date("2023-03-21")
                .title("test")
                .emoji("😀")
                .backgroundColor("#ffffff")
                .isSecret(false)
                .build();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        Block block = blockCreateRequest.toEntity(user, date);
        blockRepository.save(block);
        List<MonthlyBlockReportGetResponse> blockReport = reportService.getBlockReport(userEmail,
                sort, year, month);

        //then
        assertThat(blockReport.size()).isEqualTo(8);
    }

    @Test
    @DisplayName("특정 연월의 블럭 리포트를 불러올 수 있다. 달성률이 높은 순으로 정렬한다.")
    void getBlockReportWithSortByTaskRate() {
        //given
        String userEmail = "test@gmail.com";
        String sort = "task-rate";
        Integer year = 2023;
        Integer month = 3;

        //when
        List<MonthlyBlockReportGetResponse> blockReport = reportService.getBlockReport(userEmail,
                sort, year, month);
        List<MonthlyBlockReportGetResponse> copyBlockReport = blockReport;
        copyBlockReport.sort(
                Comparator.comparing(MonthlyBlockReportGetResponse::getTaskRatePercentage)
                        .reversed());

        //then
        assertThat(blockReport).isEqualTo(copyBlockReport);
    }

    @Test
    @DisplayName("특정 연월의 블럭 리포트를 불러올 수 있다. 블럭이 많은 순으로 정렬한다.")
    void getBlockReportWithSortByBlockCount() {
        //given
        String userEmail = "test@gmail.com";
        String sort = "most-block";
        Integer year = 2023;
        Integer month = 3;

        //when
        List<MonthlyBlockReportGetResponse> blockReport = reportService.getBlockReport(userEmail,
                sort, year, month);
        List<MonthlyBlockReportGetResponse> copyBlockReport = blockReport;
        copyBlockReport.sort(
                Comparator.comparing(MonthlyBlockReportGetResponse::getBlockCount).reversed());

        //then
        assertThat(blockReport).isEqualTo(copyBlockReport);
    }
}
