package br.com.rfalessandro.razzieawards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import br.com.rfalessandro.razzieawards.model.Producer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class ProducerAwardsIntervalDTO {
    public String producer;
    public short interval;
    public short previousWin;
    public short followingWin;

}
