package br.com.rfalessandro.razzieawards.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class MaxMinProducerAwardsIntervalDTO {
    List<ProducerAwardsIntervalDTO> max;
    List<ProducerAwardsIntervalDTO> min;
}
