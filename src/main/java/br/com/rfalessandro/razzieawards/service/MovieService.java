package br.com.rfalessandro.razzieawards.service;

import br.com.rfalessandro.razzieawards.model.Movie;
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
        movie.getStudios()
            .forEach(studio -> studioRepository.findOrCreate(studio.getName()));
        movie.getProducers()
            .forEach(producer -> producerRepository.findOrCreate(producer.getName()));
        movieRepository.persist(movie);
        log.info("Persisted movie: {}", movie);
        return movie;
    }


	public long count() {
        return movieRepository.count();
    }
}
