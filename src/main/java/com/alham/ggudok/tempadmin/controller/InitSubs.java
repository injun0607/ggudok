package com.alham.ggudok.tempadmin.controller;


import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.*;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitSubs {

    private final InitSubsService subsService;

    @PostConstruct
    public void init() {
        subsService.init();

    }


    @Component
    static class InitSubsService {



        @PersistenceContext
        private EntityManager em;



        @Transactional
        public void init() {
            Category movie = new Category("영화","ott");
            Category music = new Category("음악","music");

            em.persist(movie);
            em.persist(music);

            Subs netfilx = new Subs("netfilx");
            Subs melon = new Subs("melon");
            Subs watcha = new Subs("watcha");

            netfilx.addCategory(movie);
            melon.addCategory(music);
            watcha.addCategory(movie);

            em.persist(netfilx);
            em.persist(melon);
            em.persist(watcha);

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

            Tag age0 = new Tag("10대");
            Tag age1 = new Tag("20대");
            Tag age2 = new Tag("30대");
            Tag age3 = new Tag("40대");
            Tag age4 = new Tag("50대");

            Tag gender1 = new Tag("여성");
            Tag gender2 = new Tag("남성");

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

            em.persist(category1);
            em.persist(category2);
            em.persist(category3);
            em.persist(category4);
            em.persist(category5);
            em.persist(category6);
            em.persist(category7);
            em.persist(category8);

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
