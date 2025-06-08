package br.com.rfalessandro.razzieawards.repository;
import br.com.rfalessandro.razzieawards.model.Movie;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {

}
