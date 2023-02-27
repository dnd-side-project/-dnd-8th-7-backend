package com.dnd8th.dto.task;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class TaskCreateResponse {
    @NotNull
    private Long taskId;

    @NotBlank
    private String content;

    @Builder
    public TaskCreateResponse(Long taskId, String content) {
        this.taskId = taskId;
        this.content = content;
    }
}
