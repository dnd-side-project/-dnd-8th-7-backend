package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

@Data
public class BlockCalendarDto {
    private Long blockId;
    private String backgroundColor;

    @Builder
    public BlockCalendarDto(Long blockId, String backgroundColor){
        this.blockId = blockId;
        this.backgroundColor = backgroundColor;
    }
}
