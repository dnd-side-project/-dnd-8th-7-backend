package com.dnd8th.dto.report;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportMonthlyComparisonGetResponse {

    private Integer lastMonthAchievementRate;
    private Integer nowMonthAchievementRate;

    @Builder
    public ReportMonthlyComparisonGetResponse(Integer lastMonthAchievementRate,
            Integer nowMonthAchievementRate) {
        this.lastMonthAchievementRate = lastMonthAchievementRate;
        this.nowMonthAchievementRate = nowMonthAchievementRate;
    }
}
