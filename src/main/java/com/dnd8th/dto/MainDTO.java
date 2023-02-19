package com.dnd8th.dto;

import lombok.Data;

import java.util.List;

@Data
public class MainDTO {
    private String date;
    private Integer totalBlock;
    private Integer totalTask;
    private List<BlockDTO> blocks;
}
