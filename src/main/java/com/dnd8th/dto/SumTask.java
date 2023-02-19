package com.dnd8th.dto;

import lombok.Data;

import java.util.List;

@Data
public class SumTask {
    private Integer sumOfTask;
    private Integer sumOfDoneTask;
    private List<TaskDTO> tasks;

}