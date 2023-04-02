package com.dnd8th.dto.block;

import com.dnd8th.entity.Block;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class BlockCalendarGetDTO {
    private Long id;
    private Date date;
    private String blockColor;

    @Builder
    public BlockCalendarGetDTO(Block block) {
        this.id = block.getId();
        this.blockColor = block.getBlockColor();
        this.date = block.getDate();
    }
}
