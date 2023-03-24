package com.dnd8th.dto.report;

import com.dnd8th.entity.Task;
import lombok.Builder;
import lombok.Data;

@Data
public class MonthlyTaskGetDTO {

    private Long id;
    private String contents;
    private Boolean status;

    @Builder
    public MonthlyTaskGetDTO(Task task) {
        this.id = task.getId();
        this.contents = task.getContents();
        this.status = task.getStatus();
    }
}
