package com.dnd8th.dto.report;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MostTaskRateBlockGetResponse {

    private String content;

    public MostTaskRateBlockGetResponse(String content) {
        this.content = content;
    }
}
