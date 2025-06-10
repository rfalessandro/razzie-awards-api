package br.com.rfalessandro.razzieawards.service;

import br.com.rfalessandro.razzieawards.dto.MovieDTO;
import br.com.rfalessandro.razzieawards.exception.CsvProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Slf4j
public class CsvLoaderService {

    @Inject MovieService movieService;

    @ConfigProperty(name = "csv.file.path")
    String csvPath;

    @Transactional
    public void onApplicationStart(@Observes StartupEvent event) throws IOException {
        log.info("Starting CSV Loader");
        if (movieService.count() > 0) {
            log.info("Movies already loaded, ignoring CSV file");
        } else {
            importMoviesFromFile();
        }
    }

    private void importMoviesFromFile() throws IOException {
        log.info("Loading movies from {}", csvPath);
        try {
            List<MovieDTO> movies = parseFile(csvPath);
            movies.forEach(movie -> movieService.createMovie(movie.toModel()));
        } catch (Exception e) {
            log.error("Error loading movies from CSV file", e);
            throw new CsvProcessingException("Error loading movies from CSV file", e);
        }
    }

    public List<MovieDTO> parseFile(String csvPath) throws IOException {
        InputStream csvStream = getExternalCsvStream(csvPath);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
        MappingIterator<MovieDTO> iter =
                mapper.readerFor(MovieDTO.class).with(schema).readValues(csvStream);

        List<MovieDTO> validRows = new java.util.ArrayList<>();
        while (iter.hasNextValue()) {
            try {
                MovieDTO row = iter.nextValue();
                validRows.add(row);
            } catch (Exception e) {
                log.error("Corrupted row: {}", e.getMessage());
            }
        }
        return validRows;
    }

    public InputStream getExternalCsvStream(String csvPath) {
        try {
            Path path = Paths.get(csvPath);
            if (Files.exists(path)) {
                return Files.newInputStream(path);
            }

            InputStream resourceStream = getClass().getResourceAsStream("/" + csvPath);
            if (resourceStream != null) {
                return resourceStream;
            }
            throw new CsvProcessingException("Error loading CSV file: " + csvPath, null);
        } catch (Exception e) {
            throw new CsvProcessingException("Error loading CSV file: " + csvPath, e);
        }
    }
}
