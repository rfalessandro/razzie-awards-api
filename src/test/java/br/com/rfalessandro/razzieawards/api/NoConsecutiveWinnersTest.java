package br.com.rfalessandro.razzieawards.api;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.rfalessandro.razzieawards.dto.MaxMinProducerAwardsIntervalDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import java.util.Map;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(NoConsecutiveWinnersTest.class)
public class NoConsecutiveWinnersTest implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("csv.file.path", "/noConsecutiveWinners.csv");
    }

    @Test
    void testShouldReturnEmptyListForMaxAndMin() {
        MaxMinProducerAwardsIntervalDTO result =
                given().when()
                        .get("/api/v1/producers/award-intervals/worst-movie")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .as(MaxMinProducerAwardsIntervalDTO.class);

        assertEquals(0, result.getMax().size(), "There should be no max consecutive winners");
        assertEquals(0, result.getMin().size(), "There should be no min consecutive winners");
    }
}
