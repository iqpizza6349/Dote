package me.iqpizza6349.dote.domain.team.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.team.entity.embed.MemberTeamId;
import me.iqpizza6349.dote.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Getter
@AllArgsConstructor @NoArgsConstructor
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

    public static class AlreadyVotedException extends BusinessException {
        public AlreadyVotedException() {
            super(HttpStatus.CONFLICT, "이미 투표를 진행하였습니다.");
        }
    }
}
