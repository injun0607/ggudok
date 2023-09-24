package com.alham.ggudok.service.member;

import com.alham.ggudok.dto.member.MemberLoginDto;
import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.dto.member.MemberUpdateDto;
import com.alham.ggudok.entity.member.*;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.member.ReviewRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @PersistenceContext
    EntityManager em;

    @Test
    public void createMemberHaveSubs()throws Exception{
        //given
        Member member = new Member("injun", 10);
        memberRepository.save(member);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);
        MemberHaveSubs memberHaveSubs = memberService.createMemberHaveSubs(member, subs);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getMemberId()).get();
        List<MemberHaveSubs> memberHaveSubsList = findMember.getMemberHaveSubsList();


        for (MemberHaveSubs haveSubs : memberHaveSubsList) {
            System.out.println("haveSubs = " + haveSubs.getSubs().getSubsName());
        }
        //when

        //then
        assertEquals(memberHaveSubs.getMember().getMemberName(),"injun");

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
        assertEquals(member.getPassword(), "123");
        assertEquals(member.getPhoneNumber(), "0101234");

    }

    @Test
    public void writeReview()throws Exception{
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

        Subs subs2 = new Subs("subs2");
        subsRepository.save(subs2);

        Subs subs3 = new Subs("subs3");
        subsRepository.save(subs3);

        em.flush();
        em.clear();
        //when
        Member member = memberRepository.findByLoginId("injun0607@naver.com").get();
        Subs subs1 = subsRepository.findSubsBySubsName("subs1");
        Subs findSubs2 = subsRepository.findSubsBySubsName("subs2");
        Subs findSubs3 = subsRepository.findSubsBySubsName("subs3");


        memberService.writeReview(member,subs1,"리뷰가 참 좋네요",4);
        memberService.writeReview(member,findSubs2,"리뷰가 참 좋네요2",3);
        memberService.writeReview(member,findSubs3,"리뷰가 참 좋네요3",5);

        //then
        List<Review> reviews = member.getReviews();
        for (Review review : reviews) {
             System.out.println("review = " + review.getContent());
        }
                Review review1 = reviews.stream().filter(review -> review.getSubs().getSubsId().equals(subs1.getSubsId())).findFirst().get();
        assertEquals(review1.getContent(),"리뷰가 참 좋네요");

        Review review2 = reviews.stream().filter(review -> review.getSubs().getSubsId().equals(findSubs2.getSubsId())).findFirst().get();
        Review review3 = reviews.stream().filter(review -> review.getSubs().getSubsId().equals(findSubs3.getSubsId())).findFirst().get();

    }

    @Test
    public void findReviewByMemberAndSubs()throws Exception{
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

        Subs subs2 = new Subs("subs2");
        subsRepository.save(subs2);

        Subs subs3 = new Subs("subs3");
        subsRepository.save(subs3);

        em.flush();
        em.clear();
        //when
        Member member = memberRepository.findByLoginId("injun0607@naver.com").get();
        Subs subs1 = subsRepository.findSubsBySubsName("subs1");
        Subs findSubs2 = subsRepository.findSubsBySubsName("subs2");
        Subs findSubs3 = subsRepository.findSubsBySubsName("subs3");


        memberService.writeReview(member,subs1,"review so good",4);
        memberService.writeReview(member,findSubs2,"리뷰가 참 좋네요2",3);
        memberService.writeReview(member,findSubs3,"리뷰가 참 좋네요3",5);

        Review review = reviewRepository.findReviewByMemberAndSubs(member.getMemberId(), subs1.getSubsId()).get();
        //then

        assertEquals(review.getRating(),4);
        System.out.println("review = " + review.getContent());

    }

    @Test
    public void removeMemberHaveSubs()throws Exception{
        //given
        Member member = new Member("injun", 10,"injun0607@naver.com","1234",Gender.MAN,"01012345678");
        memberRepository.save(member);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);
        MemberFavorSubs memberFavorSubs = memberService.createMemberFavorSubs(member, subs);

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


}