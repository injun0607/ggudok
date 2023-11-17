package com.alham.ggudok.tempadmin.controller;

import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.subs.*;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.service.member.MemberService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;
    private final MemberService memberService;

    private final InitSubs initSubs;



    @PostConstruct
    @Transactional
    public void init() {
        initSubs.init();
        initMemberService.init();
//        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
//        memberRegisterDto.setMemberName("깡깡지");
//        memberRegisterDto.setAge(4);
//        memberRegisterDto.setLoginId("choiseo26@naver.com");
//        memberRegisterDto.setGender(Gender.WOMAN);
//        memberRegisterDto.setPassword("1234");
//        memberRegisterDto.setPhoneNumber("01012345678");
//        memberRegisterDto.setPasswordCheck("1234");
//        memberService.registerMember(memberRegisterDto);
//
//
//        MemberRegisterDto memberRegisterDto2 = new MemberRegisterDto();
//        memberRegisterDto2.setMemberName("인인준");
//        memberRegisterDto2.setAge(4);
//        memberRegisterDto2.setLoginId("injun0607@naver.com");
//        memberRegisterDto2.setGender(Gender.MAN);
//        memberRegisterDto2.setPassword("1234");
//        memberRegisterDto2.setPhoneNumber("01012345678");
//        memberRegisterDto2.setPasswordCheck("1234");
//        memberService.registerMember(memberRegisterDto2);
//
//
//        MemberRegisterDto memberRegisterDto3 = new MemberRegisterDto();
//        memberRegisterDto3.setMemberName("인인준준");
//        memberRegisterDto3.setAge(4);
//        memberRegisterDto3.setLoginId("yhgu0607@naver.com");
//        memberRegisterDto3.setGender(Gender.WOMAN);
//        memberRegisterDto3.setPassword("1234");
//        memberRegisterDto3.setPhoneNumber("01012345678");
//        memberRegisterDto3.setPasswordCheck("1234");
//        memberService.registerMember(memberRegisterDto3);



    }
    @Component
    static class InitMemberService {


        @PersistenceContext
        private EntityManager em;


        @Transactional
        public void init() {



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
            Category movie = new Category("영상","ott");
            Category music = new Category("음악","music");
            Category drink = new Category("음료","drink");
            Category extra = new Category("패션잡화","extra");
            Category book = new Category("책","book");
            Category plant = new Category("식물","plant");
            Category ride = new Category("교통", "ride");


            Random random = new Random();
            List<Category> categories = new ArrayList<>();

            categories.add(health);
            categories.add(food);
            categories.add(movie);
            categories.add(music);
            categories.add(drink);
            categories.add(extra);
            categories.add(book);
            categories.add(plant);
            categories.add(ride);

            em.persist(health);
            em.persist(food);
            em.persist(movie);
            em.persist(music);
            em.persist(drink);
            em.persist(extra);
            em.persist(book);
            em.persist(plant);
            em.persist(ride);


            List<Subs> subsList = new ArrayList<>();

            Subs healthCare = new Subs("healthCare");
            Subs dosirak = new Subs("dosirak");
            Subs healthcare2 = new Subs("healthCare2");
            Subs netfilx = new Subs("netfilx");
            Subs melon = new Subs("melon");
            Subs watcha = new Subs("watcha");



            Subs movie1 = new Subs("스마트비디오");
            Subs movie2 = new Subs("클립비젼");
            Subs movie3 = new Subs("월드픽");
            Subs movie4 = new Subs("무비스톤");
            Subs movie5 = new Subs("뷰온");
            Subs movie6 = new Subs("메디앙");
            Subs movie7 = new Subs("플레이캐스트");
            Subs movie8 = new Subs("포토랩");
            Subs movie9 = new Subs("무한티비");
            Subs movie10 = new Subs("멜로디데이");
            Subs movie11 = new Subs("뮤직파크");
            Subs movie12 = new Subs("소울플레이");
            Subs movie13 = new Subs("하모니스");
            Subs movie14 = new Subs("힙스테이션");
            Subs movie15 = new Subs("사운드메이트");
            Subs movie16 = new Subs("플레이쳐");
            Subs movie17 = new Subs("소울카페");
            Subs movie18 = new Subs("헤비볼륨");
            Subs movie19 = new Subs("올드멜로디");
            Subs movie20 = new Subs("푸드뱅크");
            Subs movie21 = new Subs("맛의달인");
            Subs movie23 = new Subs("크림드벨리");
            Subs movie24 = new Subs("프레시풋");
            Subs movie25 = new Subs("고메마켓");


            Subs movie26 = new Subs("푸드팜");
            Subs movie27 = new Subs("푸드샵");
            Subs movie28 = new Subs("맛나랑");
            Subs movie29 = new Subs("헬시식품");
            Subs movie30 = new Subs("알뜰식품");
            Subs movie31 = new Subs("헬스킹");
            Subs movie32 = new Subs("웰니스힐");
            Subs movie33 = new Subs("리커버업");
            Subs movie34 = new Subs("비타민케어");


            Subs movie35 = new Subs("라이프프리");
            Subs movie36 = new Subs("비건라이프");
            Subs movie37 = new Subs("스포츠클럽");
            Subs movie38 = new Subs("피트업");
            Subs movie39 = new Subs("알로하웰");
            Subs movie40 = new Subs("포인트웰");
            Subs movie41 = new Subs("프레쉬브루");
            Subs movie42 = new Subs("슈퍼스무디");
            Subs movie43 = new Subs("탄산아트");

            Subs movie44 = new Subs("커피베이");
            Subs movie45 = new Subs("오가닉티");
            Subs movie46 = new Subs("프루티칵테일");
            Subs movie47 = new Subs("힐링티");
            Subs movie48 = new Subs("밀크오션");
            Subs movie49 = new Subs("프리미엄스무디");
            Subs movie50 = new Subs("빙그레이");
            Subs movie51 = new Subs("우리식물");
            Subs movie52 = new Subs("그린그레스");



            subsList.add(movie1);
            subsList.add(movie2);
            subsList.add(movie3);
            subsList.add(movie4);
            subsList.add(movie5);
            subsList.add(movie6);
            subsList.add(movie7);
            subsList.add(movie8);
            subsList.add(movie9);
            subsList.add(movie10);
            subsList.add(movie11);
            subsList.add(movie12);
            subsList.add(movie13);
            subsList.add(movie14);
            subsList.add(movie15);
            subsList.add(movie16);
            subsList.add(movie17);
            subsList.add(movie18);
            subsList.add(movie19);
            subsList.add(movie20);
            subsList.add(movie21);
            subsList.add(movie23);
            subsList.add(movie24);
            subsList.add(movie5);
            subsList.add(movie26);
            subsList.add(movie27);
            subsList.add(movie28);
            subsList.add(movie29);
            subsList.add(movie30);
            subsList.add(movie31);
            subsList.add(movie32);
            subsList.add(movie33);
            subsList.add(movie34);
            subsList.add(movie35);
            subsList.add(movie36);
            subsList.add(movie37);
            subsList.add(movie38);
            subsList.add(movie39);
            subsList.add(movie40);
            subsList.add(movie41);
            subsList.add(movie42);
            subsList.add(movie43);
            subsList.add(movie44);
            subsList.add(movie45);
            subsList.add(movie46);
            subsList.add(movie47);
            subsList.add(movie48);
            subsList.add(movie49);
            subsList.add(movie50);
            subsList.add(movie51);
            subsList.add(movie52);


            netfilx.addCategory(movie);
            melon.addCategory(music);
            watcha.addCategory(movie);
            healthCare.addCategory(health);
            dosirak.addCategory(food);
            healthcare2.addCategory(health);

            for (Subs subs : subsList) {
                em.persist(subs);
            }

            for (int i = 0; i < subsList.size(); i++) {
                Subs subs = subsList.get(i);
                SubsRank subsRankDef = new SubsRank("기본",  (i+1)* 400, RankLevel.DEFAULT);
                SubsRank subsRankPrime = new SubsRank("프라임", (i+2) * 600, RankLevel.PRIME);
                SubsRank subsRankPremium = new SubsRank("프리미엄", (i+2) * 900, RankLevel.PREMIUM);

                subsRankDef.addSubs(subs);
                subsRankPrime.addSubs(subs);
                subsRankPremium.addSubs(subs);

                SubsContent defCon1 = new SubsContent(subs.getSubsName() + " 기본 구독혜택 1");
                SubsContent defCon2 = new SubsContent(subs.getSubsName() + " 기본 구독혜택 2");

                SubsContent primeCon1 = new SubsContent(subs.getSubsName() + " 프라임 구독혜택 1");
                SubsContent primeCon2 = new SubsContent(subs.getSubsName() + " 프라임 구독혜택 2");
                SubsContent primeCon3 = new SubsContent(subs.getSubsName() + " 프라임 구독혜택 3");

                SubsContent primiumCon1 = new SubsContent(subs.getSubsName() + " 프리미엄 구독혜택 1");
                SubsContent primiumCon2 = new SubsContent(subs.getSubsName() + " 프리미엄 구독혜택 2");
                SubsContent primiumCon3 = new SubsContent(subs.getSubsName() + " 프리미엄 구독혜택 3");


                subs.addCategory(categories.get(random.nextInt(categories.size())));

                defCon1.addSubsRank(subsRankDef);
                defCon2.addSubsRank(subsRankDef);

                primeCon1.addSubsRank(subsRankPrime);
                primeCon2.addSubsRank(subsRankPrime);
                primeCon3.addSubsRank(subsRankPrime);

                primiumCon1.addSubsRank(subsRankPremium);
                primiumCon2.addSubsRank(subsRankPremium);
                primiumCon3.addSubsRank(subsRankPremium);

            }

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
            Tag category5 = new Tag("음료");
            Tag category6 = new Tag("식물");
            Tag category7 = new Tag("패션잡화");
            Tag category8 = new Tag("책");
            Tag category9 = new Tag("교통");


            List<Tag> categoryTag = new ArrayList<>();
            categoryTag.add(category1);
            categoryTag.add(category2);
            categoryTag.add(category5);
            categoryTag.add(category6);
            categoryTag.add(category7);
            categoryTag.add(category8);
            categoryTag.add(category9);
            categoryTag.add(healthTag);
            categoryTag.add(foodTag);

            em.persist(age1);
            em.persist(age2);
            em.persist(age3);

            em.persist(gender1);
            em.persist(gender2);

            em.persist(foodTag);
            em.persist(healthTag);

            em.persist(category1);
            em.persist(category2);
            em.persist(category5);
            em.persist(category6);
            em.persist(category7);
            em.persist(category8);
            em.persist(category9);

            List<Tag> tagList = new ArrayList<>();
            tagList.add(age1);
            tagList.add(age2);
            tagList.add(age3);
            tagList.add(gender1);
            tagList.add(gender2);
            tagList.add(foodTag);
            tagList.add(healthTag);
            tagList.add(category1);
            tagList.add(category2);
            tagList.add(category5);
            tagList.add(category6);
            tagList.add(category7);
            tagList.add(category8);
            tagList.add(category9);




            for (Subs subs : subsList) {
                subs.addTag(tagList.get(random.nextInt(13)));
                subs.addTag(tagList.get(random.nextInt(13)));
                String categoryName = subs.getCategory().getCategoryName();
                Tag tag = categoryTag.stream().filter(t -> t.getTagName().equals(categoryName)).findAny().get();
                subs.addTag(tag);

            }

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
            netfilx.addTag(category1);

            netfilx.addTag(gender1);
            netfilx.addTag(gender2);

            melon.addTag(age1);
            melon.addTag(age2);
            melon.addTag(category2);


            watcha.addTag(gender1);
            watcha.addTag(gender2);
            watcha.addTag(category1);

        }

    }


}
