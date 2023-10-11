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

        MemberRelTag.createRelTag(savedMember, ageTag,true);
        MemberRelTag.createRelTag(savedMember, genderTag,true);

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
        Optional<Member> optionalMember = Optional.of(member);

        if (member.getMemberRelTags().size() == 0) {
            optionalMember = memberRepository.findByLoginIdWithTags(member.getLoginId());
        }

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
        //subs와 연관된 태그 생성
        subs.getSubsRelTags().stream().forEach(srt->createRelTag(member,srt.getTag()));

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
            //subs와 연관된 태그 생성
            subs.getSubsRelTags().stream().forEach(srt->createRelTag(member,srt.getTag()));
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
        HashMap<Long, Map<Tag, Integer>> resultHaveCntMap = new HashMap<>();
        HashMap<Long, Map<Tag, Integer>> resultFavorCntMap = new HashMap<>();



        for (Member memberHaveSub : allWithHaveSubs) {
            //회원들을 subsList를 순회하면서
            //해당 subs에 있는 태그들을 순서를 매긴다
            List<Subs> memberSubsList = memberHaveSub.getMemberHaveSubsList().stream().map(srt -> srt.getSubs()).collect(Collectors.toList());

            Map<Tag, Integer> tagScoreMap = new HashMap<>();
            Map<Tag, Integer> tagHaveCntMap = new HashMap<>();
            sumTagScore(tagScoreMap,tagHaveCntMap,memberSubsList,2);
            result.put(memberHaveSub.getMemberId(), tagScoreMap);
            resultHaveCntMap.put(memberHaveSub.getMemberId(), tagHaveCntMap);
        }

        //FavorSubs
        List<Member> allWithFavorSubs = memberRepository.findAllWithFavorSubs();

        for (Member memberFavorSubs : allWithFavorSubs) {

            List<Subs> memberSubsList = memberFavorSubs.getMemberFavorSubsList().stream().map(srt -> srt.getSubs()).collect(Collectors.toList());
            Map<Tag, Integer> tagScoreMap = result.get(memberFavorSubs.getMemberId());
            Map<Tag, Integer> tagFavorCntMap = new HashMap<>();
            sumTagScore(tagScoreMap,tagFavorCntMap,memberSubsList,1);
            resultFavorCntMap.put(memberFavorSubs.getMemberId(), tagFavorCntMap);

        }


        //좋아요와 구독한게 없는 사람은 신규회원이거나 모두지운 회원
        List<Member> allWithTag = memberRepository.findAllWithTag();

        for (Member memberWithTag : allWithTag) {
            List<MemberRelTag> memberRelTags = memberWithTag.getMemberRelTags();
            if (memberWithTag.getMemberHaveSubsList().size() == 0 && memberWithTag.getMemberFavorSubsList().size() == 0) {
                deleteMemberRelTag(memberWithTag);
            } else {
                Map<Tag, Integer> tagScoreMap = result.get(memberWithTag.getMemberId());
                Map<Tag, Integer> tagFavorCntMap = resultFavorCntMap.get(memberWithTag.getMemberId());
                Map<Tag, Integer> tagHaveCntMap = resultHaveCntMap.get(memberWithTag.getMemberId());


                Map<Tag, Integer> sortedMap = GgudokUtil.mapSortByValueDescending(tagScoreMap);
                memberTagSortUpdate(sortedMap.keySet(), tagFavorCntMap, tagHaveCntMap, memberWithTag);
            }



        }

    }

    /**
     * 멤버들의 subs들을 확인한다음
     * subs에 해당하는 태그의 횟수들을 업데이트
     * @param loginId
     */
    @Transactional
    public void updateMemberTagRecommend(String loginId,Subs subs) {
        //멤버들의 tag
        Optional<Member> optionalMember = memberRepository.findByLoginIdWithHaveSubs(loginId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            Map<Tag, Integer> tagScoreMap = new HashMap<>();
            Map<Tag, Integer> tagHaveCntMap = new HashMap<>();

            //회원들을 subsList를 순회하면서
            //해당 subs에 있는 태그들을 순서를 매긴다
            List<Subs> memberHaveSubsList = member.getMemberHaveSubsList().stream().map(srt -> srt.getSubs()).collect(Collectors.toList());

            sumTagScore(tagScoreMap, tagHaveCntMap, memberHaveSubsList, 2);

            List<Subs> memberFavorSubsList = member.getMemberFavorSubsList().stream().map(srt -> srt.getSubs()).collect(Collectors.toList());
            Map<Tag, Integer> tagFavorCntMap = new HashMap<>();

            sumTagScore(tagScoreMap, tagFavorCntMap, memberFavorSubsList, 1);

            if (memberHaveSubsList.size() == 0 && memberFavorSubsList.size() == 0) {
                deleteMemberRelTag(member);
            }

            //좋아요와 구독한 서비스가 없는 회원들은 tag sort를 설정하지않는다.
            Map<Tag, Integer> sortedMap = GgudokUtil.mapSortByValueDescending(tagScoreMap);
            memberTagSortUpdate(sortedMap.keySet(), tagFavorCntMap, tagHaveCntMap, member);

        }
    }

    /**
     * subsList에 속한 subs들을 순회하면서 tag의 score를 구하는 메서드
     * @param tagScoreMap
     * @param subsList
     * @param score
     */
    public void sumTagScore(Map<Tag, Integer> tagScoreMap, Map<Tag,Integer> tagCountMap, List<Subs> subsList, int score) {

        List<Long> subsIdList = subsList.stream().map(s -> s.getSubsId()).collect(Collectors.toList());
        List<Tag> tagsBySubsId = tagService.findTagsBySubIdList(subsIdList);
        //subs내의 태그들을 순회하면서
        //tagScoreMap에 태그들의 점수를 기록한다.
        for (Tag tag : tagsBySubsId) {
            //태그 count체크
            if (tagCountMap.containsKey(tag)) {
                tagCountMap.put(tag, tagCountMap.get(tag) + 1);
            }else{
                tagCountMap.put(tag, 1);
            }

            //tagScore체크
            if (tagScoreMap.containsKey(tag)) {
                tagScoreMap.put(tag, tagScoreMap.get(tag) + score);
            }else{
                tagScoreMap.put(tag, score);
            }
        }


    }

    /**
     * 정렬된 tag들을 순위에 맞게
     * memberRelTag에 업데이트 해준다.
     * @param tagSet
     * @param member
     */

    private void memberTagSortUpdate(Set<Tag> tagSet,Map<Tag, Integer>tagFavorCntMap,Map<Tag, Integer>tagHaveCntMap, Member member) {
        HashMap<Tag, Integer> tagRankMap = new HashMap<>();
        Set<Tag> tags = tagSet;

        int sort = 0;
        for (Tag tagKey : tags) {
            tagRankMap.put(tagKey, ++sort);
        }

        List<MemberRelTag> memberRelTags = member.getMemberRelTags();
        for (MemberRelTag mrt : memberRelTags) {
            if (tagFavorCntMap.size()!=0) {
                if (tagFavorCntMap.containsKey(mrt.getTag())) {
                    mrt.updateFavorCnt(tagFavorCntMap.get(mrt.getTag()));
                }
            }else{
                mrt.updateFavorCnt(0);
            }

            if (tagHaveCntMap.size()!=0) {
                if (tagHaveCntMap.containsKey(mrt.getTag())) {
                    mrt.updateHaveCnt(tagHaveCntMap.get(mrt.getTag()));
                }
            }else{
                mrt.updateHaveCnt(0);
            }

            if (tagRankMap.containsKey(mrt.getTag())) {
                mrt.updateTagSort(tagRankMap.get(mrt.getTag()));
            }

        }
    }

    /**
     * 해당 member의 favorSubsList, HaveSubsList가 없을때
     * member의 memberRelTag 를 순회하면서 기본태그이외의 태그를 지워준다.
     * @param member
     */
    @Transactional
    public void deleteMemberRelTag(Member member) {
        List<MemberRelTag> memberRelTags = member.getMemberRelTags();


        for (int i = memberRelTags.size() - 1; i >= 0; i--) {
            if (memberRelTags.get(i).isBasic()) {
                MemberRelTag memberRelTag = memberRelTags.get(i);
                memberRelTag.updateHaveCnt(0);
                memberRelTag.updateFavorCnt(0);

            }else{
                MemberRelTag memberRelTag = memberRelTags.get(i);
                memberRelTags.remove(memberRelTag);
            }

        }



    }

}
