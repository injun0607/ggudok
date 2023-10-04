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
            Category movie = new Category("영화","ott");
            Category music = new Category("음악","music");



            em.persist(health);
            em.persist(food);
            em.persist(movie);
            em.persist(music);

            Subs healthCare = new Subs("healthCare");
            Subs dosirak = new Subs("dosirak");
            Subs healthcare2 = new Subs("healthCare2");
            Subs netfilx = new Subs("netfilx");
            Subs melon = new Subs("melon");
            Subs watcha = new Subs("watcha");

            netfilx.addCategory(movie);
            melon.addCategory(music);
            watcha.addCategory(movie);
            healthCare.addCategory(health);
            dosirak.addCategory(food);
            healthcare2.addCategory(health);

            em.persist(netfilx);
            em.persist(melon);
            em.persist(watcha);
            em.persist(healthCare);
            em.persist(dosirak);
            em.persist(healthcare2);

            SubsRank netfilxDef = new SubsRank("기본", 4500, RankLevel.DEFAULT);
            SubsRank netfilxPrime = new SubsRank("프리미엄", 5500, RankLevel.PRIME);

            netfilxDef.addSubs(netfilx);
            netfilxPrime.addSubs(netfilx);

            SubsRank melonxDef = new SubsRank("스트리밍", 2500, RankLevel.DEFAULT);
            SubsRank melonPrime = new SubsRank("다운포함", 6500, RankLevel.PRIME);

            melonxDef.addSubs(melon);
            melonPrime.addSubs(melon);

            SubsRank watchaDef = new SubsRank("왓챠 기본", 6900, RankLevel.DEFAULT);
            SubsRank watchaPrime = new SubsRank("왓챠 프리미엄", 7500, RankLevel.PRIME);

            watchaDef.addSubs(watcha);
            watchaPrime.addSubs(watcha);

            SubsContent netdefC1 = new SubsContent("넷플릭스 기본 구독혜택1");
            SubsContent netdefC2 = new SubsContent("넷플릭스 기본 구독혜택2");

            SubsContent netdefP1 = new SubsContent("넷플릭스 프리미엄 구독혜택1");
            SubsContent netdefP2 = new SubsContent("넷플릭스 프리미엄 구독혜택2");
            SubsContent netdefP3 = new SubsContent("넷플릭스 프리미엄 구독혜택3");


            netdefC1.addSubsRank(netfilxDef);
            netdefC2.addSubsRank(netfilxDef);

            netdefP1.addSubsRank(netfilxPrime);
            netdefP2.addSubsRank(netfilxPrime);
            netdefP3.addSubsRank(netfilxPrime);

            SubsContent meldefC1 = new SubsContent("멜론 기본 구독혜택1");
            SubsContent meldefC2 = new SubsContent("멜론 기본 구독혜택2)");

            SubsContent meldefP1 = new SubsContent("멜론 프리미엄 구독혜택1");
            SubsContent meldefP2 = new SubsContent("멜론 프리미엄 구독혜택2");
            SubsContent meldefP3 = new SubsContent("멜론 프리미엄 구독혜택3");


            meldefC1.addSubsRank(melonxDef);
            meldefC2.addSubsRank(melonxDef);

            meldefP1.addSubsRank(melonPrime);
            meldefP2.addSubsRank(melonPrime);
            meldefP3.addSubsRank(melonPrime);

            SubsContent watchadefC1 = new SubsContent("watcha 기본 구독혜택1");
            SubsContent watchadefC2 = new SubsContent("watcha 기본 구독혜택2");

            SubsContent watchadefP1 = new SubsContent("watcha 프리미엄 구독혜택1");
            SubsContent watchadefP2 = new SubsContent("watcha 프리미엄 구독혜택2");
            SubsContent watchadefP3 = new SubsContent("watcha 프리미엄 구독혜택3");


            watchadefC1.addSubsRank(watchaDef);
            watchadefC2.addSubsRank(watchaDef);

            watchadefP1.addSubsRank(watchaPrime);
            watchadefP2.addSubsRank(watchaPrime);
            watchadefP3.addSubsRank(watchaPrime);


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

            SubsContent healthC1 = new SubsContent("헬스케어 기본 구독혜택1");
            SubsContent healthC2 = new SubsContent("헬스케어  기본 구독혜택2");

            SubsContent healthP1 = new SubsContent("헬스케어 프리미엄 구독혜택1");
            SubsContent healthP2 = new SubsContent("헬스케어 프리미엄 구독혜택2");
            SubsContent healthP3 = new SubsContent("헬스케어 프리미엄 구독혜택3");


            healthC1.addSubsRank(healthCareDef);
            healthC2.addSubsRank(healthCareDef);

            healthP1.addSubsRank(healthCarePrime);
            healthP2.addSubsRank(healthCarePrime);
            healthP3.addSubsRank(healthCarePrime);

            SubsContent dosidefC1 = new SubsContent("도시락 기본 구독혜택1");
            SubsContent dosidefC2 = new SubsContent("도시락 기본 구독혜택2)");

            SubsContent dosidefP1 = new SubsContent("도시락 프리미엄 구독혜택1");
            SubsContent dosidefP2 = new SubsContent("도시락 프리미엄 구독혜택2");
            SubsContent dosidefP3 = new SubsContent("도시락 프리미엄 구독혜택3");


            dosidefC1.addSubsRank(dosirakxDef);
            dosidefC2.addSubsRank(dosirakxDef);

            dosidefP1.addSubsRank(dosirakPrime);
            dosidefP2.addSubsRank(dosirakPrime);
            dosidefP3.addSubsRank(dosirakPrime);

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


            netfilx.addTag(age1);
            netfilx.addTag(age2);
            netfilx.addTag(age3);

            netfilx.addTag(gender1);
            netfilx.addTag(gender2);

            melon.addTag(age1);
            melon.addTag(age2);

            watcha.addTag(gender1);
            watcha.addTag(gender2);

        }

    }


}
