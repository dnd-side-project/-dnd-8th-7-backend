package com.dnd8th.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeekDTO {
    private String date;
    private List<String> color = new ArrayList<>();
}