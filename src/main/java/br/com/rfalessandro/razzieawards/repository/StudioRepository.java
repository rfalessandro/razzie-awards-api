package br.com.rfalessandro.razzieawards.repository;
import br.com.rfalessandro.razzieawards.model.Studio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class StudioRepository implements PanacheRepository<Studio> {

    @Transactional
    public Studio findOrCreate(String name) {
        return find("name", name).firstResultOptional()
            .orElseGet(() -> {
                log.info("Creating studio: {}", name);
                Studio studio = Studio.builder()
                                        .name(name)
                                        .build();
                persist(studio);
                return studio;
            });
    }
}
