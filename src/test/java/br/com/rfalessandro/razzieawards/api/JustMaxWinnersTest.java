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
@TestProfile(JustMaxWinnersTest.class)
public class JustMaxWinnersTest implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("csv.file.path", "/justMaxWinnersTest.csv");
    }

    MaxMinProducerAwardsIntervalDTO check_request_and_return_result() {
        return given().when()
                .get("/api/v1/producers/award-intervals")
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
        assertEquals(2, result.getMin().size());
        int interval = 5;
        result.getMax()
                .forEach(
                        ci -> {
                            assertEquals(
                                    interval,
                                    ci.getInterval(),
                                    "The interval should be " + interval);
                        });
        result.getMin()
                .forEach(
                        ci -> {
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
                        .filter(ci -> ci.getProducer().equals("Kevin Costner"))
                        .toList()
                        .size();
        assertEquals(1, count, "There should be only one max consecutive winner for Kevin Costner");
        int count2 =
                result.getMax().stream()
                        .filter(ci -> ci.getProducer().equals("Sherry Lansing"))
                        .toList()
                        .size();
        assertEquals(
                1, count2, "There should be only one max consecutive winner for Sherry Lansing");
        int countMin =
                result.getMin().stream()
                        .filter(ci -> ci.getProducer().equals("Kevin Costner"))
                        .toList()
                        .size();
        assertEquals(
                1, countMin, "There should be only one min consecutive winner for Kevin Costner");
        int countMin2 =
                result.getMin().stream()
                        .filter(ci -> ci.getProducer().equals("Sherry Lansing"))
                        .toList()
                        .size();
        assertEquals(
                1, countMin2, "There should be only one min consecutive winner for Sherry Lansing");
        System.out.println(result);
    }
}
