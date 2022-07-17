package me.iqpizza6349.dote.domain.vote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.member.type.Role;
import me.iqpizza6349.dote.domain.team.dto.TeamDto;
import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.domain.team.ro.TeamResponseDto;
import me.iqpizza6349.dote.domain.team.service.TeamService;
import me.iqpizza6349.dote.domain.vote.dto.BallotDto;
import me.iqpizza6349.dote.domain.vote.dto.VoteDto;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import me.iqpizza6349.dote.domain.vote.repository.VoteRepository;
import me.iqpizza6349.dote.domain.vote.ro.BallotRO;
import me.iqpizza6349.dote.domain.vote.ro.TeamRO;
import me.iqpizza6349.dote.domain.vote.ro.VoteRO;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final TeamService teamService;

    public VoteRO createVote(Member member, VoteDto voteDto) {
        if (isNotAdmin(member.getRole())) {
            throw new Member.ForbiddenException();
        }

        Set<Team> teamSet = voteDto.getItems()
                .stream()
                .map(TeamDto::getName)
                .map(Team::new)
                .collect(Collectors.toSet());
        log.info("item size: {}", teamSet.size());
        Vote vote = Vote.createVote(voteDto.getTitle(), teamSet, voteDto.getEndTime());
        teamService.saveAll(teamSet);
        return new VoteRO(voteRepository.save(vote));
    }
    
    public void deleteVote(Member member, long voteId) {
        if (isNotAdmin(member.getRole())) {
            throw new Member.ForbiddenException();
        }

        Vote vote = findById(voteId);
        teamService.deleteByVoteId(vote);
        voteRepository.delete(vote);
    }

    private boolean isNotAdmin(Role role) {
        return role != Role.ADMIN;
    }

    @Transactional(readOnly = true)
    public Page<VoteRO> findVotePage(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("expiryDate").descending());
        Page<Vote> votePage = voteRepository.findAll(pageable);
        return new PageImpl<>(votePage.stream()
                .map(VoteRO::new)
                .collect(Collectors.toList()));
    }

    public BallotRO addVote(Member member, long voteId, BallotDto ballotDto) {
        Vote vote = findById(voteId);
        if (vote.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new Vote.AlreadyClosedException();
        }

        teamService.ballot(member, vote, ballotDto.getTeamId());
        return new BallotRO(ballotDto.getTeamId());
    }

    public Page<TeamRO> findStatusInquiry(long voteId, int page) {
        Vote vote = findById(voteId);
        // vote 의 teams 의 현황 조회
        return new PageImpl<>(teamService.findAll(vote, page).stream()
                .collect(Collectors.toList()));
    }

    public Page<TeamResponseDto> findAllTeams(long voteId) {
        Vote vote = findById(voteId);
        return new PageImpl<>(teamService.findAllTeams(vote)
                .stream()
                .map(TeamResponseDto::new)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    protected Vote findById(long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(Vote.NotExistException::new);
    }
}
