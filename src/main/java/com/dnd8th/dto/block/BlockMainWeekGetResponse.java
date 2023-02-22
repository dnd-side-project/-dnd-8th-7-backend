package com.dnd8th.dto.block;

import lombok.Data;

import java.util.List;

@Data
public class BlockMainWeekGetResponse {
    private String user;
    private List<BlockWeekPartDto> dailyBlocks;

}
