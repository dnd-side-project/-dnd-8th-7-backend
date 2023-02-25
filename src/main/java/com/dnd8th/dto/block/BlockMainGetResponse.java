package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockMainGetResponse {
    private String date;
    private Integer totalBlock;
    private Integer totalTask;
    private Long reviewId;
    private List<BlockPartDto> blocks = new ArrayList<>();

    @Builder
    public BlockMainGetResponse(String date, Integer totalBlock, Integer totalTask, Long reviewId, List<BlockPartDto> blocks){
        this.date = date;
        this.totalBlock = totalBlock;
        this.totalTask = totalTask;
        this.reviewId = reviewId;
        this.blocks = blocks;
    }

}
