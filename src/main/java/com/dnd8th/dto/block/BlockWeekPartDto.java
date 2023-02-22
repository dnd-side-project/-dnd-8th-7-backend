package com.dnd8th.dto.block;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockWeekPartDto {
    private String date;
    private List<String> color = new ArrayList<>();

    @Builder
    public BlockWeekPartDto(String date, List<String> color) {
        this.date = date;
        this.color = color;
    }
}
