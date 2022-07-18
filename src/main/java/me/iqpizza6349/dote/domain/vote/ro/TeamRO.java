package me.iqpizza6349.dote.domain.vote.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamRO {

    private final String name;

    @JsonProperty("vote_count")
    private final int voteCount;
}
