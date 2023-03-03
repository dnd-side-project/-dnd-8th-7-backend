package com.dnd8th.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserGetResponse {
    @NotBlank
    private String imgUrl;
    @NotBlank
    private String nickname;
    @NotBlank
    private String introduction;
    @JsonProperty("isSecret")
    @NotNull
    private Boolean secret;

    @Builder
    public UserGetResponse(String imgUrl, String nickname, String introduction, Boolean secret) {
        this.imgUrl = imgUrl;
        this.nickname = nickname;
        this.introduction = introduction;
        this.secret = secret;
    }
}
