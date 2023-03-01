package com.dnd8th.dto.block;

import com.dnd8th.util.validator.ColorHex;
import com.dnd8th.util.validator.YYYYMMDD;
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
    private String emoticon;

    @ColorHex
    @NotBlank
    private String blockColor;

    @NotNull
    private Boolean isSecret;

    @Builder
    public BlockGetResponse(String date, String title, String emoticon, String blockColor,
                              Boolean isSecret) {
        this.date = date;
        this.title = title;
        this.emoticon = emoticon;
        this.blockColor = blockColor;
        this.isSecret = isSecret;
    }
}
