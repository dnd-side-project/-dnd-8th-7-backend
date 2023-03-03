package com.dnd8th.dto.auth;

import com.dnd8th.entity.User;
import javax.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignUpResponse {

    @Email(message = "Invalid Email Format.")
    private String email;
    private String nickname;


    @Builder
    public UserSignUpResponse(User user) {
        this.email = user.getEmail();
        this.nickname = user.getName();
    }
}
