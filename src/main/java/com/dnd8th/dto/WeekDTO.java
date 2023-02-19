package com.dnd8th.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeekDTO {
    private String date;
    private List<String> color = new ArrayList<>();

    @Builder
    public WeekDTO(String date, List<String> color) {
        this.date = date;
        this.color = color;
    }
}
