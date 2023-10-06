package com.alham.ggudok.service.member;

import com.alham.ggudok.dto.member.MemberLoginDto;
import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.dto.member.MemberUpdateDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.*;
import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.member.ReviewRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SubsRepository subsRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void createMemberHaveSubs()throws Exception{
        //given
        Member member = new Member("injun", 10);
        memberRepository.save(member);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);
        memberService.createMemberHaveSubs(member, subs,RankLevel.DEFAULT);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getMemberId()).get();
        List<MemberHaveSubs> memberHaveSubsList = findMember.getMemberHaveSubsList();


        for (MemberHaveSubs haveSubs : memberHaveSubsList) {
            System.out.println("haveSubs = " + haveSubs.getSubs().getSubsName());
        }
        //when

        //then
        assertEquals(memberHaveSubsList.size(),1);

    }

    @Test
    public void registerMember()throws Exception{
        //given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setPasswordCheck("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607@naver.com");

        MemberRegisterDto memberRegisterDto2 = new MemberRegisterDto();
        memberRegisterDto2.setMemberName("injun");
        memberRegisterDto2.setAge(20);
        memberRegisterDto2.setPassword("123123");
        memberRegisterDto2.setPasswordCheck("123123");
        memberRegisterDto2.setGender(Gender.MAN);
        memberRegisterDto2.setLoginId("injun0607@google.com");

        //when
        boolean check = memberService.registerMember(memberRegisterDto);
        boolean check2 = memberService.registerMember(memberRegisterDto2);
        //then
        assertEquals(check,true);



    }

    @Test
    public void memberLoginCheck()throws Exception{
        //given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setPasswordCheck("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607@naver.com");

        MemberRegisterDto memberRegisterDto2 = new MemberRegisterDto();
        memberRegisterDto2.setMemberName("seohee");
        memberRegisterDto2.setAge(20);
        memberRegisterDto2.setPassword("321321");
        memberRegisterDto2.setPasswordCheck("321321");
        memberRegisterDto2.setGender(Gender.WOMAN);
        memberRegisterDto2.setLoginId("seohee@naver.com");

        memberService.registerMember(memberRegisterDto);
        memberService.registerMember(memberRegisterDto2);

        em.flush();
        em.clear();


        //when
        Member memberInjun = memberService.memberLoginCheck(new MemberLoginDto("injun0607@naver.com", "123123"));
        Member seohee = memberService.memberLoginCheck(new MemberLoginDto("seohee@naver.com", "321321"));
        //then
        assertEquals(memberInjun.getMemberName(),"injun") ;
        assertEquals(seohee.getMemberName(),"seohee");

    }

    @Test
    public void updateTest()throws Exception{
        //given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setPasswordCheck("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607@naver.com");

        memberService.registerMember(memberRegisterDto);
        em.flush();
        em.clear();

        Member findMember = memberRepository.findByLoginId("injun0607@naver.com").get();
        //when
        MemberUpdateDto updateDto = new MemberUpdateDto();
        updateDto.setPassword("123");
        updateDto.setPhoneNumber("0101234");
        updateDto.setGender(Gender.MAN);
        updateDto.setAge(23);

        memberService.updateMember(updateDto,findMember);

        em.flush();
        em.clear();

        Member member = memberRepository.findById(findMember.getMemberId()).get();
        //then
        assertEquals(member.getPhoneNumber(), "0101234");

    }



    @Test
    public void removeMemberHaveSubs()throws Exception{
        //given
        Member member = new Member("injun", 10,"injun0607@naver.com","1234",Gender.MAN,"01012345678");
        memberRepository.save(member);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);
        memberService.createMemberFavorSubs(member, subs);

        em.flush();
        em.clear();
        //when
        Member findMember = memberService.findByLoginIdWithFavorSubs("injun0607@naver.com");
        Subs subs1 = subsRepository.findSubsBySubsName("subs1");
        //then
        assertEquals(findMember.getMemberFavorSubsList().size(),1);

        memberService.removeMemberFavorSubs("injun0607@naver.com",subs1);
        em.flush();
        em.clear();
        assertEquals(findMember.getMemberFavorSubsList().size(), 0);


    }

    @Test
    public void findMemberWithReviewsByloginId()throws Exception{
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setPasswordCheck("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607@naver.com");

        memberService.registerMember(memberRegisterDto);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);

        Subs subs2 = new Subs("subs2");
        subsRepository.save(subs2);

        Subs subs3 = new Subs("subs3");
        subsRepository.save(subs3);

        em.flush();
        em.clear();
        //when
        Member member1 = memberRepository.findMemberWithReviewsByloginId("injun0607@naver.com").get();


        Subs findsubs1 = subsRepository.findSubsBySubsName("subs1");
        Subs findSubs2 = subsRepository.findSubsBySubsName("subs2");
        Subs findSubs3 = subsRepository.findSubsBySubsName("subs3");


        reviewService.writeReview(member1,findsubs1,"review so good",4);
        reviewService.writeReview(member1,findSubs2,"review so good2",3);
        reviewService.writeReview(member1,findSubs3,"review so good3",5);


        member1.getReviews().stream().forEach(r->System.out.println(r.getContent()));
    }

    @Test
    public void removeReview()throws Exception{
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setPasswordCheck("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607@naver.com");

        memberService.registerMember(memberRegisterDto);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);

        Subs subs2 = new Subs("subs2");
        subsRepository.save(subs2);

        Subs subs3 = new Subs("subs3");
        subsRepository.save(subs3);

        em.flush();
        em.clear();
        //when
        Member member1 = memberRepository.findMemberWithReviewsByloginId("injun0607@naver.com").get();


        Subs findsubs1 = subsRepository.findSubsBySubsName("subs1");
        Subs findSubs2 = subsRepository.findSubsBySubsName("subs2");
        Subs findSubs3 = subsRepository.findSubsBySubsName("subs3");


        reviewService.writeReview(member1,findsubs1,"review so good",4);
        reviewService.writeReview(member1,findSubs2,"review so good2",3);
        reviewService.writeReview(member1,findSubs3,"review so good3",5);


        member1.getReviews().stream().forEach(r->System.out.println(r.getContent()));

        memberService.removeReview("injun0607@naver.com", findsubs1.getSubsId());

        em.flush();
        em.clear();

        Member member2 = memberRepository.findMemberWithReviewsByloginId("injun0607@naver.com").get();
        List<Review> reviews = member2.getReviews();
        assertEquals(reviews.size(),2);


    }

    @Test
    public void buySubs()throws Exception{
        //given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setPasswordCheck("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607@naver.com");

        memberService.registerMember(memberRegisterDto);

        Subs subs = new Subs("subs1");

        subsRepository.save(subs);

        em.flush();
        em.clear();

        Subs findsubs = subsRepository.findSubsBySubsName("subs1");

        memberService.buySubs("injun0607@naver.com", findsubs, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", findsubs, RankLevel.DEFAULT);
        em.flush();
        em.clear();
        //when
        Member findMember = memberService.findByLoginIdWithHaveSubs("injun0607@naver.com");


        //then

        assertEquals(findMember.getMemberHaveSubsList().size(),1);
    }

    @Test
    public void userRecommendTag()throws Exception{
        //given
        Map<Long, Map<Tag, Integer>> result = new HashMap<>();

        Map<Tag, Integer> tagScore = new HashMap<>();
        Tag tag1 = new Tag("태그1");
        Tag tag2 = new Tag("태그2");

        em.persist(tag1);
        em.persist(tag2);

        //when

        tagScore.put(tag1, 2);
        tagScore.put(tag2, 4);

        result.put(1l, tagScore);
        //then

        Map<Tag, Integer> tagScore2 = result.get(1l);
        tagScore2.put(tag1, 3);
        tagScore2.put(tag2, 5);

        assertEquals(result.get(1l).get(tag1),3);

        memberRepository.findAllWithFavorSubs();
        memberRepository.findAllWithTag();
        memberRepository.findAllWithHaveSubs();

    }


}