package br.com.rfalessandro.razzieawards.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class DefaultScenarioTest implements QuarkusTestProfile {
    @Test
    void testShouldReturnExactlyThoseWinners() {
        given().when()
                .get("/api/v1/producers/award-intervals/worst-movie")
                .then()
                .assertThat()
                .statusCode(200)
                .body("min", isA(java.util.List.class))
                .body("min", hasSize(1))
                .body("min[0].producer", equalTo("Joel Silver"))
                .body("min[0].interval", equalTo(1))
                .body("min[0].previousWin", equalTo(1990))
                .body("min[0].followingWin", equalTo(1991))
                .body("max", isA(java.util.List.class))
                .body("max", hasSize(1))
                .body("max[0].producer", equalTo("Matthew Vaughn"))
                .body("max[0].interval", equalTo(13))
                .body("max[0].previousWin", equalTo(2002))
                .body("max[0].followingWin", equalTo(2015));
    }
}
