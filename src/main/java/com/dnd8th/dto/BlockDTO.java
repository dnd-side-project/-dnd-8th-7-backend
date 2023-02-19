package com.dnd8th.dto;

import lombok.Data;

import java.util.List;

@Data
public class BlockDTO {
    private String color;
    private String icon;
    private String title;
    private Integer sumOfTask;
    private Integer sumOfDoneTask;
    private List<TaskDTO> tasks;

    public BlockDTO(
            String color, String icon, String title, Integer sumOfTask, Integer sumOfDoneTask, List<TaskDTO> tasks) {
        this.color = color;
        this.icon = icon;
        this.title = title;
        this.sumOfTask = sumOfTask;
        this.sumOfDoneTask = sumOfDoneTask;
        this.tasks = tasks;
    }
}
