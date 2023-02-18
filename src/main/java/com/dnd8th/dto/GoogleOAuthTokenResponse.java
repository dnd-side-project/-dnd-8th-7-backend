package com.dnd8th.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@JsonNaming(SnakeCaseStrategy.class)
@Getter
public class GoogleOAuthTokenResponse {

    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String refreshToken;
    private String idToken;
}
