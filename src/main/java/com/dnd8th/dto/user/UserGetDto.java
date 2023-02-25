package com.dnd8th.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserGetDto {
    private String imgPath;
    private String user;
    private String introduction;
    private Boolean lock;

    @Builder
    public UserGetDto(String imgPath, String user, String introduction, Boolean lock) {
        this.imgPath = imgPath;
        this.user = user;
        this.introduction = introduction;
        this.lock = lock;
    }
}
