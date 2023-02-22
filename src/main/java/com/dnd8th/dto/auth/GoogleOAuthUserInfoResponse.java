package com.dnd8th.dto.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import lombok.Getter;

@Getter
public class GoogleOAuthUserInfoResponse {

    @Email(message = "Invalid Email Format.")
    private String email;
    private String givenName;
    private String familyName;
    private String imagePath;

    @JsonCreator
    public GoogleOAuthUserInfoResponse(@JsonProperty("email") String email,
            @JsonProperty("given_name") String givenName,
            @JsonProperty("family_name") String familyName,
            @JsonProperty("picture") String imagePath) {
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
        this.imagePath = imagePath;
    }

}
