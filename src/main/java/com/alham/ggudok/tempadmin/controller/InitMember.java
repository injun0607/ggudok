package com.alham.ggudok.tempadmin.controller;

import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.*;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.subs.SubsService;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;
    private final MemberService memberService;

    private final AdminSubsService adminSubsService;

    private final SubsService subsService;

    private final SubsRepository subsRepository;
    private final InitSubs initSubs;

    private final EntityManager em;



    @PostConstruct
    @Transactional
    public void init() {
        initSubs.init();
        initMemberService.init();
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("깡깡지");
        memberRegisterDto.setAge(4);
        memberRegisterDto.setLoginId("choiseo26@naver.com");
        memberRegisterDto.setGender(Gender.WOMAN);
        memberRegisterDto.setPassword("1234");
        memberRegisterDto.setPhoneNumber("01012345678");
        memberRegisterDto.setPasswordCheck("1234");
        memberService.registerMember(memberRegisterDto);

        MemberRegisterDto memberRegisterDto2 = new MemberRegisterDto();
        memberRegisterDto2.setMemberName("인인준");
        memberRegisterDto2.setAge(4);
        memberRegisterDto2.setLoginId("injun0607@naver.com");
        memberRegisterDto2.setGender(Gender.MAN);
        memberRegisterDto2.setPassword("1234");
        memberRegisterDto2.setPhoneNumber("01012345678");
        memberRegisterDto2.setPasswordCheck("1234");
        memberService.registerMember(memberRegisterDto2);


        MemberRegisterDto memberRegisterDto3 = new MemberRegisterDto();
        memberRegisterDto3.setMemberName("인인준준");
        memberRegisterDto3.setAge(4);
        memberRegisterDto3.setLoginId("yhgu0607@naver.com");
        memberRegisterDto3.setGender(Gender.WOMAN);
        memberRegisterDto3.setPassword("1234");
        memberRegisterDto3.setPhoneNumber("01012345678");
        memberRegisterDto3.setPasswordCheck("1234");
        memberService.registerMember(memberRegisterDto3);



    }
    @Component
    static class InitMemberService {


        @PersistenceContext
        private EntityManager em;


        @Transactional
        public void init() {

            Member injun = new Member("injun12", 23, "injun@naver.com", "1234", Gender.MAN, "01012345678");
            Member seohee = new Member("seohee", 23, "seohee@naver.com", "1234", Gender.WOMAN, "0101234");


            em.persist(injun);
            em.persist(seohee);

        }
    }


    @Component
    static public class InitSubs{



        @PersistenceContext
        private EntityManager em;

        @Autowired
        private SubsRepository subsRepository;

        @Autowired
        private MemberService memberService;
        @Transactional
        public void init() {
            Category health = new Category("건강","health");
            Category food = new Category("식품","food");

            em.persist(health);
            em.persist(food);

            Subs healthCare = new Subs("healthCare");
            Subs dosirak = new Subs("dosirak");
            Subs healthcare2 = new Subs("healthCare2");

            healthCare.addCategory(health);
            dosirak.addCategory(food);
            healthcare2.addCategory(health);

            em.persist(healthCare);
            em.persist(dosirak);
            em.persist(healthcare2);

            SubsRank healthCareDef = new SubsRank("기본", 7500, RankLevel.DEFAULT);
            SubsRank healthCarePrime = new SubsRank("프리미엄", 8500, RankLevel.PRIME);

            healthCareDef.addSubs(healthCare);
            healthCarePrime.addSubs(healthCare);

            SubsRank dosirakxDef = new SubsRank("아침", 2500, RankLevel.DEFAULT);
            SubsRank dosirakPrime = new SubsRank("아침및점심", 6500, RankLevel.PRIME);

            dosirakxDef.addSubs(dosirak);
            dosirakPrime.addSubs(dosirak);

            SubsRank healthcare2Def = new SubsRank("헬스 기본", 3900, RankLevel.DEFAULT);
            SubsRank healthcare2Prime = new SubsRank("헬스 프리미엄", 9500, RankLevel.PRIME);

            healthcare2Def.addSubs(healthcare2);
            healthcare2Prime.addSubs(healthcare2);

            SubsContent netdefC1 = new SubsContent("헬스케어 기본 구독혜택1");
            SubsContent netdefC2 = new SubsContent("헬스케어  기본 구독혜택2");

            SubsContent netdefP1 = new SubsContent("헬스케어 프리미엄 구독혜택1");
            SubsContent netdefP2 = new SubsContent("헬스케어 프리미엄 구독혜택2");
            SubsContent netdefP3 = new SubsContent("헬스케어 프리미엄 구독혜택3");


            netdefC1.addSubsRank(healthCareDef);
            netdefC2.addSubsRank(healthCareDef);

            netdefP1.addSubsRank(healthCarePrime);
            netdefP2.addSubsRank(healthCarePrime);
            netdefP3.addSubsRank(healthCarePrime);

            SubsContent meldefC1 = new SubsContent("도시락 기본 구독혜택1");
            SubsContent meldefC2 = new SubsContent("도시락 기본 구독혜택2)");

            SubsContent meldefP1 = new SubsContent("도시락 프리미엄 구독혜택1");
            SubsContent meldefP2 = new SubsContent("도시락 프리미엄 구독혜택2");
            SubsContent meldefP3 = new SubsContent("도시락 프리미엄 구독혜택3");


            meldefC1.addSubsRank(dosirakxDef);
            meldefC2.addSubsRank(dosirakxDef);

            meldefP1.addSubsRank(dosirakPrime);
            meldefP2.addSubsRank(dosirakPrime);
            meldefP3.addSubsRank(dosirakPrime);

            SubsContent healthcare2defC1 = new SubsContent("healthcare2 기본 구독혜택1");
            SubsContent healthcare2defC2 = new SubsContent("healthcare2 기본 구독혜택2");

            SubsContent healthcare2defP1 = new SubsContent("healthcare2 프리미엄 구독혜택1");
            SubsContent healthcare2defP2 = new SubsContent("healthcare2 프리미엄 구독혜택2");
            SubsContent healthcare2defP3 = new SubsContent("healthcare2 프리미엄 구독혜택3");


            healthcare2defC1.addSubsRank(healthcare2Def);
            healthcare2defC2.addSubsRank(healthcare2Def);

            healthcare2defP1.addSubsRank(healthcare2Prime);
            healthcare2defP2.addSubsRank(healthcare2Prime);
            healthcare2defP3.addSubsRank(healthcare2Prime);

            Tag age0 = new Tag("10대");
            Tag age1 = new Tag("20대");
            Tag age2 = new Tag("30대");
            Tag age3 = new Tag("40대");
            Tag age4 = new Tag("50대");

            Tag gender1 = new Tag("여성");
            Tag gender2 = new Tag("남성");

            Tag foodTag = new Tag("식품");
            Tag healthTag = new Tag("건강");

            Tag category1 = new Tag("영상");
            Tag category2 = new Tag("음악");
            Tag category3 = new Tag("식품");
            Tag category4 = new Tag("건강");
            Tag category5 = new Tag("음료");
            Tag category6 = new Tag("주류");
            Tag category7 = new Tag("패션잡화");
            Tag category8 = new Tag("책");

            em.persist(age1);
            em.persist(age2);
            em.persist(age3);

            em.persist(gender1);
            em.persist(gender2);

            em.persist(foodTag);
            em.persist(healthTag);

            em.persist(category1);
            em.persist(category2);
            em.persist(category3);
            em.persist(category4);
            em.persist(category5);
            em.persist(category6);
            em.persist(category7);
            em.persist(category8);

            healthCare.addTag(age1);
            healthCare.addTag(age2);
            healthCare.addTag(age3);
            healthCare.addTag(healthTag);

            healthCare.addTag(gender1);
            healthCare.addTag(gender2);

            dosirak.addTag(age1);
            dosirak.addTag(age2);
            dosirak.addTag(foodTag);

            healthcare2.addTag(gender1);
            healthcare2.addTag(gender2);
            healthcare2.addTag(healthTag);


        }

    }


}
