package com.alham.ggudok.service.member;

import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberOAuth2Service {

    private final MemberRepository memberRepository;

    @Transactional
    public Member updateMemberOAuth(String loginId, String memberName) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.updateMember(memberName);

            return member;
        } else {
            Member member = new Member(loginId, memberName);
            return memberRepository.save(member);

        }
    }
}
