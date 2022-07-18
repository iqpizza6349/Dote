package me.iqpizza6349.dote.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.dote.domain.dauth.dto.*;
import me.iqpizza6349.dote.domain.member.entity.Member;
import me.iqpizza6349.dote.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(DOpenApiDto dOpenApiDto) {
        DOpenApiDto.DodamInfoData dodamInfoData = dOpenApiDto.getDodamInfoData();
        Member member = DOpenApiDto.DodamInfoData.toEntity(dodamInfoData);
        if (isExisted(member)) {
            throw new Member.AlreadyExistedException();
        }

        return memberRepository.save(DOpenApiDto.DodamInfoData.toEntity(dodamInfoData));
    }

    @Transactional(readOnly = true)
    protected boolean isExisted(Member member) {
        return memberRepository.existsByGradeAndNumberAndRoom(
                member.getGrade(), member.getNumber(), member.getRoom()
        );
    }

}
