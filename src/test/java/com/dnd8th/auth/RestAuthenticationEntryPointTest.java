package com.dnd8th.auth;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd8th.common.AuthTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RestAuthenticationEntryPointTest extends AuthTest {

    @Test
    @DisplayName("인증되지 않은 사용자가 접근할 경우 401 에러를 반환한다.")
    void unAuthorizedAccess() {
        // given & when
        ExtractableResponse<Response> unAuthorizedResponse = RestAssured.given()
                .header("Authorization", "")
                .when()
                .get("/api/review")
                .then()
                .extract();

        // then
        assertThat(unAuthorizedResponse.statusCode()).isEqualTo(401);
    }
}
