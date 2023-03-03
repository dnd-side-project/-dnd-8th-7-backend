package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BlockSumDto {
    private Integer numOfTotalBlocks;
    private Integer numOfTotalTasks;
    private List<BlockPartDto> blocks;

    @Builder
    public BlockSumDto(Integer numOfTotalBlocks, Integer numOfTotalTasks, List<BlockPartDto> blocks){
        this.numOfTotalBlocks = numOfTotalBlocks;
        this.numOfTotalTasks = numOfTotalTasks;
        this.blocks = blocks;
    }
}
