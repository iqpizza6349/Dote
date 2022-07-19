package me.iqpizza6349.dote.global.schedule;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.vote.service.VoteService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ExpiredVoteScheduler {

    private final VoteService voteService;

    @Scheduled(fixedDelay = 60 * 1000)
    public void removeExpiredVoteHandler() {
        voteService.deleteWithHandler(LocalDateTime.now().minusMinutes(30));
    }
}
