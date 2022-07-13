package me.iqpizza6349.dote.domain.team.entity.embed;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor @NoArgsConstructor
public class MemberTeamId implements Serializable {

    private int member;
    private long team;

}
