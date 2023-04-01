package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockCalendarResponse {
    private String date;
    private List<BlockCalendarDto> backgroundColors = new ArrayList<>();

    @Builder
    public BlockCalendarResponse(String date, List<BlockCalendarDto> backgroundColors) {
        this.date = date;
        this.backgroundColors = backgroundColors;
    }
}
