package com.dnd8th.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MainDTO {
    private String date;
    private Integer totalBlock;
    private Integer totalTask;
    private List<BlockDTO> blocks = new ArrayList<>();

    @Builder
    public MainDTO(String date, Integer totalBlock, Integer totalTask, List<BlockDTO> blocks){
        this.date = date;
        this.totalBlock = totalBlock;
        this.totalTask = totalTask;
        this.blocks = blocks;
    }

}
