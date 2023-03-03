package com.dnd8th.dto.review;

import com.dnd8th.util.validator.YYYYMMDD;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequest {
    @NotBlank
    @YYYYMMDD
    private String date;
    @NotBlank
    private String emoji;
    @NotBlank
    private String review;
    @NotNull
    private boolean isSecret;

    @Builder
    public ReviewUpdateRequest(String date, String emoji, String review, Boolean isSecret) {
        this.date = date;
        this.emoji = emoji;
        this.review = review;
        this.isSecret = isSecret;
    }
}