package me.iqpizza6349.dote.jpa;

import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.member.repository.MemberRepository;
import me.iqpizza6349.dote.domain.member.type.Role;
import me.iqpizza6349.dote.domain.team.entity.Team;
import me.iqpizza6349.dote.domain.team.repository.TeamRepository;
import me.iqpizza6349.dote.domain.vote.entity.Vote;
import me.iqpizza6349.dote.domain.vote.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class JPATest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void memberInsert() {
        Member member = memberRepository.save(new Member(null, 2, 2, 17, Role.ADMIN));
        assertThat(member.getGrade()).isEqualTo(2);
        assertThat(member.getRoom()).isEqualTo(2);
        assertThat(member.getNumber()).isEqualTo(17);
        assertThat(member.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    void voteInsert() {
        // 중복제거가 필요할까..?
        // 로직상 중복 투표 자체를 하지 못하다록 할 것이다.
        // select v from Vote v where member_id = ? and team_id = ?
        // 해서 존재한다면 이미 투표한 것
        Member member = memberRepository.save(new Member(null, 2, 2, 17, Role.ADMIN));
        Vote vote = voteRepository.save(Vote.createVote("happy", new HashSet<>(), null));
        Set<Team> teams = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            teams.add(new Team(null, "happy", null));
        }
        Iterable<Team> teamIterable = teamRepository.saveAll(teams);
        for (Team team : teamIterable) {
            vote.addTeam(team);
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by("teams").descending());
        Page<Vote> votes = voteRepository.findAll(pageable);
        for (Vote v : votes) {
            System.out.println(v);
        }

        assertThat(vote.getTeams()).isNotEmpty();
        assertThat(vote.getTeams().size()).isEqualTo(5);
    }
}
