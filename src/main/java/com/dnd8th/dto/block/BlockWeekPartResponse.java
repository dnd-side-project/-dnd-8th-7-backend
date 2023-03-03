package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockWeekPartResponse {
    private String date;
    private List<String> backgroundColors = new ArrayList<>();

    @Builder
    public BlockWeekPartResponse(String date, List<String> backgroundColors) {
        this.date = date;
        this.backgroundColors = backgroundColors;
    }
}
