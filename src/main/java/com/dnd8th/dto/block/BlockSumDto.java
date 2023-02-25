package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BlockSumDto {
    private Integer totalBlock;
    private Integer totalTask;
    private List<BlockPartDto> blocks;

    @Builder
    public BlockSumDto(Integer totalBlock, Integer totalTask, List<BlockPartDto> blocks){
        this.totalBlock = totalBlock;
        this.totalTask = totalTask;
        this.blocks = blocks;
    }
}
