package br.com.rfalessandro.razzieawards.api;

import java.util.Map;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(ComplexWinnersWithCorruptedMovieEntryTest.class)
public class ComplexWinnersWithCorruptedMovieEntryTest extends ComplexWinnersScenarioTest {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("csv.file.path", "/moviestest_corruption.csv");
    }

}
