package me.iqpizza6349.dote.domain.vote.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.team.entity.Team;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
public class Vote {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "vote")
    private Set<Team> teams = new HashSet<>();

    public void addTeam(Team team) {
        team.setVote(this);
        teams.add(team);
    }
}
