package com.alham.ggudok.service.member;

import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.Role;
import com.alham.ggudok.entity.security.MemberSecurity;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.security.MemberSecurityRepository;
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

    private final MemberSecurityRepository memberSecurityRepository;

    @Transactional
    public Member findMemberOAuth(String loginId) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member;

        } else {
            Member member = new Member(loginId, loginId);
            member.updateRole(Role.WEB);
            MemberSecurity memberSecurity = new MemberSecurity();
            memberSecurity.updateLoginId(loginId);
            memberSecurityRepository.saveAndFlush(memberSecurity);
            return memberRepository.save(member);

        }
    }
}
