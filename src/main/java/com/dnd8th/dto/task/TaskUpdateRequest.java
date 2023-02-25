package com.dnd8th.dto.task;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class TaskUpdateRequest {
    @NotBlank
    private String content;

    @Builder
    public TaskUpdateRequest(String content) {
        this.content = content;
    }
}
