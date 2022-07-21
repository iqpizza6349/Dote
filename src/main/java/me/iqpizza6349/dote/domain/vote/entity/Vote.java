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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vote", orphanRemoval = true)
    private List<Team> teams = new ArrayList<>();

    public static Vote createVote(String title, LocalDateTime expiryDate) {
        return Vote.builder()
                .title(title)
                .expiryDate((expiryDate == null)
                        ? LocalDateTime.of(2038, 1, 19, 12, 14, 8)
                        : expiryDate)
                .build();
    }

    public void addTeam(List<Team> teams) {
        this.teams.addAll(teams);
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
