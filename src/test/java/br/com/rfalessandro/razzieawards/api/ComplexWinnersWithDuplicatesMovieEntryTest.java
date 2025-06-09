package br.com.rfalessandro.razzieawards.api;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import java.util.Map;

@QuarkusTest
@TestProfile(ComplexWinnersWithDuplicatesMovieEntryTest.class)
public class ComplexWinnersWithDuplicatesMovieEntryTest extends ComplexWinnersScenarioTest {

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of("csv.file.path", "/moviestest_duplicates.csv");
    }
}
