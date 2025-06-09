package br.com.rfalessandro.razzieawards.service;

import java.util.List;

import br.com.rfalessandro.razzieawards.dto.MaxMinProducerAwardsIntervalDTO;
import br.com.rfalessandro.razzieawards.model.Producer;
import br.com.rfalessandro.razzieawards.repository.ProducerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ProducerService {

    @Inject
    ProducerRepository producerRepository;

    public MaxMinProducerAwardsIntervalDTO getMaxMinAwardsInterval() {
        return producerRepository.findMaxMinAwardsProducer();
    }
}
