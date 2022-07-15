package me.iqpizza6349.dote.domain.vote.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BallotRO {

    @JsonProperty("team_id")
    private final long teamId;

    private final String message;

    public BallotRO(long teamId) {
        this.teamId = teamId;
        this.message = "투표하였습니다.";
    }
}
