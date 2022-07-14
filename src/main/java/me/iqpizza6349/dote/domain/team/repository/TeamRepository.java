package me.iqpizza6349.dote.domain.team.repository;

import me.iqpizza6349.dote.domain.team.entity.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    @Query("select t from Team t where t.vote.teams = ?1")
    List<Team> findAllByVoteTeams(Set<Team> vote_teams);

}
