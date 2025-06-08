package br.com.rfalessandro.razzieawards.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import br.com.rfalessandro.razzieawards.dto.MovieDTO;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class CsvLoaderService {

    @Inject
    MovieService movieService;

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
        List<MovieDTO> movies = parseFile(csvPath);
        movies.forEach(movie ->
            movieService.createMovie(movie.toModel())
        );
	}

    public List<MovieDTO> parseFile(String csvPath) throws IOException {
        InputStream csvStream = getClass().getResourceAsStream(csvPath);
        if (csvStream == null) {
            log.error("CSV file not found at path: {}", csvPath);
            throw new RuntimeException("CSV file not found: " + csvPath);
        }
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
        return mapper.readerFor(MovieDTO.class)
            .with(schema)
            .<MovieDTO>readValues(csvStream)
            .readAll();
    }


}
