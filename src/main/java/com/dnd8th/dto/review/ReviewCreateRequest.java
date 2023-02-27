package com.dnd8th.dto.review;

import com.dnd8th.entity.Review;
import com.dnd8th.entity.User;
import com.dnd8th.util.validator.YYYYMMDD;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

    @YYYYMMDD
    @NotBlank
    private String date;

    //    @Emoji
    @NotBlank
    private String emoticon;

    @NotBlank
    private String review;

    @NotNull
    private Boolean isSecret;

    @Builder
    public ReviewCreateRequest(String date, String emoticon, String review, Boolean isSecret) {
        this.date = date;
        this.emoticon = emoticon;
        this.review = review;
        this.isSecret = isSecret;
    }

    public Review toEntity(User user, Date date) {
        return Review.builder()
                .user(user)
                .date(date)
                .emotion(emoticon)
                .retrospection(review)
                .retrospectionLock(isSecret)
                .build();
    }
}