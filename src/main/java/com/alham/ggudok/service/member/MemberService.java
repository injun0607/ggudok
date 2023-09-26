package com.alham.ggudok.service.member;


import com.alham.ggudok.dto.member.MemberLoginDto;
import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.dto.member.MemberUpdateDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.*;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.exception.member.MemberException;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.member.ReviewRepository;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.util.GgudokUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final TagService tagService;

    private final PasswordEncoder passwordEncoder;

    private final ReviewRepository reviewRepository;





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
                throw new MemberException("비밀번호가 올바르지 않습니다");
            }
        }else{
            throw new MemberException("아이디가 올바르지 않습니다");
        }

    }

    /**
     * 멤버 정보 업테이트
     *
     */
    @Transactional
    public boolean updateMember(MemberUpdateDto updateDto,Member member) {
        if (StringUtils.hasText(updateDto.getNewPassword())) {
            passwordEncoder.encode(updateDto.getNewPassword());
            member.updateMember(passwordEncoder.encode(updateDto.getNewPassword()), updateDto.getPhoneNumber(),updateDto.getGender(),updateDto.getAge());
        }else{
            member.updateMember(updateDto.getPhoneNumber(),updateDto.getGender(),updateDto.getAge());
        }
        return true;
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
    public Integer removeMemberFavorSubs(String loginId, Subs subs) {
        Member member = memberRepository.findByLoginIdWithFavorSubs(loginId).get();
        List<MemberFavorSubs> memberFavorSubsList = member.getMemberFavorSubsList();
        memberFavorSubsList.stream().filter(mfs->mfs.getSubs().getSubsId().equals(subs.getSubsId()))
                        .findFirst().ifPresent(mfs->memberFavorSubsList.remove(mfs));
        int i = member.updateFavorSubs(memberFavorSubsList);
        return i;

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
            throw new MemberException("이메일 형식이 올바르지 않습니다.");
        }

        //로그인 아이디 중복검사
        if(memberRepository.findByLoginId(registerDto.getLoginId()).isPresent()) {
            throw new MemberException("이미 회원가입 된 아이디입니다.");
        }

        //비밀번호 확인 체크
        if(!registerDto.getPassword().equals(registerDto.getPasswordCheck())){
            throw new MemberException("비밀번호가 다릅니다.");
        }

        return true;
    }


    public String checkEmail(String checkEmail) {
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

    public Member viewMemberInfo(String loginId) {
        memberRepository.findByLoginId(loginId);

        return null;
    }

    /**
     * 멤버 구독서비스 좋아요
     */
    @Transactional
    public void likeSubs(Member member, Subs subs) {
        createMemberFavorSubs(member, subs);
    }

    public Member findByLoginIdWithTags(String loginId) {
        Optional<Member> memberWithTag = memberRepository.findByLoginIdWithTags(loginId);
        if (memberWithTag.isPresent()) {
            return memberWithTag.get();
        } else {
            return Member.noMember();
        }

    }


    public Member findByLoginIdWithFavorSubs(String loginId) {
        Optional<Member> member = memberRepository.findByLoginIdWithFavorSubs(loginId);
        if(member.isPresent()){
            return member.get();
        }
        else {
            return new Member("no-member", 0);
        }
    }

    public Member findByLoginId(String loginId) {
        Optional<Member> member = memberRepository.findByLoginId(loginId);
        if (member.isPresent()) {
            return member.get();
        } else {
            return Member.noMember();
        }
    }

    public Member findMemberWithReviewsByloginId(String loginId) {
        Optional<Member> member = memberRepository.findMemberWithReviewsByloginId(loginId);
        if (member.isPresent()) {
            return member.get();
        }else{
            return Member.noMember();
        }
    }

    @Transactional
    public boolean removeReview(String loginId, Long subsId) {
        Optional<Member> optionalMember = memberRepository.findMemberWithReviewsByloginId(loginId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            List<Review> reviews = member.getReviews();
            reviews.stream().filter(r -> r.getSubs().getSubsId() == subsId).findAny().ifPresent(r -> reviews.remove(r));
            member.updateReviews(reviews);
            return true;
        } else {
            return false;
        }
    }
}
