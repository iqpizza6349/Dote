package me.iqpizza6349.dote.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class BallotDto {

    @JsonProperty("team_id")
    @NotNull(message = "투표할 항목은 필수 입력값입니다.")
    private long teamId;

}
