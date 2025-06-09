package br.com.rfalessandro.razzieawards.service;

import br.com.rfalessandro.razzieawards.dto.MaxMinProducerAwardsIntervalDTO;
import br.com.rfalessandro.razzieawards.repository.ProducerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ProducerService {

    @Inject ProducerRepository producerRepository;

    public MaxMinProducerAwardsIntervalDTO getMaxMinAwardsInterval() {
        return producerRepository.findMaxMinAwardsProducer();
    }
}
