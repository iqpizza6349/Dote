package me.iqpizza6349.dote.domain.vote.repository;

import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
