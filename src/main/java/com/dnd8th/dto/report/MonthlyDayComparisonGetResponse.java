package com.dnd8th.dto.report;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthlyDayComparisonGetResponse {

    private Integer sunday;
    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;
    private Integer saturday;

    @Builder
    public MonthlyDayComparisonGetResponse(Integer sunday, Integer monday, Integer tuesday,
            Integer wednesday, Integer thursday, Integer friday, Integer saturday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }
}
