package com.dnd8th.dto.review;

import com.dnd8th.entity.Review;
import com.dnd8th.entity.User;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

    @NotBlank
    private String date;

    @NotBlank
    private String emoticon;

    @NotBlank
    private String review;
    
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
