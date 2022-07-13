package me.iqpizza6349.dote.domain.team.entity;

import lombok.Getter;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.team.entity.embed.MemberTeamId;

import javax.persistence.*;

@Getter
@Entity
@IdClass(MemberTeamId.class)
public class MemberTeam {

    @Id
    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Id
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
