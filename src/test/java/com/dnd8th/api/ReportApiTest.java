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
        Integer year = 2023;
        Integer month = 3;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/most-task-rate/" + year + "/" + month)
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
        Integer year = 2023;
        Integer month = 3;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/most-made-block/" + year + "/" + month)
                .then()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("정상적으로 가장 많이 만든 블록을 확인할 수 있다.")
    void getLeastTaskRateBlock() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token;
        Integer year = 2023;
        Integer month = 3;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/least-task-rate/" + year + "/" + month)
                .then()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(200);
    }


    @Test
    @DisplayName("정상적으로 달성률을 가져올 수 있다.")
    void getAchieveRate() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/monthly-comparison/2023/3/2")
                .then()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("정상적으로 요일별 달성률을 가져올 수 있다.")
    void getAchieveRateByDay() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/monthly-day-comparison/2023/3")
                .then()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("정상적으로 요일별 달성률을 가져올 수 있다.")
    void getBlockReport() {
        // given
        String token = jwtProviderService.generateToken("test@gmail.com", "");
        String authHeader = "Bearer " + token;

        // when
        ExtractableResponse<Response> extractableResponse = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/block-report/task-rate/2023/3")
                .then()
                .extract();

        ExtractableResponse<Response> extractableResponse2 = RestAssured.given()
                .log().all()
                .header("Authorization", authHeader)
                .when()
                .get("/api/report/block-report/most-block/2023/3")
                .then()
                .extract();

        // then
        assertThat(extractableResponse.statusCode()).isEqualTo(200);
        assertThat(extractableResponse2.statusCode()).isEqualTo(200);
    }
}
