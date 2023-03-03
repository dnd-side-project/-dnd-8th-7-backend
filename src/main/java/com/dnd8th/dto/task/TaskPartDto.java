package com.dnd8th.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class TaskPartDto {

    private Long taskId;
    private String task;
    @JsonProperty("isDone")
    private Boolean done;

    @Builder
    public TaskPartDto(Long taskId, String task, Boolean done) {
        this.taskId = taskId;
        this.task = task;
        this.done = done;
    }
}
