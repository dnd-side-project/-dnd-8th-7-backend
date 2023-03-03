package com.dnd8th.dto.review;

import com.dnd8th.util.validator.YYYYMMDD;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String emoji;
    @NotBlank
    private String review;
    @JsonProperty("isSecret")
    @NotNull
    private boolean secret;

    @Builder
    public ReviewGetResponse(String date, String emoji, String review, Boolean secret) {
        this.date = date;
        this.emoji = emoji;
        this.review = review;
        this.secret = secret;
    }
}
