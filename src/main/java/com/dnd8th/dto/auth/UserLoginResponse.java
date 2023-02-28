package com.dnd8th.dto.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginResponse {

    private String token;
    private Boolean isNewUser;

    @Builder
    public UserLoginResponse(String token, Boolean isNewUser) {
        this.token = token;
        this.isNewUser = isNewUser;
    }
}
