package com.dnd8th.dto.task;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class TaskSumDto {
    private Integer numOfTasks;
    private Integer numOfDoneTask;
    private List<TaskPartDto> tasks;

    @Builder
    public TaskSumDto(Integer numOfTasks, Integer numOfDoneTask, List<TaskPartDto> tasks){
        this.numOfTasks = numOfTasks;
        this.numOfDoneTask = numOfDoneTask;
        this.tasks = tasks;
    }

}
