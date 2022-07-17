package me.iqpizza6349.dote.domain.team.repository;

import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByVote(Vote vote);

    @Transactional
    @Modifying
    @Query("delete from Team t where t.vote = ?1")
    void deleteByVote(Vote vote);

}
