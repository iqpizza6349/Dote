package me.iqpizza6349.dote.domain.vote.controller;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.team.ro.TeamResponseDto;
import me.iqpizza6349.dote.domain.vote.dto.BallotDto;
import me.iqpizza6349.dote.domain.vote.dto.VoteDto;
import me.iqpizza6349.dote.domain.vote.ro.BallotRO;
import me.iqpizza6349.dote.domain.vote.ro.ListRO;
import me.iqpizza6349.dote.domain.vote.ro.TeamRO;
import me.iqpizza6349.dote.domain.vote.ro.VoteRO;
import me.iqpizza6349.dote.domain.vote.service.VoteService;
import me.iqpizza6349.dote.global.annotations.AuthToken;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @AuthToken
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteRO addVote(@RequestAttribute Member member,
                          @RequestBody @Valid VoteDto voteDto) {
        return voteService.createVote(member, voteDto);
    }

    @AuthToken
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote(@RequestAttribute Member member,
                           @RequestParam(name = "vote_id") long voteId) {
        voteService.deleteVote(member, voteId);
    }

    @GetMapping
    public Page<VoteRO> findVotePage(@RequestParam int page) {
        return voteService.findVotePage(page);
    }

    @GetMapping("/{vote-id}")
    public List<TeamResponseDto> findAllTeams(
            @PathVariable(name = "vote-id") long voteId) {
        return voteService.findAllTeams(voteId);
    }

    @AuthToken
    @PostMapping("/{vote-id}/ballot")
    @ResponseStatus(HttpStatus.CREATED)
    public BallotRO addBallot(@RequestAttribute Member member,
                              @PathVariable(name = "vote-id") long voteId,
                              @RequestBody @Valid BallotDto ballotDto) {
        return voteService.addVote(member, voteId, ballotDto);
    }

    @GetMapping("/{vote-id}/ballot")
    public ListRO<TeamRO> findStatusInquiry(
            @PathVariable(name = "vote-id") long voteId) {
        return voteService.findStatusInquiry(voteId);
    }
}
