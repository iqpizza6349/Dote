package me.iqpizza6349.dote.domain.team.ro;

import lombok.Getter;
import me.iqpizza6349.dote.domain.team.entity.Team;

@Getter
public class TeamResponseDto {

    private final long id;
    private final String name;

    public TeamResponseDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }
}
