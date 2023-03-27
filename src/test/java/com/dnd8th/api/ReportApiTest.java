package com.dnd8th.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd8th.common.ReportTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportApiTest extends ReportTest {

    @Test
    @DisplayName("정상적으로 가장 높은 달성률의 블록을 확인할 수 있다.")
    void getHighestAchievementBlock() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token;
        Integer month = 3;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/most-task-rate/" + month)
                .then()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("정상적으로 가장 많이 만든 블록을 확인할 수 있다.")
    void getMostMadeBlock() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token;
        Integer month = 3;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/most-made-block/" + month)
                .then()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(200);
    }

}
