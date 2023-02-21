package com.dnd8th.dto.auth;

import com.dnd8th.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignUpResponse {

    private String email;
    private String name;


    @Builder
    public UserSignUpResponse(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
