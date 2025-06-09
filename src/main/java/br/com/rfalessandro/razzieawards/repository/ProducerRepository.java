package br.com.rfalessandro.razzieawards.repository;
import java.util.ArrayList;
import java.util.List;

import br.com.rfalessandro.razzieawards.dto.MaxMinProducerAwardsIntervalDTO;
import br.com.rfalessandro.razzieawards.dto.ProducerAwardsIntervalDTO;
import br.com.rfalessandro.razzieawards.model.Producer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ProducerRepository implements PanacheRepository<Producer> {

    @Transactional
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


    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
    public MaxMinProducerAwardsIntervalDTO findMaxMinAwardsProducer() {
        List resultList = getEntityManager()
            .createNativeQuery("""
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
                    following_year,
                    previous_year
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
            FROM min_interval
            union all
            SELECT
                *
            FROM
                max_interval
            """)
            .getResultList();
        resultList.forEach(row -> log.info("##@!: {}", row));
        List<ProducerAwardsIntervalDTO> max = new ArrayList<>();
        List<ProducerAwardsIntervalDTO> min = new ArrayList<>();
        resultList.forEach(row2 -> {
            Object[] row = (Object[]) row2;
            if ((Integer) row[4] == 1) {
                min.add(ProducerAwardsIntervalDTO.builder()
                    .producer((String) row[0])
                    .interval((Short) row[1])
                    .previousWin((Short) row[2])
                    .followingWin((Short) row[3])
                    .build());
            } else {
                max.add(ProducerAwardsIntervalDTO.builder()
                    .producer((String) row[0])
                    .interval((Short) row[1])
                    .previousWin((Short) row[2])
                    .followingWin((Short) row[3])
                    .build());
            }
        });
        return MaxMinProducerAwardsIntervalDTO.builder()
            .min(min)
            .max(max)
            .build();
    }
}
