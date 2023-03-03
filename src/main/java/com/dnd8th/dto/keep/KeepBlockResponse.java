package com.dnd8th.dto.keep;

import lombok.Builder;
import lombok.Data;

@Data
public class KeepBlockResponse {
    private Long blockId;
    private String backgroundColor;
    private String emoji;
    private String title;
    private Integer numOfTasks;

    @Builder
    public KeepBlockResponse(
            Long blockId, String backgroundColor, String emoji, String title, Integer numOfTasks) {
        this.blockId = blockId;
        this.backgroundColor = backgroundColor;
        this.emoji = emoji;
        this.title = title;
        this.numOfTasks = numOfTasks;
    }
}
