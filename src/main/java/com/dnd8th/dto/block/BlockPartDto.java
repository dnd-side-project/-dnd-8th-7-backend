package com.dnd8th.dto.block;

import com.dnd8th.dto.task.TaskPartDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockPartDto {
    private Long blockId;
    private String backgroundColor;
    private String emoji;
    private String title;
    private Integer numOfTasks;
    private Integer numOfDoneTask;
    private List<TaskPartDto> tasks = new ArrayList<>();

    @Builder
    public BlockPartDto(
            Long blockId, String backgroundColor, String emoji, String title, Integer numOfTasks,
            Integer numOfDoneTask, List<TaskPartDto> tasks) {
        this.blockId = blockId;
        this.backgroundColor = backgroundColor;
        this.emoji = emoji;
        this.title = title;
        this.numOfTasks = numOfTasks;
        this.numOfDoneTask = numOfDoneTask;
        this.tasks = tasks;
    }
}
