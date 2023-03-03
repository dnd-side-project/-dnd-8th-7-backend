package com.dnd8th.dto.task;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskUpdateRequest {

    private String content;

    @Builder
    public TaskUpdateRequest(String content) {
        this.content = content;
    }
}
