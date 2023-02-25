package com.dnd8th.dto.task;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class TaskSumDto {
    private Integer sumOfTask;
    private Integer sumOfDoneTask;
    private List<TaskPartDto> tasks;

    @Builder
    public TaskSumDto(Integer sumOfTask, Integer sumOfDoneTask, List<TaskPartDto> tasks){
        this.sumOfTask = sumOfTask;
        this.sumOfDoneTask = sumOfDoneTask;
        this.tasks = tasks;
    }

}
