package com.dnd8th.dto.task;

import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class TaskCreateResponse {
    @NotNull
    private Long blockId;

    @NotBlank
    private String content;

    @Builder
    public TaskCreateResponse(Long blockId, String content) {
        this.blockId = blockId;
        this.content = content;
    }
}