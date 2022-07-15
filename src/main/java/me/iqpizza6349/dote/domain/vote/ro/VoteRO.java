package me.iqpizza6349.dote.domain.vote.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.iqpizza6349.dote.domain.vote.entity.Vote;

import java.time.LocalDateTime;

@Getter
public class VoteRO {

    private final long id;
    private final String title;

    @JsonProperty("end_time")
    private final LocalDateTime endTime;

    public VoteRO(Vote vote) {
        this.id = vote.getId();
        this.title = vote.getTitle();
        this.endTime = vote.getExpiryDate();
    }

}
