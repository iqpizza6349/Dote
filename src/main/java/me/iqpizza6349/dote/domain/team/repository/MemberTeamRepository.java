package me.iqpizza6349.dote.domain.team.repository;

import me.iqpizza6349.dote.domain.team.entity.MemberTeam;
import me.iqpizza6349.dote.domain.team.entity.embed.MemberTeamId;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import me.iqpizza6349.dote.domain.vote.ro.TeamRO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MemberTeamRepository
        extends JpaRepository<MemberTeam, MemberTeamId> {

    @Query("select distinct " +
            "new me.iqpizza6349.dote.domain.vote.ro.TeamRO(m.team.name, m.countMember) " +
            "from MemberTeam m where m.team.vote = ?1")
    Page<TeamRO> findDistinctByTeamVote(Vote teamVote, Pageable pageable);

    @Query("select m from MemberTeam m where m.member.id = ?1")
    Set<MemberTeam> findAllByMemberId(int memberId);

}
