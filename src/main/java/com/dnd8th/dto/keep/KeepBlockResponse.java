package com.dnd8th.dto.keep;

import lombok.Builder;
import lombok.Data;

@Data
public class KeepBlockResponse {
    private Long blockId;
    private String color;
    private String icon;
    private String title;
    private Integer sumOfTask;

    @Builder
    public KeepBlockResponse(
            Long blockId, String color, String icon, String title, Integer sumOfTask) {
        this.blockId = blockId;
        this.color = color;
        this.icon = icon;
        this.title = title;
        this.sumOfTask = sumOfTask;
    }
}
