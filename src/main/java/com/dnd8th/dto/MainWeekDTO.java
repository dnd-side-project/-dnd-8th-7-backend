package com.dnd8th.dto;

import lombok.Data;

import java.util.List;

@Data
public class MainWeekDTO {
    private String user;
    private List<WeekDTO> dailyBlocks;

}
