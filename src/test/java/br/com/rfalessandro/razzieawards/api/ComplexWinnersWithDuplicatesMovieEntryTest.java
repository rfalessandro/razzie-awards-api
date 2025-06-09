package br.com.rfalessandro.razzieawards.api;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import br.com.rfalessandro.razzieawards.dto.MaxMinProducerAwardsIntervalDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(ComplexWinnersWithDuplicatesMovieEntryTest.class)
public class ComplexWinnersWithDuplicatesMovieEntryTest extends ComplexWinnersScenarioTest {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("csv.file.path", "/moviestest_duplicates.csv");
    }

}
