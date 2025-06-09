package br.com.rfalessandro.razzieawards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class MaxMinProducerAwardsIntervalDTO {
    List<ProducerAwardsIntervalDTO> max;
    List<ProducerAwardsIntervalDTO> min;

}
