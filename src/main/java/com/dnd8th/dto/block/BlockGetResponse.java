package com.dnd8th.dto.block;

import com.dnd8th.util.validator.ColorHex;
import com.dnd8th.util.validator.YYYYMMDD;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class BlockGetResponse {
    @YYYYMMDD
    @NotBlank
    private String date;

    @NotBlank
    private String title;

    @NotBlank
    private String emoji;

    @ColorHex
    @NotBlank
    private String backgroundColor;

    @JsonProperty("isSecret")
    @NotNull
    private Boolean secret;

    @Builder
    public BlockGetResponse(String date, String title, String emoji, String backgroundColor,
                              Boolean secret) {
        this.date = date;
        this.title = title;
        this.emoji = emoji;
        this.backgroundColor = backgroundColor;
        this.secret = secret;
    }
}
