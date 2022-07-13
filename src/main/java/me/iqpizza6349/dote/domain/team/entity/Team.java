package me.iqpizza6349.dote.domain.team.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import me.iqpizza6349.dote.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Vote vote;

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public Team(String name) {
        this(null, name, null);
    }

    public static class NotExistedException extends BusinessException {
        public NotExistedException() {
            super(HttpStatus.NOT_FOUND, "존재하지 않는 항목입니다.");
        }
    }


}
