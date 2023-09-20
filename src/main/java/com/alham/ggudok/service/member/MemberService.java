package com.alham.ggudok.service.member;


import com.alham.ggudok.controller.subs.member.MemberLoginDto;
import com.alham.ggudok.controller.subs.member.MemberRegisterDto;
import com.alham.ggudok.controller.subs.member.MemberUpdateDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.MemberFavorSubs;
import com.alham.ggudok.entity.member.MemberHaveSubs;
import com.alham.ggudok.entity.member.MemberRelTag;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.error.member.ErrorMember;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.subs.CategoryRepository;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.util.GgudokUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final TagService tagService;

    private final PasswordEncoder passwordEncoder;





    /**
     * 멤버 등록
     * @param registerDto
     * @return
     */
    @Transactional
    public boolean registerMember(MemberRegisterDto registerDto) {

        if(!validMember(registerDto)){
            return false;
        }

        Member member = new Member(registerDto.getMemberName(),
                registerDto.getAge(),
                registerDto.getLoginId(),
                passwordEncoder.encode(registerDto.getPassword()),
                registerDto.getGender(),
                registerDto.getPhoneNumber());
        Member savedMember = memberRepository.save(member);

//      회원 태그 분류 작업
//      나이 성별
        Tag ageTag = tagService.checkAgeTag(savedMember.getAge());
        Tag genderTag = tagService.checkGender(savedMember.getGender());
        createRelTag(savedMember, ageTag);
        createRelTag(savedMember, genderTag);

        return true;
    }





    /**
     * 멤버 로그인
     * @param
     * @return
     */
    public Member memberLoginCheck(MemberLoginDto loginDto) {


        if (memberRepository.findByLoginId(loginDto.getLoginId()).isPresent()) {

            Member member = memberRepository.findByLoginId(loginDto.getLoginId()).get();
            if(passwordEncoder.matches(loginDto.getPassword(),member.getPassword())){
                return member;
            }else{
                new ErrorMember("비밀번호가 올바르지 않습니다");
                return null;
            }
        }else{
            new ErrorMember("아이디가 올바르지 않습니다");
            return null;
        }

    }

    @Transactional
    public void updateMember(MemberUpdateDto updateDto) {
        Optional<Member> optionalMember = memberRepository.findById(updateDto.getMemberId());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.updateMember(updateDto.getPassword(), updateDto.getPhoneNumber());
        }

    }


    @Transactional
    public MemberHaveSubs createMemberHaveSubs(Member member, Subs subs) {

        return MemberHaveSubs.createHaveSubs(member, subs);
    }

    @Transactional
    public MemberFavorSubs createMemberFavorSubs(Member member, Subs subs) {
        return MemberFavorSubs.createFavorSubs(member, subs);
    }
    @Transactional
    public MemberRelTag createRelTag(Member member, Tag tag) {
        return MemberRelTag.createRelTag(member, tag);
    }




    /**
     * 회원가입시 유효성 검사 메서드
     * 유효성 검사시 항목 추가 될수있음
     */
    private boolean validMember(MemberRegisterDto registerDto) {
        //올바른 이메일인지 검사
        if (!GgudokUtil.isValidEmail(registerDto.getLoginId())) {
            return false;
        }

        //로그인 아이디 중복검사
        if(memberRepository.findByLoginId(registerDto.getLoginId()).isPresent()) {
            return false;
        }

        //비밀번호 확인 체크
        if(!registerDto.getPassword().equals(registerDto.getPasswordCheck())){
            return false;
        }

        return true;
    }


    public String checkEmail(String checkEmail) {
        String checkString = "";
        if (!GgudokUtil.isValidEmail(checkEmail)) {
            return "fail";
        }
        String certCode = GgudokUtil.certEmail(checkEmail);
        if (certCode.equals(GgudokUtil.EMAIL_FAIL)) {
            return "fail";
        } else {
            return certCode;
        }
    }



}
