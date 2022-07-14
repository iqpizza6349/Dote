package me.iqpizza6349.dote.domain.team.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.team.entity.embed.MemberTeamId;
import me.iqpizza6349.dote.global.exception.BusinessException;
import org.hibernate.annotations.Formula;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@NoArgsConstructor
@IdClass(MemberTeamId.class)
public class MemberTeam implements Serializable {

    @Id
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "team_id")
    private Team team;

    @Id
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberTeam(Team team, Member member) {
        this.team = team;
        this.member = member;
        this.countMember = 0;
    }

    @Formula("(select count(*) from member_team mt " +
            "where = mt.team_id = id)")
    private transient int countMember;

    public static class AlreadyVotedException extends BusinessException {
        public AlreadyVotedException() {
            super(HttpStatus.CONFLICT, "이미 투표를 진행하였습니다.");
        }
    }

    public static class NeverVotedException extends BusinessException {
        public NeverVotedException() {
            super(HttpStatus.NOT_FOUND, "투표를 진행하지 않았습니다.");
        }
    }
}
