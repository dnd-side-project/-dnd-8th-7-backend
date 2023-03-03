package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockMainGetResponse {
    private String date;
    private Integer numOfTotalBlocks;
    private Integer numOfTotalTasks;
    private Long reviewId;
    private List<BlockPartDto> blocks = new ArrayList<>();

    @Builder
    public BlockMainGetResponse(String date, Integer numOfTotalBlocks, Integer numOfTotalTasks,
                                Long reviewId, List<BlockPartDto> blocks){
        this.date = date;
        this.numOfTotalBlocks = numOfTotalBlocks;
        this.numOfTotalTasks = numOfTotalTasks;
        this.reviewId = reviewId;
        this.blocks = blocks;
    }

}
