package br.com.rfalessandro.razzieawards.repository;
import br.com.rfalessandro.razzieawards.model.Producer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ProducerRepository implements PanacheRepository<Producer> {
    public Producer findOrCreate(String name) {
       return find("name", name).firstResultOptional()
            .orElseGet(() -> {
                log.info("Creating producer: {}", name);
                Producer producer = Producer.builder()
                                            .name(name)
                                            .build();
                persist(producer);
                return producer;
            });
    }

}
