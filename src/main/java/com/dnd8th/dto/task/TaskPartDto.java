package com.dnd8th.dto.task;

import lombok.Builder;
import lombok.Data;

@Data
public class TaskPartDto {

    private Long taskId;
    private String task;
    private Boolean isDone;

    @Builder
    public TaskPartDto(Long taskId, String task, Boolean isDone) {
        this.taskId = taskId;
        this.task = task;
        this.isDone = isDone;
    }
}
