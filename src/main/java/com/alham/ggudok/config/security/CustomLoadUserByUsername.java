package com.alham.ggudok.config.security;

import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomLoadUserByUsername implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> loginMember = memberRepository.findByLoginId(loginId);
        if (loginMember.isPresent()) {
            Member member = loginMember.get();
            MemberDto memberDto = new MemberDto();
            memberDto.setMemberName(member.getMemberName());
            memberDto.setLoginId(member.getLoginId());
            memberDto.setPassword(member.getPassword());

            return memberDto;
        }else{
            throw new UsernameNotFoundException("Not Found User");
        }
    }
}
