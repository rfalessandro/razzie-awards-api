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
@TestProfile(ComplexWinnersScenarioTest.class)
public class ComplexWinnersScenarioTest implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("csv.file.path", "/moviestest.csv");
    }

    MaxMinProducerAwardsIntervalDTO check_request_and_return_result() {
        return given().when()
                .get("/api/v1/producers/award-intervals/worst-movie")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(MaxMinProducerAwardsIntervalDTO.class);
    }

    @Test
    void testShouldReturnCorrectMaxMinWinnersCount() {
        MaxMinProducerAwardsIntervalDTO result = check_request_and_return_result();
        assertEquals(2, result.getMax().size());
        assertEquals(3, result.getMin().size());
    }

    @Test
    void testShouldReturnTheCorrectIntervals() {
        MaxMinProducerAwardsIntervalDTO result = check_request_and_return_result();
        result.getMax()
                .forEach(
                        ci -> {
                            int interval = 14;
                            assertEquals(
                                    interval,
                                    ci.getInterval(),
                                    "The interval should be " + interval);
                        });
        result.getMin()
                .forEach(
                        ci -> {
                            int interval = 1;
                            assertEquals(
                                    interval,
                                    ci.getInterval(),
                                    "The interval should be " + interval);
                        });
    }

    @Test
    void testShouldContainsExactlyTheFollowingProducers() {
        MaxMinProducerAwardsIntervalDTO result = check_request_and_return_result();
        int count =
                result.getMax().stream()
                        .filter(ci -> ci.getProducer().equals("Joel Silver"))
                        .toList()
                        .size();
        assertEquals(1, count, "There should be only one max consecutive winner for Joel Silver");

        int count2 =
                result.getMax().stream()
                        .filter(ci -> ci.getProducer().equals("ABCD_DE_MAX"))
                        .toList()
                        .size();
        assertEquals(1, count2, "There should be only one max consecutive winner for ABCD_DE_MAX");

        int count3 =
                result.getMin().stream()
                        .filter(ci -> ci.getProducer().equals("ABCD_DE"))
                        .toList()
                        .size();
        assertEquals(2, count3, "There should be only two min consecutive winner for ABCD_DE");

        int count4 =
                result.getMax().stream()
                        .filter(ci -> ci.getProducer().equals("Joel Silver"))
                        .toList()
                        .size();
        assertEquals(
                1, count4, "There should be only one min consecutive winner for Joel Silver");
    }
}
