package com.dnd8th.dto.block;

import com.dnd8th.dto.block.BlockPartDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockMainGetResponse {
    private String date;
    private Integer totalBlock;
    private Integer totalTask;
    private List<BlockPartDto> blocks = new ArrayList<>();

    @Builder
    public BlockMainGetResponse(String date, Integer totalBlock, Integer totalTask, List<BlockPartDto> blocks){
        this.date = date;
        this.totalBlock = totalBlock;
        this.totalTask = totalTask;
        this.blocks = blocks;
    }

}
