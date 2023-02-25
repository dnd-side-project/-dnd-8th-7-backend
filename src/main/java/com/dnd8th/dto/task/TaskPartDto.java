package com.dnd8th.dto.task;

import lombok.Builder;
import lombok.Data;

@Data
public class TaskPartDto {
    private Long task_id;
    private String task;
    private Boolean isDone;

    @Builder
    public TaskPartDto(Long task_id, String task, Boolean isDone) {
        this.task_id = task_id;
        this.task = task;
        this.isDone = isDone;
    }
}
