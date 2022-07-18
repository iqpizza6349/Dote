package me.iqpizza6349.dote.domain.vote.repository;

import me.iqpizza6349.dote.domain.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByExpiryDateBefore(LocalDateTime expiryDate);

}
