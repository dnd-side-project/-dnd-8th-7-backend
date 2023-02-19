package com.dnd8th.dto;

import lombok.Data;

import java.util.List;

@Data
public class SumBlock {
    private Integer totalBlock;
    private Integer totalTask;
    private List<BlockDTO> blocks;
}
