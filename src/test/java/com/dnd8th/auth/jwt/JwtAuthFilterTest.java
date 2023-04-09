package com.dnd8th.auth.jwt;

import com.dnd8th.common.AuthTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtAuthFilterTest extends AuthTest {

    @Test
    @DisplayName("정상적으로 토큰을 발급받을 수 있다.")
    void generateToken() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token;

        // when
        ExtractableResponse<Response> response = RestAssured.given()
                .header("Authorization", authHeader)
                .when()
                .get("/api/test")
                .then()
                .extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("토큰이 없는 경우 401 에러를 반환한다.")
    void withoutToken() {
        // given & when
        ExtractableResponse<Response> response = RestAssured.given()
                .header("Authorization", "")
                .when()
                .get("/api/test")
                .then()
                .extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    @DisplayName("토큰이 유효하지 않은 경우 401 에러를 반환한다.")
    void withInvalidToken() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token + "invalid";

        // when
        ExtractableResponse<Response> response = RestAssured.given()
                .header("Authorization", authHeader)
                .when()
                .get("/api/test")
                .then()
                .extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(401);
    }
}
