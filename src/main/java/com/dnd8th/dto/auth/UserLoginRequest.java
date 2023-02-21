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
    @Email(message = "이메일을 양식을 지켜주세요.")
    private String email;

    @NotBlank
    private String name;

    private String imagePath;

    @Builder
    public UserLoginRequest(String email, String name, String imagePath) {
        this.email = email;
        this.name = name;
        this.imagePath = imagePath;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .imagePath(imagePath)
                .role(Role.USER)
                .build();
    }
}
