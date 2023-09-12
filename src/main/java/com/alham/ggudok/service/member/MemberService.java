package com.alham.ggudok.service.member;


import com.alham.ggudok.dto.member.MemberLoginDto;
import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.MemberFavorSubs;
import com.alham.ggudok.entity.member.MemberHaveSubs;
import com.alham.ggudok.entity.member.MemberRelTag;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.error.member.ErrorMember;
import com.alham.ggudok.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 멤버 등록
     * @param registerDto
     * @return
     */
    @Transactional
    public boolean registerMember(MemberRegisterDto registerDto) {

        Member member = new Member(registerDto.getMemberName(),
                registerDto.getAge(),
                registerDto.getLoginId(),
                registerDto.getPassword(),
                registerDto.getGender(),
                registerDto.getPhoneNumber());

        if(!validMember(registerDto)){
            return false;
        }

        memberRepository.save(member);
        return true;
    }

    /**
     * 멤버 로그인
     * @param loginDto
     * @return
     */
    public boolean memberLoginCheck(MemberLoginDto loginDto) {

        if (memberRepository.findByLoginId(loginDto.getLoginId()).isPresent()) {

            Member member = memberRepository.findByLoginId(loginDto.getLoginId()).get();
            if(member.getPassword().equals(loginDto.getPassword())){
                return true;
            }else{
                new ErrorMember("비밀번호가 올바르지 않습니다");
                return false;
            }
        }else{
            new ErrorMember("아이디가 올바르지 않습니다");
            return false;
        }

    }


    public MemberHaveSubs createMemberHaveSubs(Member member, Subs subs) {

        return MemberHaveSubs.createHaveSubs(member, subs);
    }

    public MemberFavorSubs createMemberFavorSubs(Member member, Subs subs) {
        return MemberFavorSubs.createFavorSubs(member, subs);
    }

    public MemberRelTag createRelTag(Member member, Tag tag) {
        return MemberRelTag.createRelTag(member, tag);
    }




    /**
     * 회원가입시 유효성 검사 메서드
     * 유효성 검사시 항목 추가 될수있음
     */
    private boolean validMember(MemberRegisterDto registerDto) {

        //로그인 아이디 중복검사
        if(memberRepository.findByLoginId(registerDto.getLoginId()).isPresent()) {
            return false;
        }
        return true;
    }



}
