package com.dnd8th.dto.block;

import lombok.Data;

import java.util.List;

@Data
public class BlockMainWeekGetResponse {
    private String user;
    private String imgPath;
    private List<BlockWeekPartDto> dailyBlocks;

}
