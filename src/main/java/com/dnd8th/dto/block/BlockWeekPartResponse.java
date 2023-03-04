package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockWeekPartResponse {
    private String date;
    private List<BlockWeekPartDto> backgroundColors = new ArrayList<>();

    @Builder
    public BlockWeekPartResponse(String date, List<BlockWeekPartDto> backgroundColors) {
        this.date = date;
        this.backgroundColors = backgroundColors;
    }
}
