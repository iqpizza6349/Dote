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
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {
    
    private final MemberTeamRepository memberTeamRepository;
    private final TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
    protected List<TeamRO> findAllByTeamVote(Vote vote, Sort sort) {
        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        Query query = entityManager.createNativeQuery(
                "select distinct team1_.name as col_0_0_,\n" +
                        "                ifnull((select count(*) from member_team mt where mt.team_id = memberteam0_.team_id), 0) as col_1_0_\n" +
                        "from member_team memberteam0_\n" +
                        "    right join team team1_ on memberteam0_.team_id = team1_.id\n" +
                        "    where team1_.vote_id = :vote_id\n" +
                        "order by (select count(*) from member_team mt where mt.team_id = memberteam0_.team_id)\n" +
                        "desc limit 10"
        );
        query.setParameter("vote_id", vote.getId());
        return jpaResultMapper.list(query, TeamRO.class);
    }

    private boolean isExistedIn(Set<Team> teams, Team team) {
        return teams.contains(team);
    }

    private boolean hasVoted(Set<MemberTeam> memberVoted, Vote vote) {
        // ????????? ????????? ?????? ?????? ???
        for (MemberTeam memberTeam : memberVoted) {
            if (memberTeam.getTeam() != null) {
                Team team = memberTeam.getTeam();
                if (team.getVote().equals(vote)) {
                    // ?????? id ??? ????????? ????????? ????????? ??? ?????? ?????????
                    return true;
                }
            }
        }

        // ????????? ?????????????????? ?????? ????????? ????????? ????????? ?????? ?????????
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAll(Set<Team> teams) {
        teamRepository.saveAll(teams);
    }

    public void ballot(Member member, Vote vote, long teamId) {
        Team team = findById(teamId);
        if (!isExistedIn(vote.getTeams(), team)) {
            // ?????? ????????? ?????????????????? ?????? ?????? ??????
            throw new Team.NotExistedException();
        }

        Set<MemberTeam> memberVotedTeams = findAllByMemberId(member.getId());
        MemberTeamId memberTeamId = new MemberTeamId(member.getId(), teamId);
        if (hasVoted(memberTeamId) || hasVoted(memberVotedTeams, vote)) {
            // ?????? ????????? ????????? ??? ???????????????
            // ?????? ????????? ????????? ?????? ????????? ??????
            // ?????? ??????
            throw new MemberTeam.AlreadyVotedException();
        }

        memberTeamRepository.save(new MemberTeam(team, member));
    }
    
    public List<TeamRO> findAll(Vote vote) {
        // ?????? ??????
        // ?????? ????????? ?????? ???????????? ??????
        return findAllByTeamVote(vote, Sort.by("countMember").descending());
    }

    public List<Team> findAllTeams(Vote vote) {
        return findAllByTeamId(vote);
    }
    
    public void deleteByVoteId(Vote vote) {
        if (vote.getTeams() == null || hasNull(vote.getTeams())) {
            return;
        }

        memberTeamRepository.deleteByTeamIn(vote.getTeams());
    }

    private boolean hasNull(Set<Team> teams) {
        for (Team team : teams) {
            if (team == null) {
                return true;
            }
        }

        return false;
    }
}
