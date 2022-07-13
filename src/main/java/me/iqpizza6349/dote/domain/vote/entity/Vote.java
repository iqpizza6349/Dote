package me.iqpizza6349.dote.domain.vote.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.iqpizza6349.dote.domain.team.entity.Team;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

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

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expiryDate;

    @Builder.Default
    @OneToMany(mappedBy = "vote")
    private Set<Team> teams = new HashSet<>();

    public void addTeam(Team team) {
        team.setVote(this);
        teams.add(team);
    }

    public static Vote createVote(String title, Set<Team> teams,  LocalDateTime expiryDate) {
        return Vote.builder()
                .title(title)
                .teams(teams)
                .expiryDate((expiryDate == null)
                        ? LocalDateTime.of(2038, 1, 19, 12, 14, 8)
                        : expiryDate)
                .build();
    }
}
