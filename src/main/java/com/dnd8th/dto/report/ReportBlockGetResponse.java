package com.dnd8th.dto.report;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportBlockGetResponse {

    private String content;

    public ReportBlockGetResponse(String content) {
        this.content = content;
    }
}
