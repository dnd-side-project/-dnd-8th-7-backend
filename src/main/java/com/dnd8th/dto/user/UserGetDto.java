package com.dnd8th.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class UserGetDto {
    private String imgUrl;
    private String nickname;
    private String introduction;
    @JsonProperty("isSecret")
    private Boolean secret;

    @Builder
    public UserGetDto(String imgUrl, String nickname, String introduction, Boolean secret) {
        this.imgUrl = imgUrl;
        this.nickname = nickname;
        this.introduction = introduction;
        this.secret = secret;
    }
}
