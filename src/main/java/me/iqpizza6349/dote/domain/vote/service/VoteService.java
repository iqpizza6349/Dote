package me.iqpizza6349.dote.domain.vote.service;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.team.dto.TeamDto;
import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.domain.vote.dto.VoteDto;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import me.iqpizza6349.dote.domain.vote.repository.VoteRepository;
import me.iqpizza6349.dote.domain.vote.ro.VoteRO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteRO createVote(VoteDto voteDto) {
        Set<Team> teamSet = voteDto.getItems()
                .stream()
                .map(TeamDto::getName)
                .map(Team::new)
                .collect(Collectors.toSet());

        Vote vote = Vote.createVote(voteDto.getTitle(), teamSet, voteDto.getEndTime());
        return new VoteRO(voteRepository.save(vote));
    }
}
