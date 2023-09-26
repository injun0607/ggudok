package com.alham.ggudok.service.member;

import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.Review;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTest {

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


        reviewService.writeReview(member,subs1,"리뷰가 참 좋네요",4);
        reviewService.writeReview(member,findSubs2,"리뷰가 참 좋네요2",3);
        reviewService.writeReview(member,findSubs3,"리뷰가 참 좋네요3",5);

        //then
        List<Review> reviews = member.getReviews();
        for (Review review : reviews) {
            System.out.println("review = " + review.getContent());
        }
        Review review1 = reviews.stream().filter(review -> review.getSubs().getSubsId().equals(subs1.getSubsId())).findFirst().get();
        assertEquals(review1.getContent(),"리뷰가 참 좋네요");

        em.flush();
        em.clear();

        reviewService.writeReview(member, subs1, "change Review", 3);

        List<Review> reviews1 = member.getReviews();
        for (Review review : reviews1) {

            System.out.println(review.getContent() +" "+ review.getRating());
        }


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


        reviewService.writeReview(member,subs1,"review so good",4);
        reviewService.writeReview(member,findSubs2,"리뷰가 참 좋네요2",3);
        reviewService.writeReview(member,findSubs3,"리뷰가 참 좋네요3",5);

        Review review = reviewRepository.findReviewByMemberAndSubs(member.getMemberId(), subs1.getSubsId()).get();
        //then

        assertEquals(review.getRating(),4);
        System.out.println("review = " + review.getContent());

    }

    @Test
    public void findSubsReviewsBySubsId()throws Exception{
        //given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setPasswordCheck("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607@naver.com");

        memberService.registerMember(memberRegisterDto);

        MemberRegisterDto memberRegisterDto2 = new MemberRegisterDto();
        memberRegisterDto2.setMemberName("injun");
        memberRegisterDto2.setAge(20);
        memberRegisterDto2.setPassword("123123");
        memberRegisterDto2.setPasswordCheck("123123");
        memberRegisterDto2.setGender(Gender.MAN);
        memberRegisterDto2.setLoginId("yinjun0607@naver.com");

        memberService.registerMember(memberRegisterDto2);

        MemberRegisterDto memberRegisterDto3 = new MemberRegisterDto();
        memberRegisterDto3.setMemberName("injun");
        memberRegisterDto3.setAge(20);
        memberRegisterDto3.setPassword("123123");
        memberRegisterDto3.setPasswordCheck("123123");
        memberRegisterDto3.setGender(Gender.MAN);
        memberRegisterDto3.setLoginId("yhgu0607@naver.com");

        memberService.registerMember(memberRegisterDto3);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);

        Subs subs2 = new Subs("subs2");
        subsRepository.save(subs2);

        Subs subs3 = new Subs("subs3");
        subsRepository.save(subs3);

        em.flush();
        em.clear();
        //when
        Member member1 = memberRepository.findByLoginId("injun0607@naver.com").get();
        Member member2 = memberRepository.findByLoginId("yinjun0607@naver.com").get();
        Member member3 = memberRepository.findByLoginId("yhgu0607@naver.com").get();

        Subs findsubs1 = subsRepository.findSubsBySubsName("subs1");
        Subs findSubs2 = subsRepository.findSubsBySubsName("subs2");
        Subs findSubs3 = subsRepository.findSubsBySubsName("subs3");


        reviewService.writeReview(member1,findsubs1,"review so good",4);
        reviewService.writeReview(member1,findSubs2,"review so good2",3);
        reviewService.writeReview(member1,findSubs3,"review so good3",5);

        reviewService.writeReview(member2,findsubs1,"review so good4",4);
        reviewService.writeReview(member2,findSubs2,"review so good5",3);
        reviewService.writeReview(member2,findSubs3,"review so good6",5);

        reviewService.writeReview(member2,findsubs1,"review so good7",4);
        reviewService.writeReview(member2,findSubs2,"review so good8",3);
        //when
        List<Review> findReviews1 = reviewService.findSubsReviewsBySubsId(findsubs1.getSubsId()).get();
        List<Review> findReviews2 = reviewService.findSubsReviewsBySubsId(findSubs2.getSubsId()).get();
        List<Review> findReviews3 = reviewService.findSubsReviewsBySubsId(findSubs3.getSubsId()).get();
        //then
        assertEquals(findReviews1.size(), 3);
        assertEquals(findReviews2.size(), 3);
        assertEquals(findReviews3.size(), 2);

    }



}