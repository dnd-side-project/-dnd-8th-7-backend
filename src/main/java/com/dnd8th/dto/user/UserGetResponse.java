package com.dnd8th.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserGetResponse {
    @NotBlank
    private String imgPath;
    @NotBlank
    private String user;
    @NotBlank
    private String introduction;

    private Boolean lock;

    @Builder
    public UserGetResponse(String imgPath, String user, String introduction, Boolean lock) {
        this.imgPath = imgPath;
        this.user = user;
        this.introduction = introduction;
        this.lock = lock;
    }
}
