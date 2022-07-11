package me.iqpizza6349.dote.domain.member.repository;

import me.iqpizza6349.dote.domain.member.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {

}
