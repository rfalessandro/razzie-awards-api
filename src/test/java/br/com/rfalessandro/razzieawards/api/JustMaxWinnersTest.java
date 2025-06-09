package br.com.rfalessandro.razzieawards.api;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import br.com.rfalessandro.razzieawards.dto.MaxMinProducerAwardsIntervalDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(JustMaxWinnersTest.class)
public class JustMaxWinnersTest implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("csv.file.path", "/justMaxWinnersTest.csv");
    }

    @Test
    void testNoConsecutiveWinners() {
        MaxMinProducerAwardsIntervalDTO result = given()
          .when().get("/api/v1/producers/award-intervals")
          .then()
            .assertThat()
            .statusCode(200)
            .extract().as(
                MaxMinProducerAwardsIntervalDTO.class
            );

        assertEquals(2, result.getMax().size());
        int interval = 5;
        result.getMax().forEach(ci -> {
            assertEquals(interval, ci.getInterval(), "The interval should be " + interval);
        });
        System.out.println("###############@! {}" + result);
    }

}
