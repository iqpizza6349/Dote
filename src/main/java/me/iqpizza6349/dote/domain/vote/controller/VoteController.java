package me.iqpizza6349.dote.domain.vote.controller;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.vote.dto.BallotDto;
import me.iqpizza6349.dote.domain.vote.dto.VoteDto;
import me.iqpizza6349.dote.domain.vote.ro.BallotRO;
import me.iqpizza6349.dote.domain.vote.ro.VoteRO;
import me.iqpizza6349.dote.domain.vote.service.VoteService;
import me.iqpizza6349.dote.global.annotations.AuthToken;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @AuthToken
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteRO addVote(@RequestBody @Valid VoteDto voteDto) {
        return voteService.createVote(voteDto);
    }

    @AuthToken
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote(@RequestParam(name = "vote_id") long voteId) {
        voteService.deleteVote(voteId);
    }

    @AuthToken
    @GetMapping
    public Page<VoteRO> findVotePage(@RequestParam int page) {
        return voteService.findVotePage(page);
    }

    @AuthToken
    @PostMapping("/ballot")
    @ResponseStatus(HttpStatus.CREATED)
    public BallotRO addBallot(@RequestAttribute Member member,
                              @RequestBody @Valid BallotDto ballotDto) {
        return voteService.addVote(member, ballotDto);
    }

    // TODO 현황 조회

}
