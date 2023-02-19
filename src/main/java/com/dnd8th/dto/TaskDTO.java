package com.dnd8th.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private String task;
    private Boolean isDone;

    public TaskDTO(String task, Boolean isDone) {
        this.task = task;
        this.isDone = isDone;
    }
}
