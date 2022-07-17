package me.iqpizza6349.dote.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.team.entity.MemberTeam;
import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.domain.team.entity.embed.MemberTeamId;
import me.iqpizza6349.dote.domain.team.repository.MemberTeamRepository;
import me.iqpizza6349.dote.domain.team.repository.TeamRepository;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import me.iqpizza6349.dote.domain.vote.ro.TeamRO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
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
    protected boolean hasVoted(MemberTeamId memberTeamId) {
        return memberTeamRepository.existsById(memberTeamId);
    }

    @Transactional(readOnly = true)
    protected List<Team> findAllByTeamId(Vote vote) {
        return teamRepository.findAllByVote(vote);
    }
    
    @Transactional(readOnly = true)
    protected Set<MemberTeam> findAllByMemberId(int memberId) {
        return memberTeamRepository.findAllByMemberId(memberId);
    }
    
    @Transactional(readOnly = true)
    protected Page<TeamRO> findAllByTeamVote(Vote vote, Pageable pageable) {
        return memberTeamRepository.findDistinctByTeamVote(vote, pageable);
    }

    private boolean isExistedIn(Set<Team> teams, Team team) {
        return teams.contains(team);
    }

    private boolean hasVoted(Set<MemberTeam> memberVoted, Vote vote) {
        // 회원이 투표한 모든 기록 중
        for (MemberTeam memberTeam : memberVoted) {
            if (memberTeam.getTeam() != null) {
                Team team = memberTeam.getTeam();
                if (team.getVote().equals(vote)) {
                    // 투표 id 와 동일한 투표에 투표를 한 적이 있다면
                    return true;
                }
            }
        }

        // 투표를 진행하더라도 같은 투표에 투표를 진행한 적이 없다면
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAll(Set<Team> teams) {
        teamRepository.saveAll(teams);
    }

    public void ballot(Member member, Vote vote, long teamId) {
        Team team = findById(teamId);
        if (!isExistedIn(vote.getTeams(), team)) {
            // 투표 항목에 존재하지않는 다면 예외 발생
            throw new Team.NotExistedException();
        }

        Set<MemberTeam> memberVotedTeams = findAllByMemberId(member.getId());
        MemberTeamId memberTeamId = new MemberTeamId(member.getId(), teamId);
        if (hasVoted(memberTeamId) || hasVoted(memberVotedTeams, vote)) {
            // 같은 항목에 투표를 또 진행하거나
            // 다른 항목에 투표를 다시 진행할 경우
            // 예외 발생
            throw new MemberTeam.AlreadyVotedException();
        }

        memberTeamRepository.save(new MemberTeam(team, member));
    }

    public Page<TeamRO> findAll(Vote vote, int page) {
        // 현황 조회
        // 해당 투표에 있는 항목들만 조회
        Pageable pageable = PageRequest.of(page, 10, Sort.by("countMember").descending());
        return findAllByTeamVote(vote, pageable);
    }

    public List<Team> findAllTeams(Vote vote) {
        return findAllByTeamId(vote);
    }
}
