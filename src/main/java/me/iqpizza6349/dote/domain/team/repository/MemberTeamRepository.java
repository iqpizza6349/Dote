package me.iqpizza6349.dote.domain.team.repository;

import me.iqpizza6349.dote.domain.team.entity.MemberTeam;
import me.iqpizza6349.dote.domain.team.entity.embed.MemberTeamId;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MemberTeamRepository
        extends JpaRepository<MemberTeam, MemberTeamId> {

    @Query("select m from MemberTeam m where m.team.vote = ?1")
    List<MemberTeam> findAllByTeamVote(Vote teamVote, Sort sort);

    @Query("select m from MemberTeam m where m.member.id = ?1")
    Set<MemberTeam> findAllByMemberId(int memberId);

}
