package br.com.rfalessandro.razzieawards.dto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import br.com.rfalessandro.razzieawards.model.Movie;
import br.com.rfalessandro.razzieawards.model.Producer;
import br.com.rfalessandro.razzieawards.model.Studio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class MovieDTO {
    private String title;
    private short year;
    private String studios;
    private String producers;
    private boolean winner;

    public void setWinner(String value) {
        this.winner = "yes".equalsIgnoreCase(value.trim());
    }


    public Movie toModel() {
        List<Studio> studios = parseStudio();
        List<Producer> producers = parseProducer();
        return Movie.builder()
                .title(this.getTitle())
                .year(this.getYear())
                .studios(studios)
                .producers(producers)
                .build();
    }


    private List<Studio> parseStudio() {
        String studiosStr = this.getStudios();
        if (studiosStr == null || studiosStr.trim().isEmpty()) {
            return new ArrayList<>();
        }
        studiosStr = studiosStr.replace(" and ", ",");
        return Arrays.stream(studiosStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(name ->
                    Studio.builder()
                        .name(name)
                        .build()
                )
                .collect(Collectors.toList());
    }

    private List<Producer> parseProducer() {
        String producersStr = this.getProducers();
        if (producersStr == null || producersStr.trim().isEmpty()) {
            return new ArrayList<>();
        }
        producersStr = producersStr.replace(" and ", ",");
        return Arrays.stream(producersStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(name ->
                    Producer.builder()
                        .name(name)
                        .build()
                )
                .collect(Collectors.toList());
    }



}
