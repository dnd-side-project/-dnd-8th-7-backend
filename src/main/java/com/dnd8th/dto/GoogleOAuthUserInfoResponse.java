package com.dnd8th.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GoogleOAuthUserInfoResponse {

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
