package me.iqpizza6349.dote.domain.team.repository;

import me.iqpizza6349.dote.domain.team.entity.MemberTeam;
import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.domain.team.entity.embed.MemberTeamId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MemberTeamRepository
        extends JpaRepository<MemberTeam, MemberTeamId> {

    @Query("select m from MemberTeam m where m.team = ?1")
    Page<MemberTeam> findAllByTeam(Team team, Pageable pageable);

    @Query("select m from MemberTeam m where m.member.id = ?1")
    Set<MemberTeam> findAllByMemberId(int memberId);

}
