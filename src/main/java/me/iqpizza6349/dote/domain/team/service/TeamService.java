package me.iqpizza6349.dote.domain.team.service;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.team.entity.MemberTeam;
import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.domain.team.entity.embed.MemberTeamId;
import me.iqpizza6349.dote.domain.team.repository.MemberTeamRepository;
import me.iqpizza6349.dote.domain.team.repository.TeamRepository;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {
    
    private final MemberTeamRepository memberTeamRepository;
    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    protected Team findById(long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(Team.NotExistedException::new);
    }

    @Transactional(readOnly = true)
    protected boolean isVoted(MemberTeamId memberTeamId) {
        return memberTeamRepository.existsById(memberTeamId);
    }

    private boolean isExistedIn(Set<Team> teams, Team team) {
        return teams.contains(team);
    }

    public void ballot(Member member, Vote vote, long teamId) {
        Team team = findById(teamId);
        if (!isExistedIn(vote.getTeams(), team)) {
            throw new Team.NotExistedException();
        }

        MemberTeamId memberTeamId = new MemberTeamId(member.getId(), teamId);
        if (isVoted(memberTeamId)) {
            // 이미 투표한 이력이 존재하기에 예외 발생
            throw new MemberTeam.AlreadyVotedException();
        }

        memberTeamRepository.save(new MemberTeam(team, member));
    }
}
