package com.dnd8th.dto.review;

import com.dnd8th.entity.Review;
import com.dnd8th.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequest {
    @NotBlank
    private String date;
    @NotBlank
    private String emoticon;
    @NotBlank
    private String review;
    @NotNull
    private boolean isSecret;

    @Builder
    public ReviewUpdateRequest(String date, String emoticon, String review, Boolean isSecret) {
        this.date = date;
        this.emoticon = emoticon;
        this.review = review;
        this.isSecret = isSecret;
    }
}