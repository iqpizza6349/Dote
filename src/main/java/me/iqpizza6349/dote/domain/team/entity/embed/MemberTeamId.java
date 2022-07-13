package me.iqpizza6349.dote.domain.team.entity.embed;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor @NoArgsConstructor
public class MemberTeamId implements Serializable {

    private int member;
    private long team;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberTeamId that = (MemberTeamId) o;
        return member == that.member && team == that.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, team);
    }
}
