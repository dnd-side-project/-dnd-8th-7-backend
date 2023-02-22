package com.dnd8th.dto.block;

import com.dnd8th.dto.task.TaskPartDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockPartDto {
    private String color;
    private String icon;
    private String title;
    private Integer sumOfTask;
    private Integer sumOfDoneTask;
    private List<TaskPartDto> tasks = new ArrayList<>();

    @Builder
    public BlockPartDto(
            String color, String icon, String title, Integer sumOfTask, Integer sumOfDoneTask, List<TaskPartDto> tasks) {
        this.color = color;
        this.icon = icon;
        this.title = title;
        this.sumOfTask = sumOfTask;
        this.sumOfDoneTask = sumOfDoneTask;
        this.tasks = tasks;
    }
}
