package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BlockWeekPartDto {
    private Long blockId;
    private String backgroundColor;

    @Builder
    public BlockWeekPartDto(Long blockId, String backgroundColor){
        this.blockId = blockId;
        this.backgroundColor = backgroundColor;
    }
}
