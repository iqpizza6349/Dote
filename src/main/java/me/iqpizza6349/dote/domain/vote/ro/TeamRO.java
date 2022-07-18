package me.iqpizza6349.dote.domain.vote.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
public class TeamRO {

    private final String name;

    @JsonProperty("vote_count")
    private final BigInteger voteCount;
}
