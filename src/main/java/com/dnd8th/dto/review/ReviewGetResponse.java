package com.dnd8th.dto.review;

import com.dnd8th.util.validator.YYYYMMDD;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ReviewGetResponse {
    @NotBlank
    @YYYYMMDD
    private String date;
    @NotBlank
    private String emoticon;
    @NotBlank
    private String review;
    @NotNull
    private boolean secret;

    @Builder
    public ReviewGetResponse(String date, String emoticon, String review, Boolean secret) {
        this.date = date;
        this.emoticon = emoticon;
        this.review = review;
        this.secret = secret;
    }
}
