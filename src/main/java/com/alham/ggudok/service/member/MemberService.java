package com.alham.ggudok.service.member;


import com.alham.ggudok.dto.member.MemberLoginDto;
import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.dto.member.MemberUpdateDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.*;
import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.exception.member.MemberException;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.member.ReviewRepository;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.util.GgudokUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final TagService tagService;

    private final PasswordEncoder passwordEncoder;

    private final EntityManager em;

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

        MemberRelTag.createRelTag(savedMember, ageTag);
        MemberRelTag.createRelTag(savedMember, genderTag);

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
    public boolean createMemberHaveSubs(Member member, Subs subs, RankLevel rankLevel) {
        List<MemberHaveSubs> memberHaveSubsList = member.getMemberHaveSubsList();
        Optional<MemberHaveSubs> optionalMemberHaveSubs =
                memberHaveSubsList.stream().filter(mfs -> mfs.getSubs().getSubsId() == subs.getSubsId()).findFirst();

        if (optionalMemberHaveSubs.isPresent()) {
            MemberHaveSubs memberHaveSubs = optionalMemberHaveSubs.get();
            if (memberHaveSubs.getRankLevel().equals(rankLevel)) {
                return false;
            } else {
                memberHaveSubsList.remove(memberHaveSubs);
                memberHaveSubs.updateRankLevel(rankLevel);
                memberHaveSubsList.add(memberHaveSubs);
                member.updateHaveSubs(memberHaveSubsList);
                return true;
            }

        } else {
            MemberHaveSubs.createHaveSubs(member, subs, rankLevel);
            return true;
        }
    }

    @Transactional
    public Integer removeMemberHaveSubs(String loginId, Subs subs) {
        Member member = memberRepository.findByLoginIdWithHaveSubs(loginId).get();
        List<MemberHaveSubs> memberHaveSubsList = member.getMemberHaveSubsList();
        memberHaveSubsList.stream().filter(mhs->mhs.getSubs().getSubsId().equals(subs.getSubsId()))
                .findFirst().ifPresent(mhs->memberHaveSubsList.remove(mhs));
        int i = member.updateHaveSubs(memberHaveSubsList);
        return i;

    }



    @Transactional
    public boolean createMemberFavorSubs(Member member, Subs subs) {
        if (member.getMemberFavorSubsList().stream().filter(mfs -> mfs.getSubs().getSubsId() == subs.getSubsId()).findFirst().isPresent()) {
            return false;
        } else{
            MemberFavorSubs.createFavorSubs(member, subs);
            return true;
        }
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
    public boolean createRelTag(Member member, Tag tag) {
        Optional<Member> optionalMember = memberRepository.findByLoginIdWithTags(member.getLoginId());
        if (optionalMember.isPresent()) {
            Member findMember = optionalMember.get();
            if (findMember.getMemberRelTags().stream().filter(mfs -> mfs.getTag().getTagName().equals(tag.getTagName())).findFirst().isPresent()) {
                return false;
            }else {
                MemberRelTag.createRelTag(member, tag);
                return true;
            }
        }else{
            return false;
        }

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

//TODO cert 코드 비활성화 상태
    public String checkEmail(String checkEmail) {
        if (!GgudokUtil.isValidEmail(checkEmail)) {
            return "fail";
        }

//        String certCode = GgudokUtil.certEmail(checkEmail);
        String certCode = "1234";
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

    public Member findByLoginIdWithHaveSubs(String loginId) {
        Optional<Member> member = memberRepository.findByLoginIdWithHaveSubs(loginId);
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

    @Transactional
    public boolean buySubs(String loginId, Subs subs, RankLevel rankLevel) {
        Optional<Member> optionalMember = memberRepository.findByLoginIdWithHaveSubs(loginId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            createMemberHaveSubs(member, subs, rankLevel);
            return true;
        }else{
            throw new MemberException("가입되지 않은 회원입니다!");
        }
    }

    /*
     * 유저의 태그 추천 시스템
     * 구독한 subs, 좋아요한 subs들의 태그들을 수집한뒤
     * 구독한 subs들의 태그들은 개당 2점
     * 좋아요한 subs들의 태그들은 개당 1점을 부여
     * 태그들의 전체 나온 횟수로 점수부여
     */
    @Transactional
    public void userRecommendTag() {

        List<Member> all = memberRepository.findAll();
        //HaveSubs
        List<Member> allWithHaveSubs = memberRepository.findAllWithHaveSubs();
        HashMap<Long, Map<Tag,Integer>> result = new HashMap<>();



        for (Member memberHaveSub : allWithHaveSubs) {
            //회원들을 subsList를 순회하면서
            //해당 subs에 있는 태그들을 순서를 매긴다
            List<Subs> memberSubsList = memberHaveSub.getMemberHaveSubsList().stream().map(srt -> srt.getSubs()).collect(Collectors.toList());

            Map<Tag, Integer> tagScoreMap = new HashMap<>();
            sumTagScore(tagScoreMap,memberSubsList,2);
            result.put(memberHaveSub.getMemberId(), tagScoreMap);
        }

        //FavorSubs
        List<Member> allWithFavorSubs = memberRepository.findAllWithFavorSubs();

        for (Member memberFavorSubs : allWithFavorSubs) {

            List<Subs> memberSubsList = memberFavorSubs.getMemberHaveSubsList().stream().map(srt -> srt.getSubs()).collect(Collectors.toList());
            Map<Tag, Integer> tagScoreMap = result.get(memberFavorSubs.getMemberId());
            sumTagScore(tagScoreMap,memberSubsList,1);

        }


        //없는 좋아요와 구독한 서비스가 없는 회원들은 tag sort를 설정하지않는다.
        List<Member> allWithTag = memberRepository.findAllWithTag();

        for (Member memberWithTag : allWithTag) {
            if (result.containsKey(memberWithTag.getMemberId())) {
                Map<Tag, Integer> tagScoreMap = result.get(memberWithTag.getMemberId());

                Map<Tag, Integer> sortedMap = GgudokUtil.mapSortByValueDescending(tagScoreMap);

                HashMap<Tag, Integer> tagRankMap = new HashMap<>();
                Set<Tag> tags = sortedMap.keySet();

                int sort = 0;
                for (Tag tagKey : tags) {
                    tagRankMap.put(tagKey, ++sort);
                }

                memberWithTag.
                        getMemberRelTags()
                        .stream()
                        .forEach(mrt->mrt.updateTagSort(tagRankMap.get(mrt.getTag())));
            }

        }

    }

    /**
     * subsList에 속한 subs들을 순회하면서 tag의 score를 구하는 메서드
     * @param tagScoreMap
     * @param subsList
     * @param score
     */
    public void sumTagScore(Map<Tag, Integer> tagScoreMap, List<Subs> subsList, int score) {
        for (Subs subs : subsList) {
            List<Tag> tagsBySubsId = tagService.findTagsBySubsId(subs.getSubsId());
            //subs내의 태그들을 순회하면서
            //tagScoreMap에 태그들의 점수를 기록한다.
            for (Tag tag : tagsBySubsId) {
                if (tagScoreMap.containsKey(tag)) {
                    tagScoreMap.put(tag, tagScoreMap.get(tag) + score );
                }else{
                    tagScoreMap.put(tag, score);
                }
            }

        }
    }
}
