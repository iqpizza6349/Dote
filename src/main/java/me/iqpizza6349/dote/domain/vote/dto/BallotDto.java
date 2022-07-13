package me.iqpizza6349.dote.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class BallotDto {

    @JsonProperty("vote_id")
    private long voteId;

    @JsonProperty("team_id")
    private long teamId;

}
