package br.com.rfalessandro.razzieawards.service;

import java.util.List;
import java.util.stream.Collectors;

import br.com.rfalessandro.razzieawards.model.Movie;
import br.com.rfalessandro.razzieawards.model.Producer;
import br.com.rfalessandro.razzieawards.model.Studio;
import br.com.rfalessandro.razzieawards.repository.MovieRepository;
import br.com.rfalessandro.razzieawards.repository.ProducerRepository;
import br.com.rfalessandro.razzieawards.repository.StudioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class MovieService {

    @Inject
    MovieRepository movieRepository;

    @Inject
    StudioRepository studioRepository;

    @Inject
    ProducerRepository producerRepository;


    @Transactional
    public Movie createMovie(Movie movie) {
        loadOrCreateStudios(movie);
        loadOrCreateProducers(movie);
        movieRepository.persist(movie);
        log.info("Created movie: {}", movie);
        return movie;
    }

    private void loadOrCreateStudios(Movie movie) {
        List<Studio> studios = movie.getStudios()
            .stream()
            .map(studio -> studioRepository.findOrCreate(studio.getName()))
            .toList();

        movie.setStudios(studios);
    }

    private void loadOrCreateProducers(Movie movie) {
        List<Producer> producers = movie.getProducers()
            .stream()
            .map(producer -> producerRepository.findOrCreate(producer.getName()))
            .toList();

        movie.setProducers(producers);
    }

	public long count() {
        return movieRepository.count();
    }
}
