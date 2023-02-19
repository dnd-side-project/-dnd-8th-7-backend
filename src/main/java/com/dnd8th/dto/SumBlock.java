package com.dnd8th.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class SumBlock {
    private Integer totalBlock;
    private Integer totalTask;
    private List<BlockDTO> blocks;

    @Builder
    public SumBlock(Integer totalBlock, Integer totalTask, List<BlockDTO> blocks){
        this.totalBlock = totalBlock;
        this.totalTask = totalTask;
        this.blocks = blocks;
    }
}
