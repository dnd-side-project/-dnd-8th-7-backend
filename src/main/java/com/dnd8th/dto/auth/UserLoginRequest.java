package com.dnd8th.dto.auth;

import com.dnd8th.entity.Role;
import com.dnd8th.entity.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequest {

    @NotBlank
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank
    private String nickname;

    private String imageUrl;

    @Builder
    public UserLoginRequest(String email, String nickname, String imageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(nickname)
                .imagePath(imageUrl)
                .role(Role.USER)
                .build();
    }
}
