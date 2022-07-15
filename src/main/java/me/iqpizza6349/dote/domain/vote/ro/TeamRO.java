package me.iqpizza6349.dote.domain.vote.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import me.iqpizza6349.dote.domain.team.entity.MemberTeam;

@Getter
public class TeamRO {

    private final String name;

    @JsonProperty("vote_count")
    private final int voteCount;

    public TeamRO(MemberTeam memberTeam) {
        this.name = memberTeam.getTeam().getName();
        this.voteCount = memberTeam.getCountMember();
    }
}
