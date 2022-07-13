package me.iqpizza6349.dote.domain.vote.service;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.team.dto.TeamDto;
import me.iqpizza6349.dote.domain.team.entity.MemberTeam;
import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.domain.team.service.TeamService;
import me.iqpizza6349.dote.domain.vote.dto.BallotDto;
import me.iqpizza6349.dote.domain.vote.dto.VoteDto;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import me.iqpizza6349.dote.domain.vote.repository.VoteRepository;
import me.iqpizza6349.dote.domain.vote.ro.BallotRO;
import me.iqpizza6349.dote.domain.vote.ro.VoteRO;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.NESTED)
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final TeamService teamService;

    public VoteRO createVote(VoteDto voteDto) {
        Set<Team> teamSet = voteDto.getItems()
                .stream()
                .map(TeamDto::getName)
                .map(Team::new)
                .collect(Collectors.toSet());

        Vote vote = Vote.createVote(voteDto.getTitle(), teamSet, voteDto.getEndTime());
        return new VoteRO(voteRepository.save(vote));
    }
    
    public void deleteVote(long voteId) {
        voteRepository.delete(findById(voteId));
    }

    @Transactional(readOnly = true)
    public Page<VoteRO> findVotePage(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("expiryDate").descending());
        Page<Vote> votePage = voteRepository.findAll(pageable);
        return new PageImpl<>(votePage.stream()
                .map(VoteRO::new)
                .collect(Collectors.toList()));
    }

    public BallotRO addVote(Member member, BallotDto ballotDto) {
        Vote vote = findById(ballotDto.getVoteId());
        teamService.ballot(member, vote, ballotDto.getTeamId());
        return new BallotRO(ballotDto.getTeamId());
    }

    // TODO 현황 조회

    @Transactional(readOnly = true)
    protected Vote findById(long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(Vote.NotExistException::new);
    }
}
