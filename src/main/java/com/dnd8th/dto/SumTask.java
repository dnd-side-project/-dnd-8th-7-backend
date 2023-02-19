package com.dnd8th.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SumTask {
    private Integer sumOfTask;
    private Integer sumOfDoneTask;
    private List<TaskDTO> tasks;

    @Builder
    public SumTask(Integer sumOfTask, Integer sumOfDoneTask, List<TaskDTO> tasks){
        this.sumOfTask = sumOfTask;
        this.sumOfDoneTask = sumOfDoneTask;
        this.tasks = tasks;
    }

}
