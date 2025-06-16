package br.com.rfalessandro.razzieawards.repository;

import br.com.rfalessandro.razzieawards.dto.MaxMinProducerAwardsIntervalDTO;
import br.com.rfalessandro.razzieawards.dto.ProducerAwardsIntervalDTO;
import br.com.rfalessandro.razzieawards.model.Producer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ProducerRepository implements PanacheRepository<Producer> {

    @Transactional
    public Producer findOrCreate(String name) {
        return find("name", name)
                .firstResultOptional()
                .orElseGet(
                        () -> {
                            log.info("Creating producer: {}", name);
                            Producer producer = Producer.builder().name(name).build();
                            persist(producer);
                            return producer;
                        });
    }

    @SuppressWarnings("unchecked")
    public MaxMinProducerAwardsIntervalDTO findMaxMinAwardsProducer() {
        String query =
                """
             WITH producer_wins AS (
                SELECT
                    p.name as producer,
                    m.indication_year as following_year,
                    LAG(m.indication_year ) OVER (PARTITION BY p.id ORDER BY m.indication_year) as previous_year
                FROM producer p
                    inner join movie_producers mp
                        on p.id = mp.producer_id
                    inner join movie m
                        on mp.movie_id = m.id
                WHERE
                    m.winner = true
            ),
            intervals_tmp as (
                SELECT
                    producer,
                    following_year - previous_year as diff,
                    previous_year,
                    following_year
                FROM
                    producer_wins
                WHERE
                    previous_year is not null
            ),
            min_interval AS (
                SELECT
                    *,
                    1 as type2

                FROM
                    intervals_tmp
                WHERE
                    diff = (SELECT MIN(diff) FROM intervals_tmp)
            ),
            max_interval AS (
                SELECT
                    *,
                    2 as type2
                FROM
                    intervals_tmp
                WHERE
                    diff = (SELECT MAX(diff) FROM intervals_tmp)
            )
            SELECT
                *
            FROM
                min_interval
            UNION ALL
            SELECT
                *
            FROM
                max_interval
            """;

        List<ProducerAwardsIntervalDTO> max = new ArrayList<>();
        List<ProducerAwardsIntervalDTO> min = new ArrayList<>();
        getEntityManager().createNativeQuery(query).getResultList().stream()
                .forEach(
                        row -> {
                            Object[] rowArray = (Object[]) row;
                            ProducerAwardsIntervalDTO producerAwardsIntervalDTO =
                                    ProducerAwardsIntervalDTO.builder()
                                            .producer((String) rowArray[0])
                                            .interval((Short) rowArray[1])
                                            .previousWin((Short) rowArray[2])
                                            .followingWin((Short) rowArray[3])
                                            .build();
                            if ((Integer) rowArray[4] == 1) {
                                min.add(producerAwardsIntervalDTO);
                            } else {
                                max.add(producerAwardsIntervalDTO);
                            }
                        });
        return MaxMinProducerAwardsIntervalDTO.builder().min(min).max(max).build();
    }
}
