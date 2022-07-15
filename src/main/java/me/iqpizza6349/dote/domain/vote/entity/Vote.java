package me.iqpizza6349.dote.domain.vote.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.global.exception.BusinessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

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
    @OneToMany(mappedBy = "vote", cascade = {CascadeType.ALL})
    private Set<Team> teams = new HashSet<>();

    public void addTeam(Team team) {
        team.setVote(this);
        teams.add(team);
    }

    public static Vote createVote(String title, Set<Team> teams,  LocalDateTime expiryDate) {
        Vote vote = Vote.builder()
                .title(title)
                .expiryDate((expiryDate == null)
                        ? LocalDateTime.of(2038, 1, 19, 12, 14, 8)
                        : expiryDate)
                .build();
        for (Team team : teams) {
            vote.addTeam(team);
        }

        return vote;
    }

    public static class NotExistException extends BusinessException {
        public NotExistException() {
            super(HttpStatus.NOT_FOUND, "존재하지 않는 투표입니다.");
        }
    }
    
    public static class AlreadyClosedException extends BusinessException {
        public AlreadyClosedException() {
            super(HttpStatus.CONFLICT, "이미 마감된 투표입니다.");
        }
    }
}
