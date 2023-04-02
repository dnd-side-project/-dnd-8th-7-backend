package com.dnd8th.dto.report;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthlyBlockReportGetResponse {

    private String title;
    private Integer blockCount;
    private Integer achievedTaskCount;
    private Integer totalTaskCount;
    private Integer taskRatePercentage;

    @Builder
    public MonthlyBlockReportGetResponse(String title, Integer blockCount,
            Integer achievedTaskCount,
            Integer totalTaskCount, Integer taskRatePercentage) {
        this.title = title;
        this.blockCount = blockCount;
        this.achievedTaskCount = achievedTaskCount;
        this.totalTaskCount = totalTaskCount;
        this.taskRatePercentage = taskRatePercentage;
    }
}
