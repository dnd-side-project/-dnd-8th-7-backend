package com.dnd8th.dto.report;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class MonthlyBlockGetDTO {

    private Long id;
    private String title;
    private Date date;
    private List<MonthlyTaskGetDTO> tasks;

    @Builder
    public MonthlyBlockGetDTO(Long id, String title, List<MonthlyTaskGetDTO> tasks) {
        this.id = id;
        this.title = title;
        this.tasks = tasks;
    }
}
