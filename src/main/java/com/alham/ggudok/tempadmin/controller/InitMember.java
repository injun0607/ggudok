package com.alham.ggudok.tempadmin.controller;

import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Role;
import com.alham.ggudok.entity.subs.*;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.service.member.MemberService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 초기 데이터 init component
 * 카테고리
 * 구독서비스
 * 관리자
 * 이벤트
 *
 */
@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final MemberService memberService;

    private final InitSubs initSubs;




    @PostConstruct
    @Transactional
    public void init() {
        initSubs.init();
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("깡깡지");
        memberRegisterDto.setAge(4);
        memberRegisterDto.setLoginId("choiseo26@naver.com");
        memberRegisterDto.setGender(Gender.WOMAN);
        memberRegisterDto.setPassword("1234");
        memberRegisterDto.setPhoneNumber("01012345678");
        memberRegisterDto.setPasswordCheck("1234");
        memberRegisterDto.setRole(Role.ADMIN);
        memberService.registerMember(memberRegisterDto);
//
//
        MemberRegisterDto memberRegisterDto2 = new MemberRegisterDto();
        memberRegisterDto2.setMemberName("인인준");
        memberRegisterDto2.setAge(4);
        memberRegisterDto2.setLoginId("injun0607@naver.com");
        memberRegisterDto2.setGender(Gender.MAN);
        memberRegisterDto2.setPassword("1234");
        memberRegisterDto2.setPhoneNumber("01012345678");
        memberRegisterDto2.setPasswordCheck("1234");
        memberRegisterDto2.setRole(Role.ADMIN);
        memberService.registerMember(memberRegisterDto2);
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
    static public class InitSubs{

        @Value("${download.subs.main}")
        private String subsImageUrl;

        @Value("${download.event}")
        private String eventImageUrl;

        @Value("${download.category}")
        private String categoryImageUrl;

        @PersistenceContext
        private EntityManager em;

        @Autowired
        private SubsRepository subsRepository;

        @Autowired
        private MemberService memberService;
        @Transactional
        public void init() {

            //카테고리 등록
            Category food = new Category("식품","food");
            food.updateCategoryImage(categoryImageUrl+"food.svg");

            Category culture = new Category("문화","culture");
            culture.updateCategoryImage(categoryImageUrl+"culture.svg");

            Category drink = new Category("음료","drink");
            drink.updateCategoryImage(categoryImageUrl+"drink.svg");

            Category extra = new Category("패션/잡화","extra");
            extra.updateCategoryImage(categoryImageUrl+"extra.svg");

            Category book = new Category("글/그림","book");
            book.updateCategoryImage(categoryImageUrl+"book.svg");

            Category plant = new Category("식물","plant");
            plant.updateCategoryImage(categoryImageUrl+"plant.svg");

            Category ride = new Category("교통", "ride");
            ride.updateCategoryImage(categoryImageUrl+"ride.svg");

            Category health = new Category("건강/뷰티","health");
            health.updateCategoryImage(categoryImageUrl+"health.svg");



            em.persist(health);
            em.persist(food);
            em.persist(culture);
            em.persist(drink);
            em.persist(extra);
            em.persist(book);
            em.persist(plant);
            em.persist(ride);

            //나이
            Tag age20 = new Tag("20대");
            Tag age30 = new Tag("30대");
            Tag age40 = new Tag("40대");
            Tag age50 = new Tag("50대");
            Tag age60 = new Tag("60대");
            Tag age70 = new Tag("70대");


            //성별
            Tag woman = new Tag("여성");
            Tag man = new Tag("남성");

            //카테고리 태그
            Tag foodTag = new Tag("식품");
            Tag cultureTag = new Tag("문화");
            Tag drinkTag = new Tag("음료");
            Tag extraTag = new Tag("패션/잡화");
            Tag bookTag = new Tag("글/그림");
            Tag plantTag = new Tag("식물");
            Tag rideTag = new Tag("교통");
            Tag healthTag = new Tag("건강/뷰티");

            //치약, 화장품, 김 ,반찬 ,채소, 식료품
            Tag toothPaste = new Tag("치약");
            Tag cosmetic = new Tag("화장품");
            Tag seaWeed = new Tag("김");
            Tag banchan = new Tag("반찬");
            Tag vegetable = new Tag("채소");
            Tag grocery = new Tag("식료품");

            //과일, 영양제
            Tag fruit = new Tag("과일");
            Tag supplements = new Tag("영양제");

            // 과자, 빵, 계란, 샐러드
            Tag snack = new Tag("과자");
            Tag bread = new Tag("빵");
            Tag egg = new Tag("계란");
            Tag salad = new Tag("샐러드");

            // 술, 차, 커피, 우유
            Tag alcohol = new Tag("술");
            Tag tea = new Tag("차");
            Tag coffee = new Tag("커피");
            Tag milk = new Tag("우유");
            Tag water = new Tag("생수");

            // 영상, ott, 음악, 게임, 분석·데이터, 이모티콘
            Tag video = new Tag("영상");
            Tag ott = new Tag("ott");
            Tag music = new Tag("음악");
            Tag game = new Tag("게임");
            Tag analysis = new Tag("분석·데이터");
            Tag emoji = new Tag("이모티콘");

            // 그림, 글
            Tag picture = new Tag("그림");
            Tag text = new Tag("글");

            // 논문, eBook, 어린이
            Tag paper = new Tag("논문");
            Tag eBook = new Tag("eBook");
            Tag children = new Tag("어린이");

            // 생리대, 양말, 반려동물, 방향제, 안경, 의류, 전자기기, 종합몰
            Tag sanitaryPad = new Tag("생리대");
            Tag socks = new Tag("양말");
            Tag pet = new Tag("반려동물");
            Tag airFreshener = new Tag("방향제");
            Tag glasses = new Tag("안경");
            Tag clothing = new Tag("의류");
            Tag electronicDevice = new Tag("전자기기");
            Tag mall = new Tag("종합몰");

            // 꽃
            Tag flower = new Tag("꽃");

            // 자전거, 자동차
            Tag bicycle = new Tag("자전거");
            Tag car = new Tag("자동차");

            // 이벤트
            Tag event = new Tag("이벤트");

            List<Tag> categoryTag = new ArrayList<>();
            categoryTag.add(cultureTag);
            categoryTag.add(drinkTag);
            categoryTag.add(plantTag);
            categoryTag.add(extraTag);
            categoryTag.add(bookTag);
            categoryTag.add(rideTag);
            categoryTag.add(healthTag);
            categoryTag.add(foodTag);

            //나이
            em.persist(age20);
            em.persist(age30);
            em.persist(age40);
            em.persist(age50);
            em.persist(age60);
            em.persist(age70);

            //성별
            em.persist(woman);
            em.persist(man);

            //카테고리태그
            em.persist(foodTag);
            em.persist(healthTag);
            em.persist(cultureTag);
            em.persist(drinkTag);
            em.persist(plantTag);
            em.persist(extraTag);
            em.persist(bookTag);
            em.persist(rideTag);

            //치약, 화장품, 김 ,반찬 ,채소, 식료품
            em.persist(toothPaste);
            em.persist(cosmetic);
            em.persist(seaWeed);
            em.persist(banchan);
            em.persist(grocery);
            em.persist(vegetable);


            // 과일, 영양제
            em.persist(fruit);
            em.persist(supplements);

            // 과자, 빵, 계란, 샐러드
            em.persist(snack);
            em.persist(bread);
            em.persist(egg);
            em.persist(salad);

            // 술, 차, 커피, 우유
            em.persist(alcohol);
            em.persist(tea);
            em.persist(coffee);
            em.persist(milk);
            em.persist(water);

            // 영상, ott, 음악, 게임, 분석·데이터, 이모티콘
            em.persist(video);
            em.persist(ott);
            em.persist(music);
            em.persist(game);
            em.persist(analysis);
            em.persist(emoji);

            // 그림, 글
            em.persist(picture);
            em.persist(text);

            // 논문, eBook
            em.persist(paper);
            em.persist(eBook);
            em.persist(children);

            // 생리대, 양말, 반려동물, 방향제, 안경, 의류, 전자기기, 종합몰
            em.persist(sanitaryPad);
            em.persist(socks);
            em.persist(pet);
            em.persist(airFreshener);
            em.persist(glasses);
            em.persist(clothing);
            em.persist(electronicDevice);
            em.persist(mall);

            // 꽃
            em.persist(flower);

            // 자전거, 자동차
            em.persist(bicycle);
            em.persist(car);

            // 이벤트
            em.persist(event);



            //TODO subs등록

            // 식품1
            Subs realFruits = new Subs("리얼후르츠");
            realFruits.updateImage(subsImageUrl+"realFruits.png");
            realFruits.addCategory(food);
            realFruits.updateInfo("생산자와 소비자가 함께 나누고 싶은 리얼후르츠의 마음\n" +
                    "리얼후르츠는 바쁜 현대인들을 위한 건강한 푸드컬처를 만들어 가기 위해 노력합니다. 리얼후르츠는 기존 유통과정을 거치지 않고 과일산지와의 거래를 통하여, 농가와 소비자를 이을 수 있는 시스템을 지향하고 있습니다.\n" +
                    "이익과 편안함보다 정직한 가격과 까다로운 기준의 불편함을 추구하는 사람들. 건강하고 맛있는 과일을 공급하는 사람들.\n" +
                    "\n"+
                    "“우리는 리얼후르츠의 과일마스터입니다.”");

            realFruits.addTag(woman);
            realFruits.addTag(man);
            realFruits.addTag(age20);
            realFruits.addTag(age30);
            realFruits.addTag(age40);
            realFruits.addTag(foodTag);
            realFruits.addTag(fruit);


            SubsRank realFruitsDefault = new SubsRank("Single BOX", 35000, RankLevel.DEFAULT);
            SubsContent realFruitDefaultContent1 = new SubsContent("한달 한번 배송 기준");
            SubsContent realFruitDefaultContent2 = new SubsContent("사과 2/딸기 1팩/바나나 1/파인애플 1/메로골드 1/감귤 6-8");
            realFruitDefaultContent1.addSubsRank(realFruitsDefault);
            realFruitDefaultContent2.addSubsRank(realFruitsDefault);
            realFruitsDefault.addSubs(realFruits);

            SubsRank realFruitsPrime = new SubsRank("Double BOX", 45000, RankLevel.PRIME);
            SubsContent realFruitPrimeContent1 = new SubsContent("한달 한번 배송 기준");
            SubsContent realFruitPrimeContent2 = new SubsContent("사과 3/한라봉 2/망고 2/참외 2/청포도 1/바나나 1/파인애플 1/메로골드 1");
            realFruitPrimeContent1.addSubsRank(realFruitsPrime);
            realFruitPrimeContent2.addSubsRank(realFruitsPrime);
            realFruitsPrime.addSubs(realFruits);

            SubsRank realFruitsPremium = new SubsRank("Premium BOX", 65000, RankLevel.PREMIUM);
            SubsContent realFruitPremiumContent1 = new SubsContent("한달 한번 배송 기준");
            SubsContent realFruitPremiumContent2 = new SubsContent("사과 3/딸기 1팩/한라봉 1/체리 1팩/배 2/망고 2/아보카도 1/메론 1/파인애플 1/메로골드 1");
            realFruitPremiumContent1.addSubsRank(realFruitsPremium);
            realFruitPremiumContent2.addSubsRank(realFruitsPremium);
            realFruitsPremium.addSubs(realFruits);

            em.persist(realFruits);

            //식품2
            Subs jinMatGwa = new Subs("진맛과");
            jinMatGwa.updateImage(subsImageUrl+"jinMatGwa.png");
            jinMatGwa.addCategory(food);
            jinMatGwa.addTag(woman);
            jinMatGwa.addTag(man);
            jinMatGwa.addTag(age30);
            jinMatGwa.addTag(age40);
            jinMatGwa.addTag(age50);
            jinMatGwa.addTag(foodTag);
            jinMatGwa.addTag(fruit);

            jinMatGwa.updateInfo("대한민국 1% 명인과일 프리미엄 브랜드, 진짜 맛있는 과일\n" +
                    "진맛과는 과일을 좋아하고 즐겨 드시는 고객분들께 어떻게 하면 '항상 변함없이 신선하고 진짜 맛있는 과일을 드릴 수 있을까'라는 고민에서 시작했습니다.\n" +
                    "\n" +
                    "귀한 과일, 대한민국 1% 명인이 직접 생산하는 과일\n" +
                    "평생을 자식 기르듯 아침이슬 맞고 나가 살피고, 밤이슬 맞으며 보듬은 대한민국 1% 귀한 열매만을 선별했습니다. 타협하지 않는 고집과 특별한 노하우가 있기에 국가와 지자체로부터 인정받은 대한민국 과일 명인이 직접 기르는 과일만을 엄선합니다.\n" +
                    "맛있는 약속, 맛이 없으면 100% 환불\n" +
                    "우리 땅에서 정성껏 기른 국내산 프리미엄 과일입니다. 계절에 따라, 지역에 따라, 농장의 위치에 따라 각기 열매가 맺히는 시기도 다르고 맛도 달라집니다. 그렇기에 진맛과는 서두르지 않습니다. 가장 맛있는 시기를 아는 명인들이 수확한 과일을 고스란히 고객의 식탁 위에 올려드립니다. 이 자신감으로 맛이 없으면 100% 환불해드립니다.\n" +
                    "진심의 노력, 대한민국 최고의 과일을 찾기 귀한 준비과정 1,000일\n" +
                    "대한민국에서 가장 맛있는 과일을 찾아 전국 오지까지 찾아다닌 천일 간의 여정! 그 길 위에서 만난 수만여 곳의 농장! 명인이 생산한 과일을 맛보았던 그 기쁨의 순간! 고집스레 '안 한다' 고개를 절레절레 흔들던 명인들! 그들을 수십 번 찾아가 설득시켰던 인내의 순간! 우리의 진정 어린 시간들이 지금의 진맛과를 만들었습니다.\n" +
                    "\n" +
                    "진짜 맛있는 과일을 찾기 위해 3년간 10만 킬로 이상을 달리며 준비했습니다. 진맛과의 맛있는 여정은 오늘도 그리고 내일도 계속되고 있습니다.");

            SubsRank jindefaultRank = new SubsRank("진맛과 제철과일 구독서비스 (1회당 5만원)", 50000, RankLevel.DEFAULT);
            SubsContent jindefatulContent1 = new SubsContent("한달 한번 배송 기준");
            SubsContent jindefatulContent2 = new SubsContent("1회 배송당 3~4개 종류의 제철과일 구독서비스");
            jindefatulContent1.addSubsRank(jindefaultRank);
            jindefatulContent2.addSubsRank(jindefaultRank);
            jindefaultRank.addSubs(jinMatGwa);


            SubsRank jinPrimeRank = new SubsRank("진맛과 제철과일 구독서비스 (1회당 10만원)", 100000, RankLevel.PRIME);
            SubsContent jinPrimeContent1 = new SubsContent("한달 한번 배송 기준");
            SubsContent jinPrimeContent2 = new SubsContent("1회 배송당 4~5개 종류의 제철과일 구독서비스");
            jinPrimeContent1.addSubsRank(jinPrimeRank);
            jinPrimeContent2.addSubsRank(jinPrimeRank);
            jinPrimeRank.addSubs(jinMatGwa);

            em.persist(jinMatGwa);


            //식품3
            Subs lotteMonthSnack = new Subs("롯데웰푸드 스위트몰 월간과자");
            lotteMonthSnack.updateImage(subsImageUrl+"lotteMonthSnack.png");
            lotteMonthSnack.addCategory(food);
            lotteMonthSnack.addTag(woman);
            lotteMonthSnack.addTag(man);
            lotteMonthSnack.addTag(age20);
            lotteMonthSnack.addTag(foodTag);
            lotteMonthSnack.addTag(snack);

            lotteMonthSnack.updateInfo("아이들에게 어떤 간식을 줘야 하나 매번 고민 중이신가요? 과자 마니아인 당신, 신상 과자를 찾아 항상 헤매시나요?\n" +
                    "회사에서 일 할 때, 학교에서 공부할 때, 단 것이 먹고 싶지 않으셨나요?\n" +
                    "이럴 때 바로 필요한 건, 월간 과자를 소개합니다.\n" +
                    "\n" +
                    "매달 새로운 테마에 색다른 과자들! 매월 달라지는 재미있는 테마와 그에 맞는 과자로 새롭게 구성해주는 이색 과자 큐레이션 서비스입니다.");

            SubsRank lotteMonthSnackdefaultRank = new SubsRank("소확행팩", 9900, RankLevel.DEFAULT);
            SubsContent lotteMonthSnackdefatulContent1 = new SubsContent("나에게 선물하는 소확행 과자보따리!");
            lotteMonthSnackdefatulContent1.addSubsRank(lotteMonthSnackdefaultRank);
            lotteMonthSnackdefaultRank.addSubs(lotteMonthSnack);


            SubsRank lotteMonthSnackPrimeRank = new SubsRank("마니아팩", 19800, RankLevel.PRIME);
            SubsContent lotteMonthSnackPrimeContent1 = new SubsContent("소확행으로는 모자라! 난 마니아니까!");
            SubsContent lotteMonthSnackPrimeContent2 = new SubsContent("소확행팩 2배 + α");
            lotteMonthSnackPrimeContent1.addSubsRank(lotteMonthSnackPrimeRank);
            lotteMonthSnackPrimeContent2.addSubsRank(lotteMonthSnackPrimeRank);
            lotteMonthSnackPrimeRank.addSubs(lotteMonthSnack);
            em.persist(lotteMonthSnack);


            //식품4
            Subs lotteMonthBread = new Subs("롯데웰푸드 스위트몰 월간생빵");
            lotteMonthBread.updateImage(subsImageUrl+"lotteMonthBread.png");

            lotteMonthBread.addCategory(food);
            lotteMonthBread.addTag(woman);
            lotteMonthBread.addTag(man);
            lotteMonthBread.addTag(age20);
            lotteMonthBread.addTag(foodTag);
            lotteMonthBread.addTag(bread);

            lotteMonthBread.updateInfo("\"오늘은 무엇을 줄까?\" 가족들의 간식을 고민 중이신가요? \"밥보다 빵이 좋아!\" 항상 색다른 빵을 찾아 다니시나요?\n" +
                    "\"너무 바쁜데 밥은 또 뭘 먹지\" 정신없는 일상 속에 간단한 끼니 거리를 찾으시나요?\n" +
                    "\n" +
                    "이럴 때 바로 필요한 건! 매월 다른 빵을, 신제품까지, 신선하게! 매월 다른 구성으로, 다양한 빵들이 배송 되는 \"이색 빵 큐레이션 서비스\"입니다.");

            SubsRank lotteMonthBreadDefaultRank = new SubsRank("월간 생빵", 11900, RankLevel.DEFAULT);
            SubsContent lotteMonthBreadDefatulContent1 = new SubsContent("공장에서 방금 만들어 가장 맛있고 신선한 빵으로 배송해드립니다.");
            lotteMonthBreadDefatulContent1.addSubsRank(lotteMonthBreadDefaultRank);
            lotteMonthBreadDefaultRank.addSubs(lotteMonthBread);

            em.persist(lotteMonthBread);

            //식품5
            Subs rockSeaweed = new Subs("기역이미음 돌김");
            rockSeaweed.updateImage(subsImageUrl+"rockSeaweed.png");
            rockSeaweed.addCategory(food);
            rockSeaweed.addTag(woman);
            rockSeaweed.addTag(man);
            rockSeaweed.addTag(age20);
            rockSeaweed.addTag(foodTag);
            rockSeaweed.addTag(seaWeed);

            rockSeaweed.updateInfo("남해 청정지역에서 양식한 프리미엄 원초와 최고급 재료로 만들어진 조미김, 일월의돌김.\n" +
                    "기역이미음의 제품을 합리적인 가격에 가정의 생활패턴에 맞추어 정기적으로 받으시고, 새로운 제품에 대한 체험도 우선적으로 받아보세요.");

            SubsRank rockSeaweedDefaultRank = new SubsRank("일월의돌김 정기구매", 5900, RankLevel.DEFAULT);
            SubsContent rockSeaweedDefaultContent1 = new SubsContent("일월의돌김(5g)x20봉지");
            rockSeaweedDefaultContent1.addSubsRank(rockSeaweedDefaultRank);
            rockSeaweedDefaultRank.addSubs(rockSeaweed);

            em.persist(rockSeaweed);


            //식품6
            Subs monthBanchan = new Subs("현관앞키친 월간반찬");
            monthBanchan.updateImage(subsImageUrl+"monthBanchan.png");
            monthBanchan.addCategory(food);
            monthBanchan.addTag(woman);
            monthBanchan.addTag(man);
            monthBanchan.addTag(age20);
            monthBanchan.addTag(foodTag);
            monthBanchan.addTag(banchan);

            monthBanchan.updateInfo("반찬 고민 끝! 매일 아침 배송오는 다섯가지 가정식 반찬!\n" +
                    "매일 새로운 반찬으로 골고루, 반찬 걱정 없는 식사 준비!");

            SubsRank monthBanchanDefaultRank = new SubsRank("반찬 월간 정기구독", 79200, RankLevel.DEFAULT);
            SubsContent monthBanchanDefaultContent1 = new SubsContent("1일 1팩  다섯가지 반찬  현관 앞 새벽배송");
            SubsContent monthBanchanDefaultContent2 = new SubsContent("하루 9,900원, 주 2회 기준 가격입니다.");
            monthBanchanDefaultContent1.addSubsRank(monthBanchanDefaultRank);
            monthBanchanDefaultContent2.addSubsRank(monthBanchanDefaultRank);
            monthBanchanDefaultRank.addSubs(monthBanchan);

            em.persist(monthBanchan);

            //식품7
            Subs uglyUs = new Subs("어글리어스");
            uglyUs.updateImage(subsImageUrl+"uglyUs.png");
            uglyUs.addCategory(food);
            uglyUs.addTag(woman);
            uglyUs.addTag(man);
            uglyUs.addTag(age20);
            uglyUs.addTag(foodTag);
            uglyUs.addTag(grocery);

            uglyUs.updateInfo("합리적 가격, 선물같은 설렘. 어글리어스 채소박스.\n" +
                    "\n" +
                    "농산물의 폐기는 유통과 소비 모든 단계에서 사회적 손실을 야기합니다. 어글리어스는 불필요한 낭비를 줄여 환경 보호에 기여하고, 농부의 노력에 대한 정당한 보상을 제공합니다. 농산물의 품질은 외형이 아닌 신선함, 맛 그리고 건강한 생산 과정에 있다는 믿음으로 어글리어스가 탄생했습니다.\n" +
                    "판로를 잃은 농산물을 구출하고 폐기를 막습니다. 개성있는 외형으로 판로를 잃은 농산물, 일시적인 구조 문제로 판로를 잃은 농산물, 공급 과다로 생긴 잉여 농산물들을 산지에서 직접 수매해 합리적인 가격으로 제공합니다.\n" +
                    "친환경 생산의 확대로 지속가능한 땅을 늘려나갑니다. 생산부터 자연 생태계와 함께 스스로 거칠게 자라길 선택한 친환경 농산물들을 가치있게 여깁니다. 어글리어스가 모든 농가를 직접 방문하고 생산 환경을 확인해, 친환경 농산물과 소싱철학에 맞는 농산물들을 우선 소싱합니다.\n" +
                    "플라스틱 없는 포장, 친환경 패키징을 추구합니다. 농산물의 신선도를 지키는 선에서 최소한의 포장을 끊임없이 실험하고 실천합니다. 종이백과 펄프용기, 생분해 비닐을 사용하고 플라스틱은 지양합니다. 조금 번거롭더라도 우리의 땅과 식탁에 이로운 방향을 선택합니다.!");

            SubsRank uglyUsDefaultRank = new SubsRank("스탠다드 박스", 31000, RankLevel.DEFAULT);
            SubsContent uglyUsDefaultContent1 = new SubsContent("1~2인 가구를 위한 채소박스");
            SubsContent uglyUsDefaultContent2 = new SubsContent("구성 예시 : 로메인 100g, 파프리카 1~2개, 버섯 150g, 사과 1~2개, 애호박 1개, 양파 2개, 오이 1개, 대파 200g");
            SubsContent uglyUsDefaultContent3 = new SubsContent("레시피 페이퍼 제공 : 더 즐거운 채소 생활을 위해 레시피와 보관법을 함께 알려드려요.");
            SubsContent uglyUsDefaultContent4 = new SubsContent("한 달 두 번 기준 가격");
            uglyUsDefaultContent1.addSubsRank(uglyUsDefaultRank);
            uglyUsDefaultContent2.addSubsRank(uglyUsDefaultRank);
            uglyUsDefaultContent3.addSubsRank(uglyUsDefaultRank);
            uglyUsDefaultContent4.addSubsRank(uglyUsDefaultRank);
            uglyUsDefaultRank.addSubs(uglyUs);

            SubsRank uglyUsPrimeRank = new SubsRank("점보 박스", 50000, RankLevel.PRIME);
            SubsContent uglyUsPrimeContent1 = new SubsContent("3~4인 가구를 위한 채소박스");
            SubsContent uglyUsPrimeContent2 = new SubsContent("구성 예시 : 로메인 200g, 파프리카 2~3개, 버섯 300g, 사과 3~4개, 애호박 1개, 양파 5개, 오이 2개, 대파 400g");
            SubsContent uglyUsPrimeContent3 = new SubsContent("레시피 페이퍼 제공 : 더 즐거운 채소 생활을 위해 레시피와 보관법을 함께 알려드려요.");
            SubsContent uglyUsPrimeContent4 = new SubsContent("한 달 두 번 기준 가격");

            uglyUsPrimeContent1.addSubsRank(uglyUsPrimeRank);
            uglyUsPrimeContent2.addSubsRank(uglyUsPrimeRank);
            uglyUsPrimeContent3.addSubsRank(uglyUsPrimeRank);
            uglyUsPrimeContent4.addSubsRank(uglyUsPrimeRank);
            uglyUsPrimeRank.addSubs(uglyUs);

            em.persist(uglyUs);


            //식품8
            Subs sisterPack = new Subs("언니네텃밭 제철꾸러미");
            sisterPack.updateImage(subsImageUrl+"sisterPack.png");

            sisterPack.addCategory(food);
            sisterPack.addTag(woman);
            sisterPack.addTag(man);
            sisterPack.addTag(age20);
            sisterPack.addTag(foodTag);
            sisterPack.addTag(grocery);

            sisterPack.updateInfo("언니네텃밭 제철꾸러미는 여성농민들이 구성한 마을 공동체에서 직접 재배, 수확한 먹을거리를 도시 소비자 회원들과 함께 나누고자 만들어졌습니다. 여성농민은 소규모 텃밭에서 직접 생산한 먹을거리를 매주 1회 소비자 회원들께 보내드리고 소비자 회원은 매월 지정된 회비로 여성농민의 생산을 지원하고 있습니다.\n" +
                    "꾸러미를 통해 여성농민은 지속가능한 농사를 지을 수 있도록 생산비를 보장 받고 생태농업으로 전환하는 과정을 지원 받습니다. 소비자 회원은 건강하고 다양한 제철 먹을거리를 제공 받습니다. 언니네텃밭의 농사는 대규모, 기업형 농사로 망가진 땅을 살리고 종의 다양성을 지키는 농사로 지속가능한 농업 농촌 환경을 만들어가고 있습니다. 또한 생태 농업과 전통 농업의 복원을 위해 토종씨앗 농사를 이어가며 다음 세대의 건강한 먹을거리 생산기반까지 함께 만들어 갑니다.\n" +
                    "언니네텃밭은 얼굴있는 생산자와 마음을 알아주는 소비자가 소통과 협력을 통해 안전하고 지속가능한 먹을거리 생산소비 시스템을 만들어가는 공동체를 지향합니다. 따라서 생산과 소비의 안정성을 위해 회원제로 운영하고 있습니다. 생산자 회원과 소비자 회원은 공동체 방문, 소비자 모임 등 다양한 관계맺기를 통해 교류를 확대해 가고 있습니다.");

            SubsRank sisterPackDefaultRank = new SubsRank("1인꾸러미", 100000, RankLevel.DEFAULT);
            SubsContent sisterPackDefaultContent1 = new SubsContent("우리콩두부(1모), 유정란(4~6개), 제철채소(2~3종류), 농가공(밑반찬, 간식 2종류)");
            SubsContent sisterPackDefaultContent2 = new SubsContent("첫 회비 20000원의 가입비 추가");
            SubsContent sisterPackDefaultContent3 = new SubsContent("매주 받기 기준 가격 (꾸러미 개당 25000원)");
            sisterPackDefaultContent1.addSubsRank(sisterPackDefaultRank);
            sisterPackDefaultContent2.addSubsRank(sisterPackDefaultRank);
            sisterPackDefaultContent3.addSubsRank(sisterPackDefaultRank);
            sisterPackDefaultRank.addSubs(sisterPack);

            SubsRank sisterPackPrimeRank = new SubsRank("제철꾸러미", 120000, RankLevel.PRIME);
            SubsContent sisterPackPrimeContent1 = new SubsContent("우리콩두부(1모), 유정란(4~6개), 제철채소(2~3종류), 농가공(밑반찬, 간식 2종류)");
            SubsContent sisterPackPrimeContent2 = new SubsContent("첫 회비 20000원의 가입비 추가");
            SubsContent sisterPackPrimeContent3 = new SubsContent("매주 받기 기준 가격 (꾸러미 개당 25000원)");

            sisterPackPrimeContent1.addSubsRank(sisterPackPrimeRank);
            sisterPackPrimeContent2.addSubsRank(sisterPackPrimeRank);
            sisterPackPrimeContent3.addSubsRank(sisterPackPrimeRank);
            sisterPackPrimeRank.addSubs(sisterPack);


            SubsRank sisterPackPremiumRank = new SubsRank("요리뚝딱꾸러미", 33500, RankLevel.PREMIUM);
            SubsContent sisterPackPremiumContent1 = new SubsContent("공동체가 돌아가며 매월 다르게 준비하는 요리키트. 재료선별, 가공, 부재료와 양념, 레시피까지 언니들이 준비한 것으로 직접 조리해먹는 꾸러미");
            SubsContent sisterPackPremiumContent2 = new SubsContent("첫 회비 20000원의 가입비 추가");
            SubsContent sisterPackPremiumContent3 = new SubsContent("월 1회 발송 기준 가격 (꾸러미 개당 33500원)");

            sisterPackPremiumContent1.addSubsRank(sisterPackPremiumRank);
            sisterPackPremiumContent2.addSubsRank(sisterPackPremiumRank);
            sisterPackPremiumContent3.addSubsRank(sisterPackPremiumRank);
            sisterPackPremiumRank.addSubs(sisterPack);

            em.persist(sisterPack);

            //식품9
            Subs lotteMonthMeal = new Subs("롯데웰푸드 스위트몰 월간밥상");
            lotteMonthMeal.updateImage(subsImageUrl+"lotteMonthMeal.png");

            lotteMonthMeal.addCategory(food);
            lotteMonthMeal.addTag(woman);
            lotteMonthMeal.addTag(man);
            lotteMonthMeal.addTag(age20);
            lotteMonthMeal.addTag(foodTag);
            lotteMonthMeal.addTag(grocery);

            lotteMonthMeal.updateInfo("\"오늘 뭐 먹지? 배달? 배달비 비싸던데.. 퇴근하고 마트 가야겠다. 장 보고, 저녁 차리면 2시간은 기본...\"\n" +
                    "월간밥상이 해결해드릴게요!\n" +
                    "\n" +
                    "월 1회 집 앞 배송, 월마다 새로운 구성! 가정간편식 정기구독 서비스.\n" +
                    "매달 3가지 이상의 상품으로 구성되며, 정기 구독 시 평균 15% 할인율로 만나볼 수 있습니다! 매달 메뉴를 새롭게 큐레이션하여 집 앞으로 가정간편식을 배달해드려요.");

            SubsRank lotteMonthMealDefaultRank = new SubsRank("소확행팩", 29800, RankLevel.DEFAULT);
            SubsContent lotteMonthMealDefaultContent1 = new SubsContent("좋은 가격으로 행복하게 즐겨요!");
            lotteMonthMealDefaultContent1.addSubsRank(lotteMonthMealDefaultRank);
            lotteMonthMealDefaultRank.addSubs(lotteMonthMeal);

            SubsRank lotteMonthMealPrimeRank = new SubsRank("마니아팩", 49800, RankLevel.PRIME);
            SubsContent lotteMonthMealPrimeContent1 = new SubsContent("소확행팩 구성에 푸짐하게 더 얹었어요!");
            lotteMonthMealPrimeContent1.addSubsRank(lotteMonthMealPrimeRank);
            lotteMonthMealPrimeRank.addSubs(lotteMonthMeal);

            em.persist(lotteMonthMeal);


            //식품10
            Subs monthEgg = new Subs("월간계란");
            monthEgg.updateImage(subsImageUrl+"monthEgg.png");

            monthEgg.addCategory(food);
            monthEgg.addTag(woman);
            monthEgg.addTag(man);
            monthEgg.addTag(age20);
            monthEgg.addTag(foodTag);
            monthEgg.addTag(egg);

            monthEgg.updateInfo("월간계란은 프리미엄 계란을 정기적으로 배송해드리는 \"월 정기배송 서비스 소셜벤처\"입니다.\n" +
                    "구매해 주시는 계란의 알 수 10%를 어려운 사정에 있는 노인분들과 아이들에게 기부합니다.\n" +
                    "\n" +
                    "좋은 환경에서 자란 닭이 낳은 알.\n" +
                    "닭의 쾌적한 환경을 위해 유기농 왕겨를 일주일에 2번씩 뿌려주고 밤껍질을 도포하여 닭의 위장 건강과 냄새까지 잡아줍니다. 추가로 광합성 제재로 생활공간의 전반에 유용한 미생물을 공급해줍니다.");

            SubsRank monthEggDefaultRank = new SubsRank("무항생제 방사유정란 초란 생계란", 11900, RankLevel.DEFAULT);
            SubsContent monthEggDefaultContent1 = new SubsContent("무항생제 초란 방사 유정란, 난각번호 2번란 (30알)");
            monthEggDefaultContent1.addSubsRank(monthEggDefaultRank);
            monthEggDefaultRank.addSubs(monthEgg);

            em.persist(monthEgg);


            //식품11
            Subs pocketSalad = new Subs("포켓샐러드");
            pocketSalad.updateImage(subsImageUrl+"pocketSalad.png");

            pocketSalad.addCategory(food);
            pocketSalad.addTag(woman);
            pocketSalad.addTag(age20);
            pocketSalad.addTag(age30);
            pocketSalad.addTag(foodTag);
            pocketSalad.addTag(salad);

            pocketSalad.updateInfo("뜯어서 바로 먹는 간편한 포켓샐러드\n" +
                    "\n" +
                    "한 번의 주문으로 매주 나만의 식단 완성! 원하는 날짜와 구성으로 맞춤 식단관리 시작!\n" +
                    "진공 포장된 각각의 재료를 뜯어 들어있는 용기에 부어주세요. 세럭/손질이 되어있어 언제 어디서든 뜯어서 바로 먹을 수 있어요.\n" +
                    "포켓샐러드 만의 특별한 진공포장으로 신선함이 오래 가고 채소의 아삭함과 토핑의 촉촉함이 살아있어요.");

            SubsRank pocketSaladDefaultRank = new SubsRank("4주 정기배송 샐러드 주 3일", 55800, RankLevel.DEFAULT);
            SubsContent pocketSaladDefaultContent1 = new SubsContent("4주동안 주 1회 수령 (3팩씩 총 12팩)");
            SubsContent pocketSaladDefaultContent2 = new SubsContent("뜯어서 바로 먹는 간편한 포켓샐러드");
            SubsContent pocketSaladDefaultContent3 = new SubsContent("그린 샐러드로만 선택 시 금액");
            pocketSaladDefaultContent1.addSubsRank(pocketSaladDefaultRank);
            pocketSaladDefaultContent2.addSubsRank(pocketSaladDefaultRank);
            pocketSaladDefaultContent3.addSubsRank(pocketSaladDefaultRank);
            pocketSaladDefaultRank.addSubs(pocketSalad);


            SubsRank pocketSaladPrimeRank = new SubsRank("4주 정기배송 샐러드 주 6일", 110880, RankLevel.PRIME);
            SubsContent pocketSaladPrimeContent1 = new SubsContent("4주동안 주 1회 수령 (6팩씩 총 24팩)");
            SubsContent pocketSaladPrimeContent2 = new SubsContent("뜯어서 바로 먹는 간편한 포켓샐러드");
            SubsContent pocketSaladPrimeContent3 = new SubsContent("그린 샐러드로만 선택 시 금액");
            pocketSaladPrimeContent1.addSubsRank(pocketSaladPrimeRank);
            pocketSaladPrimeContent2.addSubsRank(pocketSaladPrimeRank);
            pocketSaladPrimeContent3.addSubsRank(pocketSaladPrimeRank);
            pocketSaladPrimeRank.addSubs(pocketSalad);

            em.persist(pocketSalad);

            //식품12
            Subs monthKimchi = new Subs("월간 농협김치맛선");
            monthKimchi.updateImage(subsImageUrl+"monthKimchi.png");


            monthKimchi.addCategory(food);
            monthKimchi.addTag(woman);
            monthKimchi.addTag(man);
            monthKimchi.addTag(age30);
            monthKimchi.addTag(age40);
            monthKimchi.addTag(age50);
            monthKimchi.addTag(age60);
            monthKimchi.addTag(foodTag);
            monthKimchi.addTag(banchan);

            monthKimchi.updateInfo("우리집이 김치맛집! 프리미엄 김치 구독 서비스, 월간 농협김치맛선\n" +
                    "자동 결제로 간편하게 매월 지정한 날짜에 한국농협김치가 문앞에 도착!\n" +
                    "\n" +
                    "산지와의 계약재배를 통해 최고 품질의 국산농산물만 엄선. 한국농협김치의 모든 김치는 산지와의 계약재배를 통해 100% 국산농산물을 원재료로 만들어 믿을 수 있습니다. 원료 안전성 검사와 HACCP(안전관리인증기증) 인증 자체 시설에서 청결하게 생산합니다.\n" +
                    "한국농협김치 특허기술로 개발한 비법양념. 한국농혐김치 R&D 식품연구소에서 특허기술로 개발한 표고버섯, 느타리버섯, 새송이버섯을 첨가한 비법양념과 농협김치전용 새우젓과 멸치액젓으로 건강한 감칠맛을 선사합니다.");

            SubsRank monthKimchiDefaultRank = new SubsRank("미니어처 (소확행 세트)", 15900, RankLevel.DEFAULT);
            SubsContent monthKimchiDefaultContent1 = new SubsContent("소용량 김치 10개입");
            SubsContent monthKimchiDefaultContent2 = new SubsContent("2가지 단품상품 중 택 1");
            monthKimchiDefaultContent1.addSubsRank(monthKimchiDefaultRank);
            monthKimchiDefaultContent2.addSubsRank(monthKimchiDefaultRank);
            monthKimchiDefaultRank.addSubs(monthKimchi);


            SubsRank monthKimchiPrimetRank = new SubsRank("취향저격 (너 하나로 충분해)", 26900, RankLevel.PRIME);
            SubsContent  monthKimchiPrimeContent1 = new SubsContent("대용량 단품김치 4종");
            SubsContent  monthKimchiPrimeContent2 = new SubsContent("소4가지 단품상품중 택 1");
            monthKimchiPrimeContent1.addSubsRank(monthKimchiPrimetRank);
            monthKimchiPrimeContent2.addSubsRank(monthKimchiPrimetRank);
            monthKimchiPrimetRank.addSubs(monthKimchi);

            em.persist(monthKimchi);



            //문화1
            Subs playStationPlus = new Subs("플레이스테이션 플러스");
            playStationPlus.updateImage(subsImageUrl+"playStationPlus.png");

            playStationPlus.addCategory(culture);
            playStationPlus.addTag(man);
            playStationPlus.addTag(age20);
            playStationPlus.addTag(age30);
            playStationPlus.addTag(cultureTag);
            playStationPlus.addTag(game);


            playStationPlus.updateInfo("PlayStation Network에서 사용 가능한 계정이 필요하고 해당 계정에 결제 수단이 등록되어 있어야 합니다.\n" +
                    "가입 시 PlayStation Plus 에센셜 플랜을 통해 최신 월간 게임을 즉시 이용할 수 있고, 스페셜 또는 디럭스 멤버는 전체 게임 카탈로그를 즐길 수 있습니다. PlayStation Plus 디럭스 멤버는 클래식 카탈로그에 액세스할 수 있습니다.");

            SubsRank playStationPlusDefaultRank = new SubsRank("에센셜", 7500, RankLevel.DEFAULT);
            SubsContent playStationPlusDefaultContent1 = new SubsContent("월간 게임, 온라인 멀티플레이 등을 즐기세요");
            SubsContent playStationPlusDefaultContent2 = new SubsContent("월간 게임/온라인 멀티플레이/독점 할인/독점 콘텐츠/클라우드 스토리지/셰어플레이/게임 도움말");
            playStationPlusDefaultContent1.addSubsRank(playStationPlusDefaultRank);
            playStationPlusDefaultContent2.addSubsRank(playStationPlusDefaultRank);
            playStationPlusDefaultRank.addSubs(playStationPlus);

            SubsRank playStationPlusPrimeRank = new SubsRank("디럭스", 12900, RankLevel.PRIME);
            SubsContent playStationPlusPrimeContent1 = new SubsContent("제한 없이 모두 엑세스");
            SubsContent playStationPlusPrimeContent2 = new SubsContent("스페셜의 모든 혜택 + 클래식 카탈로그/게임 체험판");
            playStationPlusPrimeContent1.addSubsRank(playStationPlusPrimeRank);
            playStationPlusPrimeContent2.addSubsRank(playStationPlusPrimeRank);
            playStationPlusPrimeRank.addSubs(playStationPlus);

            em.persist(playStationPlus);


            //문화2
            Subs ocssub = new Subs("옥썹 경매공매정보");
            ocssub.updateImage(subsImageUrl+"ocssub.png");

            ocssub.addCategory(culture);
            ocssub.addTag(man);
            ocssub.addTag(age40);
            ocssub.addTag(age50);
            ocssub.addTag(cultureTag);
            ocssub.addTag(analysis);


            ocssub.updateInfo("이제는 더이상 투자에 적합한 물건을 검색하기 위해 고생하지 않아도 됩니다.\n" +
                    "옥썹의 정기구독을 통해 회원들에게 우량한 물건을 엄선하여 소개하고 물건을 분석해 드립니다. 우량한 경매, 공매 추천 물건 정보와 실전 권리분석 정보를 매월 제공 받으실 수 있습니다.");

            SubsRank ocssubDefaultRank = new SubsRank("스탠다드", 29900, RankLevel.DEFAULT);
            SubsContent ocssubDefaultContent1 = new SubsContent("경매, 공매 추천물건 정보 매월 10건~15건");
            ocssubDefaultContent1.addSubsRank(ocssubDefaultRank);
            ocssubDefaultRank.addSubs(ocssub);

            SubsRank ocssubPrimeRank = new SubsRank("프리미엄", 48000, RankLevel.PRIME);
            SubsContent ocssubPrimeContent1 = new SubsContent("스탠다드 서비스 + 실전 권리분석 정보 매월 5건~10건");
            ocssubPrimeContent1.addSubsRank(ocssubPrimeRank);
            ocssubPrimeRank.addSubs(ocssub);

            em.persist(ocssub);


            //문화3
            Subs estatePlanet = new Subs("부동산플래닛");
            estatePlanet.updateImage(subsImageUrl+"estatePlanet.png");

            estatePlanet.addCategory(culture);
            estatePlanet.addTag(man);
            estatePlanet.addTag(age40);
            estatePlanet.addTag(age50);
            estatePlanet.addTag(cultureTag);
            estatePlanet.addTag(analysis);
            estatePlanet.addTag(event);


            estatePlanet.updateInfo("지도 상의 모든 가능성.\n" +
                    "원하는 형태 그대로 재개발, 재건축 충족 요건을 분석해주는 영역 그리기 기능은 실패없는 투자 의사결정을 돕는 가장 강력한 분석도구입니다.\n" +
                    "어떤 지역이든 한눈에 확인.\n" +
                    "개별공시지가 지도는 하나씩 조회하던 개별토지의 단위면적당 가격을 지도로 한 번에 보여주는 시각화 기능입니다.\n" +
                    "원하는 조건으로 건물 탐색.\n" +
                    "토지면적, 연면적, 층수, 건축연한 등 사용자가 입력한 상세 조건에 맞는 건물 정보를 탐색하고 목록을 확인할 수 있습니다.\n" +
                    "전국 부동산 거래특성 보고서.\n" +
                    "월별/분기별 ‘전국 부동산유형별 거래 특성’보고서를 담고 있는 마켓 리포트를 무제한 다운로드할 수 있습니다.\n" +
                    "\n" +
                    "계속 추가되고 강력해지는 탐색 플러스!\n" +
                    "노후도 분석 기능을 넘어 AI 실거래 추정가 예측 기능, 개별공시지가 지도 등 더욱 강력한 분석 기능을 멤버십 하나로 모두 사용할 수 있습니다.");

            SubsRank estatePlanetDefaultRank = new SubsRank("탐색 플러스 월간 구독권", 9900, RankLevel.DEFAULT);
            SubsContent estatePlanetDefaultContent1 = new SubsContent("첫구독 회원 첫달 60일 이용 혜택");
            SubsContent estatePlanetDefaultContent2 = new SubsContent("무약정, 무위약금, 언제든 해지");
            SubsContent estatePlanetDefaultContent3 = new SubsContent("첫구독 회원 1개월 추가");
            estatePlanetDefaultContent1.addSubsRank(estatePlanetDefaultRank);
            estatePlanetDefaultContent2.addSubsRank(estatePlanetDefaultRank);
            estatePlanetDefaultContent3.addSubsRank(estatePlanetDefaultRank);
            estatePlanetDefaultRank.addSubs(estatePlanet);

            em.persist(estatePlanet);

            //문화4
            Subs nintendoSwitch = new Subs("닌텐도 스위치 온라인");
            nintendoSwitch.updateImage(subsImageUrl+"nintendoSwitch.png");

            nintendoSwitch.addCategory(culture);
            nintendoSwitch.addTag(man);
            nintendoSwitch.addTag(age20);
            nintendoSwitch.addTag(age30);
            nintendoSwitch.addTag(cultureTag);
            nintendoSwitch.addTag(game);


            nintendoSwitch.updateInfo("멀리 있는 친구와, 전 세계의 라이벌과, 대전·협력 플레이.\n" +
                    "『모여봐요 동물의 숲』과 『마리오 카트 8 디럭스』 등 온라인 플레이 대응 소프트웨어를 통해 멀리 있는 친구들이나 전 세계의 라이벌과 대전이나 협력 플레이를 즐길 수 있습니다.\n" +
                    "\n" +
                    "NES™ & Super NES™ & Game Boy™ - Nintendo Switch Online\n" +
                    "여러 가지 추억의 게임을 모아 놓은 Nintendo Switch 전용 소프트웨어. 수록 타이틀은 계속해서 추가됩니다. 많은 타이틀 중에서 간편하게 선택하여 바로 즐길 수 있습니다. 또한 여러 장소에 가져가서 컨트롤러를 나눠 잡고 플레이하거나 친구와 온라인 플레이를 할 수 있습니다.\n" +
                    "\n" +
                    "저장 데이터를 자동으로 맡겨 두기 때문에 만일의 상황에도 안심.\n" +
                    "대응 소프트웨어의 저장 데이터를 자동으로 인터넷을 통해 맡겨 두고 소중하게 보관합니다. 만일의 경우에도 맡겨 두었던 저장 데이터는 언제라도 어느 본체에서라도 다운로드할 수 있습니다.\n" +
                    "\n" +
                    "플레이어와 대화할 수 있는, 스마트폰과 게임이 연동되는 보이스챗.\n" +
                    "온라인 플레이에서 편하게 보이스챗이 가능한 전용 앱을 이용할 수 있습니다. 예를 들면 『모여봐요 동물의 숲』과 『마리오 카트 8 디럭스』 등이 대응합니다. 게임과 연동한 특별한 기능도 있습니다.");

            SubsRank nintendoSwitchDefaultRank = new SubsRank("Nintendo Switch Online", 1660, RankLevel.DEFAULT);
            SubsContent nintendoSwitchDefaultContent1 = new SubsContent("온라인 플레이 / NES™ & Super NES™ & Game Boy™ / 저장 데이터 맡기기 / 스마트폰용 앱 / 가입자 한정 특전");
            SubsContent nintendoSwitchDefaultContent2 = new SubsContent("12개월 결제 기준 한 달 금액입니다.(12개월간 19900원)");
            nintendoSwitchDefaultContent1.addSubsRank(nintendoSwitchDefaultRank);
            nintendoSwitchDefaultContent2.addSubsRank(estatePlanetDefaultRank);
            nintendoSwitchDefaultRank.addSubs(nintendoSwitch);

            em.persist(nintendoSwitch);

            //문화5
            Subs netflix = new Subs("넷플릭스");
            netflix.updateImage(subsImageUrl+"netflix.png");

            netflix.addCategory(culture);
            netflix.addTag(man);
            netflix.addTag(woman);
            netflix.addTag(age20);
            netflix.addTag(age30);
            netflix.addTag(age40);
            netflix.addTag(cultureTag);
            netflix.addTag(ott);


            netflix.updateInfo("영화, 시리즈 등을 무제한으로 어디서나 자유롭게 시청하세요. 해지는 언제든 가능합니다.\n" +
                    "\n" +
                    "넷플릭스는 각종 수상 경력에 빛나는 시리즈, 영화, 애니메이션, 다큐멘터리 등 다양한 콘텐츠를 인터넷 연결이 가능한 수천 종의 디바이스에서 시청할 수 있는 스트리밍 서비스입니다.  저렴한 월 요금으로 원하는 시간에 원하는 만큼 즐길 수 있습니다. 무궁무진한 콘텐츠가 준비되어 있으며 매주 새로운 시리즈와 영화가 제공됩니다.\n" +
                    "넷플릭스는 장편 영화, 다큐멘터리, 시리즈, 애니메이션, 각종 상을 수상한 넷플릭스 오리지널 등 수많은 콘텐츠를 확보하고 있습니다. 마음에 드는 콘텐츠를 원하는 시간에 원하는 만큼 시청하실 수 있습니다.");

            SubsRank netflixDefaultRank = new SubsRank("베이식", 9500, RankLevel.DEFAULT);
            SubsContent netflixDefaultContent1 = new SubsContent("광고 없이 영화, 시리즈, 모바일 게임 무제한 이용");
            SubsContent netflixDefaultContent2 = new SubsContent("지원되는 디바이스에서 동시접속 1명까지 시청 가능");
            SubsContent netflixDefaultContent3 = new SubsContent("HD로 시청 가능");
            SubsContent netflixDefaultContent4 = new SubsContent("한 번에 1대의 지원되는 디바이스에서 동시에 콘텐츠 저장 가능");
            netflixDefaultContent1.addSubsRank(netflixDefaultRank);
            netflixDefaultContent2.addSubsRank(netflixDefaultRank);
            netflixDefaultContent3.addSubsRank(netflixDefaultRank);
            netflixDefaultContent4.addSubsRank(netflixDefaultRank);
            netflixDefaultRank.addSubs(netflix);

            SubsRank netflixPrimeRank = new SubsRank("스탠다드", 9500, RankLevel.PRIME);
            SubsContent netflixPrimeContent1 = new SubsContent("광고 없이 영화, 시리즈, 모바일 게임 무제한 이용");
            SubsContent netflixPrimeContent2 = new SubsContent("지원되는 디바이스에서 동시접속 2명까지 시청 가능");
            SubsContent netflixPrimeContent3 = new SubsContent("풀 HD로 시청 가능");
            SubsContent netflixPrimeContent4 = new SubsContent("한 번에 2대의 지원되는 디바이스에서 콘텐츠 저장 가능");
            SubsContent netflixPrimeContent5 = new SubsContent("함께 살지 않는 사람 1명을 추가 회원으로 등록할 수 있는 옵션 제공");
            netflixPrimeContent1.addSubsRank(netflixPrimeRank);
            netflixPrimeContent2.addSubsRank(netflixPrimeRank);
            netflixPrimeContent3.addSubsRank(netflixPrimeRank);
            netflixPrimeContent4.addSubsRank(netflixPrimeRank);
            netflixPrimeContent5.addSubsRank(netflixPrimeRank);
            netflixPrimeRank.addSubs(netflix);

            SubsRank netflixPremiumRank = new SubsRank("프리미엄", 17000, RankLevel.PREMIUM);
            SubsContent netflixPremiumContent1 = new SubsContent("광고 없이 영화, 시리즈, 모바일 게임 무제한 이용");
            SubsContent netflixPremiumContent2 = new SubsContent("지원되는 디바이스에서 동시접속 4명까지 시청 가능");
            SubsContent netflixPremiumContent3 = new SubsContent("UHD로 시청 가능");
            SubsContent netflixPremiumContent4 = new SubsContent("한 번에 6대의 지원되는 디바이스에서 콘텐츠 저장 가능");
            SubsContent netflixPremiumContent5 = new SubsContent("함께 살지 않는 사람을 최대 2명 추가 회원으로 등록할 수 있는 옵션 제공");
            SubsContent netflixPremiumContent6 = new SubsContent("넷플릭스 공간 음향");
            netflixPremiumContent1.addSubsRank(netflixPremiumRank);
            netflixPremiumContent2.addSubsRank(netflixPremiumRank);
            netflixPremiumContent3.addSubsRank(netflixPremiumRank);
            netflixPremiumContent4.addSubsRank(netflixPremiumRank);
            netflixPremiumContent5.addSubsRank(netflixPremiumRank);
            netflixPremiumContent6.addSubsRank(netflixPremiumRank);
            netflixPremiumRank.addSubs(netflix);

            em.persist(netflix);


            //문화6
            Subs tving = new Subs("티빙");
            tving.updateImage(subsImageUrl+"tving.png");

            tving.addCategory(culture);
            tving.addTag(man);
            tving.addTag(woman);
            tving.addTag(age20);
            tving.addTag(age30);
            tving.addTag(age40);
            tving.addTag(cultureTag);
            tving.addTag(ott);


            tving.updateInfo("티빙 오리지널부터 드라마·예능, 영화, 해외시리즈까지! 무제한으로 스트리밍해 보세요.\n" +
                    "\n" +
                    "티빙 오리지널 시리즈. 오직 티빙에서만 만날 수 있는 오리지널 콘텐츠를 감상해 보세요.\n" +
                    "요즘 뜨는 모든 콘텐츠. tvN·JTBC·Mnet 프로그램, 영화, 해외 시리즈, 파라마운트+ 오리지널 및 독점 시리즈.\n" +
                    "언제 어디서나 본방 사수. 본방 시작 5분 만에 Quick VOD로 빠르게 다시 보고, 놓친 방송은 타임머신으로 돌려보세요.\n" +
                    "원하는 기기로 자유롭게 감상. 스마트폰, 태블릿, PC, TV까지! 최대 4인 멀티 프로필로 가족, 친구와 함께 감상해 보세요.");

            SubsRank tvingDefaultRank = new SubsRank("베이직", 9500, RankLevel.DEFAULT);
            SubsContent tvingDefaultContent1 = new SubsContent("모든 콘텐츠, 모든 이용기기 가능");
            SubsContent tvingDefaultContent2 = new SubsContent("프로필 개수 4개");
            SubsContent tvingDefaultContent3 = new SubsContent("동시접속 1대");
            SubsContent tvingDefaultContent4 = new SubsContent("최대 화질 720p");
            SubsContent tvingDefaultContent5 = new SubsContent("콘텐츠 다운로드 200회");
            SubsContent tvingDefaultContent6 = new SubsContent("퀵 VOD/타임머신 가능");

            tvingDefaultContent1.addSubsRank(tvingDefaultRank);
            tvingDefaultContent2.addSubsRank(tvingDefaultRank);
            tvingDefaultContent3.addSubsRank(tvingDefaultRank);
            tvingDefaultContent4.addSubsRank(tvingDefaultRank);
            tvingDefaultContent5.addSubsRank(tvingDefaultRank);
            tvingDefaultContent6.addSubsRank(tvingDefaultRank);

            tvingDefaultRank.addSubs(tving);

            SubsRank tvingPrimeRank = new SubsRank("스탠다드", 13500, RankLevel.PRIME);
            SubsContent tvingPrimeContent1 = new SubsContent("모든 콘텐츠, 모든 이용기기 가능");
            SubsContent tvingPrimeContent2 = new SubsContent("프로필 개수 4개");
            SubsContent tvingPrimeContent3 = new SubsContent("동시접속 2대");
            SubsContent tvingPrimeContent4 = new SubsContent("최대 화질 1080p");
            SubsContent tvingPrimeContent5 = new SubsContent("콘텐츠 다운로드 300회");
            SubsContent tvingPrimeContent6 = new SubsContent("퀵 VOD/타임머신 가능");

            tvingPrimeContent1.addSubsRank(tvingPrimeRank);
            tvingPrimeContent2.addSubsRank(tvingPrimeRank);
            tvingPrimeContent3.addSubsRank(tvingPrimeRank);
            tvingPrimeContent4.addSubsRank(tvingPrimeRank);
            tvingPrimeContent5.addSubsRank(tvingPrimeRank);
            tvingPrimeContent6.addSubsRank(tvingPrimeRank);

            tvingPrimeRank.addSubs(tving);

            SubsRank tvingPremiumRank = new SubsRank("프리미엄", 17000, RankLevel.PREMIUM);
            SubsContent tvingPremiumContent1 = new SubsContent("모든 콘텐츠, 모든 이용기기 가능");
            SubsContent tvingPremiumContent2 = new SubsContent("프로필 개수 4개");
            SubsContent tvingPremiumContent3 = new SubsContent("동시접속 4대");
            SubsContent tvingPremiumContent4 = new SubsContent("최대 화질 1080p + 4K");
            SubsContent tvingPremiumContent5 = new SubsContent("콘텐츠 다운로드 400회");
            SubsContent tvingPremiumContent6 = new SubsContent("퀵 VOD/타임머신 가능");

            tvingPremiumContent1.addSubsRank(tvingPremiumRank);
            tvingPremiumContent2.addSubsRank(tvingPremiumRank);
            tvingPremiumContent3.addSubsRank(tvingPremiumRank);
            tvingPremiumContent4.addSubsRank(tvingPremiumRank);
            tvingPremiumContent5.addSubsRank(tvingPremiumRank);
            tvingPremiumContent6.addSubsRank(tvingPremiumRank);

            tvingPremiumRank.addSubs(tving);

            em.persist(tving);


            //문화6
            Subs watch = new Subs("왓챠");
            watch.updateImage(subsImageUrl+"watch.png");

            watch.addCategory(culture);
            watch.addTag(man);
            watch.addTag(woman);
            watch.addTag(age20);
            watch.addTag(age30);
            watch.addTag(age40);
            watch.addTag(cultureTag);
            watch.addTag(ott);


            watch.updateInfo("발견의 기쁨, 왓챠!\n" +
                    "\n" +
                    "상상할 수 있는 모든 콘텐츠 \n" +
                    "엥? 이게 있다고? 네, 왓챠에는 있습니다. 영화, 드라마는 물론 애니메이션, 예능, 다큐멘터리등의 10만여 편의 콘텐츠와 다양한 취향의 발견 왓챠 오리지널, 오직 왓챠에서만 볼 수 있는 왓챠 익스클루시브까지!\n" +
                    "\n" +
                    "내 취향에 딱 맞는 추천\n" +
                    "수많은 콘텐츠 중 내가 가장 재밌게 볼 콘텐츠는? 6억 5천만개의 별점을 바탕으로 당신의 취향에 맞는 콘텐츠만 엄선하여 추천합니다. 이 작품이 재밌을지 모르겠다구요? 내 예상 별점을 확인해 보세요. \n" +
                    "\n" +
                    "언제 어디서나 쉽고 편한 감상\n" +
                    "스마트폰, 태블릿, 스마트 TV, PC, 크롬캐스트, 안드로이드 TV, 애플 TV, PS5, 모든 기기에서 편리하게 감상할 수 있어요. 스트리밍 뿐만 아니라 다운로드 및 오프라인 재생 기능까지! \n" +
                    "\n" +
                    "최고의 화질로 동시 재생 4대까지!\n" +
                    "Ultra HD부터 4K까지 최고의 화질로 감상하세요. 최대 4개 기기까지 동시에 재생이 가능합니다.");

            SubsRank watchDefaultRank = new SubsRank("베이직", 7900, RankLevel.DEFAULT);
            SubsContent watchDefaultContent1 = new SubsContent("최대 1대 동시 감상");
            SubsContent watchDefaultContent2 = new SubsContent("Full HD 지원");
            SubsContent watchDefaultContent3 = new SubsContent("비디오 5개 저장");
            SubsContent watchDefaultContent4 = new SubsContent("모바일, 태블릿, PC, TV 지원");


            watchDefaultContent1.addSubsRank(watchDefaultRank);
            watchDefaultContent2.addSubsRank(watchDefaultRank);
            watchDefaultContent3.addSubsRank(watchDefaultRank);
            watchDefaultContent4.addSubsRank(watchDefaultRank);
            watchDefaultRank.addSubs(watch);

            SubsRank watchPrimeRank = new SubsRank("프리미엄", 12900, RankLevel.PRIME);
            SubsContent watchPrimeContent1 = new SubsContent("최대 4대 동시 감상");
            SubsContent watchPrimeContent2 = new SubsContent("Ultra HD 4K 지원");
            SubsContent watchPrimeContent3 = new SubsContent("HDR 10+ 지원");
            SubsContent watchPrimeContent4 = new SubsContent("비디오 100개 저장");
            SubsContent watchPrimeContent5 = new SubsContent("모바일, 태블릿, PC, TV 지원");


            watchPrimeContent1.addSubsRank(watchPrimeRank);
            watchPrimeContent2.addSubsRank(watchPrimeRank);
            watchPrimeContent3.addSubsRank(watchPrimeRank);
            watchPrimeContent4.addSubsRank(watchPrimeRank);
            watchPrimeContent5.addSubsRank(watchPrimeRank);
            watchPrimeRank.addSubs(watch);

            em.persist(watch);



            //문화7
            Subs melon = new Subs("멜론");
            melon.updateImage(subsImageUrl+"melon.png");

            melon.addCategory(culture);
            melon.addTag(man);
            melon.addTag(woman);
            melon.addTag(age20);
            melon.addTag(age30);
            melon.addTag(cultureTag);
            melon.addTag(music);


            melon.updateInfo("대한민국 No.1 음악 플랫폼 멜론\n" +
                    "\n" +
                    "대한민국 No.1 음악 플랫폼으로서 음악 경험의 기준을 만들어 갑니다.\n" +
                    "음악 트렌드의 중심 멜론차트부터 3,300만 유저의 빅데이터에 기반한 개인화 음악 추천까지, 유저에 대한 깊은 이해를 바탕으로 감동적인 음악 경험을 제공하기 위해 끊임없이 노력합니다.");

            SubsRank melonDefaultRank = new SubsRank("스트리밍 클럽 정기결제", 7900, RankLevel.DEFAULT);
            SubsContent melonDefaultContent1 = new SubsContent("기기 제한 없이 무제한 듣기 서비스가 제공되는 이용권입니다.");
            melonDefaultContent1.addSubsRank(melonDefaultRank);
            melonDefaultRank.addSubs(melon);

            SubsRank melonPrimeRank = new SubsRank("스트리밍 플러스 정기결제", 10900, RankLevel.PRIME);
            SubsContent melonPrimeContent1 = new SubsContent("온라인과 오프라인에서 무제한 듣기가 가능한 이용권입니다.");
            SubsContent melonPrimeContent2 = new SubsContent("오프라인 파일의 저장 및 재생은 인증된 모바일 디바이스 1대에서 가능하며, 오프라인 재생 기능을 통해 데이터 연결 없이 음악을 감상할 수 있습니다.");
            melonPrimeContent1.addSubsRank(melonPrimeRank);
            melonPrimeContent2.addSubsRank(melonPrimeRank);
            melonPrimeRank.addSubs(melon);

            SubsRank melonPremiumRank = new SubsRank("MP3 30 플러스 정기결제", 10900, RankLevel.PREMIUM);
            SubsContent melonPremiumContent1 = new SubsContent("무제한 듣기 서비스와 MP3 30곡 다운로드 서비스가 제공되는 이용권입니다.");
            melonPremiumContent1.addSubsRank(melonPremiumRank);
            melonPremiumRank.addSubs(melon);

            em.persist(melon);

            //문화8
            Subs flo = new Subs("FLO 뮤직");
            flo.updateImage(subsImageUrl+"flo.png");
            flo.addCategory(culture);
            flo.addTag(man);
            flo.addTag(woman);
            flo.addTag(age20);
            flo.addTag(age30);
            flo.addTag(cultureTag);
            flo.addTag(music);


            flo.updateInfo("지금 당신의 음악, FLO\n" +
                    "\n" +
                    "쓰면 쓸수록 나와 점점 닮아가는 FLO 뮤직. 인기순으로 듣는 것이 아닌 나만의 취향을 채우는 FLO 뮤직.\n" +
                    "똑같은 음악에 질리셨나요? FLO는 매일 내게 맞춘 음악을 배달합니다. FLO에서 내 맘에 쏙 드는 곡을 발견하세");

            SubsRank floDefaultRank = new SubsRank("모바일 무제한 듣기", 6900, RankLevel.DEFAULT);
            SubsContent floDefaultContent1 = new SubsContent("모바일에서만 듣는다면 스마트하게");
            floDefaultContent1.addSubsRank(floDefaultRank);
            floDefaultRank.addSubs(flo);

            SubsRank floPrimetRank = new SubsRank("무제한 듣기", 7900, RankLevel.PRIME);
            SubsContent floPrimeContent1 = new SubsContent("모바일, PC, NUGU 모든 기기 무제한");
            floPrimeContent1.addSubsRank(floPrimetRank);
            floPrimetRank.addSubs(flo);

            SubsRank floPremiumtRank = new SubsRank("무제한 듣기+오프라인 재생", 10900, RankLevel.PRIME);
            SubsContent floPremiumContent1 = new SubsContent("오프라인 재생 가능, 소장하고 싶다면");
            floPremiumContent1.addSubsRank(floPremiumtRank);
            floPremiumtRank.addSubs(flo);

            em.persist(flo);

            //문화9
            Subs bux = new Subs("벅스");
            bux.updateImage(subsImageUrl+"bux.png");
            bux.addCategory(culture);
            bux.addTag(man);
            bux.addTag(woman);
            bux.addTag(age20);
            bux.addTag(age30);
            bux.addTag(cultureTag);
            bux.addTag(music);


            bux.updateInfo("음악에 대한 다양한 가치를 발견하고, 발전시키는 사람들이 모인 곳, BUGS!\n" +
                    "\n" +
                    "나를 위한 플리, 벅스.\n" +
                    "마음을 담은 추천 플레이리스트 그리고 일상을 감성으로 가득 채워줄 essential;까지. 벅스는 감각적인 큐레이션을 잘 하는 음악 전문 서비스입니다.");

            SubsRank buxDefaultRank = new SubsRank("모바일 무제한 듣기", 6900, RankLevel.DEFAULT);
            SubsContent buxDefaultContent1 = new SubsContent("모바일에서만 듣는다면 스마트하게");
            buxDefaultContent1.addSubsRank(buxDefaultRank);
            buxDefaultRank.addSubs(bux);

            SubsRank buxPrimetRank = new SubsRank("무제한 듣기", 7900, RankLevel.PRIME);
            SubsContent buxPrimeContent1 = new SubsContent("모든 기기에서 감상 가능");
            buxPrimeContent1.addSubsRank(buxPrimetRank);
            buxPrimetRank.addSubs(bux);

            SubsRank buxPremiumtRank = new SubsRank("Premium 무제한 듣기", 12000, RankLevel.PRIME);
            SubsContent buxPremiumContent1 = new SubsContent("오프라인 재생 가능, 소장하고 싶다면");
            buxPremiumContent1.addSubsRank(buxPremiumtRank);
            buxPremiumtRank.addSubs(bux);

            em.persist(bux);

            //문화10
            Subs appleMusic = new Subs("애플 뮤직");
            appleMusic.updateImage(subsImageUrl+"appleMusic.png");

            appleMusic.addCategory(culture);
            appleMusic.addTag(man);
            appleMusic.addTag(woman);
            appleMusic.addTag(age20);
            appleMusic.addTag(age30);
            appleMusic.addTag(age40);
            appleMusic.addTag(cultureTag);
            appleMusic.addTag(music);
            appleMusic.addTag(event);


            appleMusic.updateInfo("새롭게 사운드에 사로잡히다.\n" +
                    "\n" +
                    "1억 곡 이상의 음악 및 30,000개 이상의 플레이리스트, 언제나 광고 없이.\n" +
                    "온몸을 휘감는 사운드를 선사하는 공간 음향 경험.\n" +
                    "Apple Music Sing과 함께, 실시간으로 뜨는 가사 보며 노래 따라 부르기.\n" +
                    "온라인에서든 오프라인에서든, 당신의 모든 기기에서.");

            SubsRank appleMusicDefaultRank = new SubsRank("개인", 6900, RankLevel.DEFAULT);
            SubsContent appleMusicDefaultContent1 = new SubsContent("1억 곡의 음악 및 전문가가 엄선한 30,000개 이상의 플레이리스트");
            SubsContent appleMusicDefaultContent2 = new SubsContent("Siri에게 간단히 요청하거나 ‘타이핑으로 Siri 사용’을 이용해 모든 노래, 앨범, 플레이리스트 또는 스테이션 재생");
            SubsContent appleMusicDefaultContent3 = new SubsContent("광고 없는 스트리밍");
            SubsContent appleMusicDefaultContent4 = new SubsContent("오리지널 쇼, 콘서트, 독점 콘텐츠, 아티스트가 진행하는 생방송 및 온디맨드 라디오 스테이션");
            SubsContent appleMusicDefaultContent5 = new SubsContent("Apple 기기/다른 지원 기기에서 감상 가능");
            SubsContent appleMusicDefaultContent6 = new SubsContent("Dolby Atmos 지원 공간 음향");
            SubsContent appleMusicDefaultContent7 = new SubsContent("무손실 오디오로 즐기는 전체 카탈로그");
            SubsContent appleMusicDefaultContent8 = new SubsContent("비트별 가사를 지원하고 보컬 설정 조절이 가능한 Apple Music Sing");
            SubsContent appleMusicDefaultContent9 = new SubsContent("보관함에 10만 곡 다운로드 가능, 당신의 전체 보관함을 어느 기기에서나 감상, 온라인에서도 오프라인에서도 맘껏");
            SubsContent appleMusicDefaultContent10 = new SubsContent("친구가 감상 중인 음악 보기");
            SubsContent appleMusicDefaultContent11= new SubsContent("신규 가입자는 Apple Music 1개월 무료");

            appleMusicDefaultContent1.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent2.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent3.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent4.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent5.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent6.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent7.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent8.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent9.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent10.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultContent11.addSubsRank(appleMusicDefaultRank);
            appleMusicDefaultRank.addSubs(appleMusic);

            SubsRank appleMusicPrimeRank = new SubsRank("가족", 7900, RankLevel.PRIME);
            SubsContent appleMusicPrimeContent1 = new SubsContent("개인 요금제의 모든 혜택");
            SubsContent appleMusicPrimeContent2 = new SubsContent("최대 6명까지 무제한 사용 가능");
            SubsContent appleMusicPrimeContent3 = new SubsContent("각 가족 구성원별 개인 음악 보관함");
            SubsContent appleMusicPrimeContent4 = new SubsContent("각 가족 구성원별 개인 맞춤 음악 추천");
            SubsContent appleMusicPrimeContent5 = new SubsContent("신규 가입자는 Apple Music 1개월 무료");
            appleMusicPrimeContent1.addSubsRank(appleMusicPrimeRank);
            appleMusicPrimeContent2.addSubsRank(appleMusicPrimeRank);
            appleMusicPrimeContent3.addSubsRank(appleMusicPrimeRank);
            appleMusicPrimeContent4.addSubsRank(appleMusicPrimeRank);
            appleMusicPrimeContent5.addSubsRank(appleMusicPrimeRank);
            appleMusicPrimeRank.addSubs(appleMusic);

            em.persist(appleMusic);


            //문화11
            Subs youtubePremium = new Subs("유튜브 프리미엄");
            youtubePremium.updateImage(subsImageUrl+"youtubePremium.png");

            youtubePremium.addCategory(culture);
            youtubePremium.addTag(man);
            youtubePremium.addTag(woman);
            youtubePremium.addTag(age20);
            youtubePremium.addTag(age30);
            youtubePremium.addTag(age40);
            youtubePremium.addTag(cultureTag);
            youtubePremium.addTag(video);
            youtubePremium.addTag(event);


            youtubePremium.updateInfo("새YouTube와 YouTube Music에서 광고로 끊김 없는 감상, 오프라인 저장, 그리고 백그라운드 재생\n" +
                    "\n" +
                    "광고 없는 무제한 동영상\n" +
                    " 광고가 끝나기를 기다릴 필요 없이 좋아하는 동영상에 마음껏 빠져 보세요. 광고로 끊김 없이 유용한 노하우를 찾거나, 새로운 레시피를 시도하거나, 좋아하는 크리에이터와 함께 운동해 보세요.\n" +
                    "\n" +
                    "오프라인으로 동영상 즐기기\n" +
                    "언제 어디서나 시청해 보세요. 동영상을 오프라인 저장하여 모바일 데이터나 Wi-Fi 없이도 언제 어디서나 시청할 수 있습니다.\n" +
                    "\n" +
                    "백그라운드 재0생\n" +
                    "화면이 꺼진 상태나 다른 앱을 사용하는 중에도 재생을 중단할 필요 없이 끊김 없이 백그라운드에서 동영상을 계속 재생할 수 있습니다.");

            SubsRank youtubePremiumDefaultRank = new SubsRank("개인", 10450, RankLevel.DEFAULT);
            SubsContent youtubePremiumDefaultContent1 = new SubsContent("YouTube에서 광고로 끊김 없는 동영상 시청 가능");
            SubsContent youtubePremiumDefaultContent2 = new SubsContent("동영상을 오프라인 저장하여 오프라인으로 시청 가능");
            SubsContent youtubePremiumDefaultContent3 = new SubsContent("다른 앱을 사용하는 중에 백그라운드에서 동영상 재생 가능");
            SubsContent youtubePremiumDefaultContent4 = new SubsContent("YouTube Music Premium 포함");
            SubsContent youtubePremiumDefaultContent5 = new SubsContent("1개월 무료 체험");

            youtubePremiumDefaultContent1.addSubsRank(youtubePremiumDefaultRank);
            youtubePremiumDefaultContent2.addSubsRank(youtubePremiumDefaultRank);
            youtubePremiumDefaultContent3.addSubsRank(youtubePremiumDefaultRank);
            youtubePremiumDefaultContent4.addSubsRank(youtubePremiumDefaultRank);
            youtubePremiumDefaultContent5.addSubsRank(youtubePremiumDefaultRank);
            youtubePremiumDefaultRank.addSubs(youtubePremium);

            em.persist(youtubePremium);


            //문화12
            Subs spotify = new Subs("스포티파이");
            spotify.updateImage(subsImageUrl+"spotify.png");

            spotify.addCategory(culture);
            spotify.addTag(man);
            spotify.addTag(woman);
            spotify.addTag(age20);
            spotify.addTag(age30);
            spotify.addTag(age40);
            spotify.addTag(cultureTag);
            spotify.addTag(music);
            spotify.addTag(event);


            spotify.updateInfo("Spotify를 사용하면 휴대폰, 컴퓨터, 태블릿 등에서 매 순간 알맞은 음악이나 팟캐스트를 손쉽게 찾을 수 있습니다.\n" +
                    "Spotify에는 수많은 트랙과 에피소드가 있습니다. 그러니 운전대를 잡고 있든, 운동을 하고 있든, 파티를 하든, 긴장을 풀고 있든 상관없이 알맞은 음악이나 팟캐스트를 항상 간편하게 재생할 수 있습니다. 듣고 싶은 항목을 선택하거나 Spotify가 골라 주는 항목을 들어보세요.\n" +
                    "또는 친구들, 아티스트 및 유명인들의 컬렉션을 둘러보거나 라디오 스테이션을 만들고 앉아서 듣기만 해도 됩니다.\n" +
                    "\n" +
                    "Spotify를 통해 일상에 사운드트랙을 더하세요.");

            SubsRank spotifyDefaultRank = new SubsRank("베이직", 7900, RankLevel.DEFAULT);
            SubsContent spotifyDefaultContent1 = new SubsContent("무광고로 음악 감상하기");
            SubsContent spotifyDefaultContent2 = new SubsContent("나만의 맞춤 플레이리스트");
            SubsContent spotifyDefaultContent3 = new SubsContent("8천만 곡 감상 가능");
            SubsContent spotifyDefaultContent4 = new SubsContent("여러 디바이스에서 감상");
            SubsContent spotifyDefaultContent5 = new SubsContent("7일 무료 체험");
            spotifyDefaultContent1.addSubsRank(spotifyDefaultRank);
            spotifyDefaultContent2.addSubsRank(spotifyDefaultRank);
            spotifyDefaultContent3.addSubsRank(spotifyDefaultRank);
            spotifyDefaultContent4.addSubsRank(spotifyDefaultRank);
            spotifyDefaultContent5.addSubsRank(spotifyDefaultRank);
            spotifyDefaultRank.addSubs(spotify);


            SubsRank spotifyPrimetRank = new SubsRank("개인", 10900, RankLevel.PRIME);
            SubsContent spotifyPrimeContent1 = new SubsContent("베이직의 모든 혜택");
            SubsContent spotifyPrimeContent2 = new SubsContent("다운로드하여 오프라인에서 감상");
            SubsContent spotifyPrimeContent3 = new SubsContent("7일 무료 체험");
            spotifyPrimeContent1.addSubsRank(spotifyPrimetRank);
            spotifyPrimeContent2.addSubsRank(spotifyPrimetRank);
            spotifyPrimeContent3.addSubsRank(spotifyPrimetRank);
            spotifyPrimetRank.addSubs(spotify);

            SubsRank spotifyPremiumRank = new SubsRank("듀오", 16350, RankLevel.PREMIUM);
            SubsContent spotifyPremiumContent1 = new SubsContent("개인의 모든 혜택");
            SubsContent spotifyPremiumContent2 = new SubsContent("Premium 별도 계정 2개");
            SubsContent spotifyPremiumContent3 = new SubsContent("7일 무료 체험");
            spotifyPremiumContent1.addSubsRank(spotifyPremiumRank);
            spotifyPremiumContent2.addSubsRank(spotifyPremiumRank);
            spotifyPremiumContent3.addSubsRank(spotifyPremiumRank);
            spotifyPremiumRank.addSubs(spotify);

            em.persist(spotify);

            //문화13
            Subs kakaoPlus = new Subs("카카오톡 이모티콘 플러스");
            kakaoPlus.updateImage(subsImageUrl+"kakaoPlus.png");

            kakaoPlus.addCategory(culture);
            kakaoPlus.addTag(man);
            kakaoPlus.addTag(woman);
            kakaoPlus.addTag(age20);
            kakaoPlus.addTag(age30);
            kakaoPlus.addTag(cultureTag);
            kakaoPlus.addTag(emoji);
            kakaoPlus.addTag(event);


            kakaoPlus.updateInfo("50만개 이모티콘을 무제한으로\n" +
                    "\n" +
                    "이모티콘 플러스'는 이모티콘을 무제한으로 이용할 수 있는 이모티콘 구독 서비스입니다.\n" +
                    "'이모티콘 플러스' 가입자는 해당 이용 기간 동안 플러스 상품으로 분류된 이모티콘을 동시에 30개까지 다운로드하여 사용할 수 있고, 제공되는 특별 키보드를 통해 자동 추천되는 이모티콘을 선택하여 사용할 수 있습니다.");

            SubsRank kakaoPlusDefaultRank = new SubsRank("이모티콘 플러스", 4900, RankLevel.DEFAULT);
            SubsContent kakaoPlusDefaultContent1 = new SubsContent("이모티콘 무제한 다운로드");
            SubsContent kakaoPlusDefaultContent2 = new SubsContent("150여 개의 한정판 이모티콘");
            SubsContent kakaoPlusDefaultContent3 = new SubsContent("이모티콘 자동추천");
            SubsContent kakaoPlusDefaultContent4 = new SubsContent("마이취향 기능");
            SubsContent kakaoPlusDefaultContent5 = new SubsContent("첫 달 무료");

            kakaoPlusDefaultContent1.addSubsRank(kakaoPlusDefaultRank);
            kakaoPlusDefaultContent2.addSubsRank(kakaoPlusDefaultRank);
            kakaoPlusDefaultContent3.addSubsRank(kakaoPlusDefaultRank);
            kakaoPlusDefaultContent4.addSubsRank(kakaoPlusDefaultRank);
            kakaoPlusDefaultContent5.addSubsRank(kakaoPlusDefaultRank);

            kakaoPlusDefaultRank.addSubs(kakaoPlus);

            em.persist(kakaoPlus);

            //음료1
            Subs alcholTalk = new Subs("술담화");
            alcholTalk.updateImage(subsImageUrl+"alcholTalk.png");
            alcholTalk.addCategory(drink);
            alcholTalk.addTag(man);
            alcholTalk.addTag(woman);
            alcholTalk.addTag(age20);
            alcholTalk.addTag(age30);
            alcholTalk.addTag(drinkTag);
            alcholTalk.addTag(alcohol);


            alcholTalk.updateInfo("찾아오는 인생술 술담화\n" +
                    "술담화는 2019년 1월부터 우리나라 최초로 전통주 구독 서비스를 시작했습니다. \"전통주를 일일이 고를 필요 없이 술과 큐레이션 카드를 함께 받아보는 담화박스는 전통주에 입문할 수 있는 좋은 계기가 될 수 있다\"라고 자신한 술담화 이재욱 대표의 이야기를 실었습니다.");

            SubsRank alcholTalkDefaultRank = new SubsRank("종합 담화박스", 39000, RankLevel.DEFAULT);
            SubsContent alcholTalkDefaultContent1 = new SubsContent("전통주 소믈리에가 엄선한 다양한 주종");
            SubsContent alcholTalkDefaultContent2 = new SubsContent("추천 안주, 향미그래프, 보관 방법 등 소믈리에의 친절한 설명을 담은 큐레이션 카드로 전통주를 쉽게 이해할 수 있어요");
            SubsContent alcholTalkDefaultContent3 = new SubsContent("이달의 전통주와 가장 어울리는 술자리 컨셉을 포스터로 만날 수 있어요");
            alcholTalkDefaultContent1.addSubsRank(alcholTalkDefaultRank);
            alcholTalkDefaultContent2.addSubsRank(alcholTalkDefaultRank);
            alcholTalkDefaultContent3.addSubsRank(alcholTalkDefaultRank);
            alcholTalkDefaultRank.addSubs(alcholTalk);

            SubsRank alcholTalkPrimetRank = new SubsRank("증류주 담화박스", 49000, RankLevel.PRIME);
            SubsContent alcholTalkPrimeContent1 = new SubsContent("전통주 소믈리에가 엄선한 증류주");
            SubsContent alcholTalkPrimeContent2 = new SubsContent("추천 안주, 향미그래프, 보관 방법 등 소믈리에의 친절한 설명을 담은 큐레이션 카드로 전통주를 쉽게 이해할 수 있어요");
            alcholTalkPrimeContent1.addSubsRank(alcholTalkPrimetRank);
            alcholTalkPrimeContent2.addSubsRank(alcholTalkPrimetRank);
            alcholTalkPrimetRank.addSubs(alcholTalk);

            SubsRank alcholTalkPremiumtRank = new SubsRank("약·청주 담화박스", 52000, RankLevel.PREMIUM);
            SubsContent alcholTalkPremiumContent1 = new SubsContent("인고의 시간 끝에 술을 맑게 걸러 탄생한 귀한 약·청주");
            SubsContent alcholTalkPremiumContent2 = new SubsContent("추천 안주, 향미그래프, 보관 방법 등 소믈리에의 친절한 설명을 담은 큐레이션 카드로 전통주를 쉽게 이해할 수 있어요");
            alcholTalkPremiumContent1.addSubsRank(alcholTalkPremiumtRank);
            alcholTalkPremiumContent2.addSubsRank(alcholTalkPremiumtRank);
            alcholTalkPremiumtRank.addSubs(alcholTalk);


            em.persist(alcholTalk);

            //음료2
            Subs oshulock = new Subs("오설록");
            oshulock.updateImage(subsImageUrl+"oshulock.png");

            oshulock.addCategory(drink);
            oshulock.addTag(man);
            oshulock.addTag(woman);
            oshulock.addTag(age30);
            oshulock.addTag(age40);
            oshulock.addTag(drinkTag);
            oshulock.addTag(tea);


            oshulock.updateInfo("다다일상, 차와 함께하는 삶\n" +
                    "차를 마시는 것은 일상의 쉼을 가지며, 나를 이끌어내고 세상을 따뜻하게 바라보는 것에 관한 일입니다. 당신이 언제 어디에 있든 차와 함께 삶의 아름다운 여백을 만들어 나가길 바랍니다.\n" +
                    "\n" +
                    "다다일상은 다채롭고 다양한 일상을 위한 오설록만의 특별한 차 구독 서비스입니다. 매일 똑같은 하루가 지겹다면 티 한잔으로 당신의 일상에 색깔을 불어넣으세요. 다채로운 차 향기로 더욱 풍요로워지는 티 라이프를 만나보세요.");

            SubsRank oshulockDefaultRank = new SubsRank("[프리미엄 티 컬렉션] 다다일상 정기배송", 28000, RankLevel.DEFAULT);
            SubsContent oshulockDefaultContent1 = new SubsContent("프리미엄 티 컬렉션 매월 정기배송");
            SubsContent oshulockDefaultContent2 = new SubsContent("더욱 즐거운 찻자리를 위해 매월 증정되는 스페셜 기프트");
            SubsContent oshulockDefaultContent3 = new SubsContent("이달의 티타임 챌린지 참여 시 받을 수 있는 챌린지 리워드");
            SubsContent oshulockDefaultContent4 = new SubsContent("나만의 일상을 기록할 수 있는 티 다이어리 제공(6개월 구독자 한정)");
            oshulockDefaultContent1.addSubsRank(oshulockDefaultRank);
            oshulockDefaultContent2.addSubsRank(oshulockDefaultRank);
            oshulockDefaultContent3.addSubsRank(oshulockDefaultRank);
            oshulockDefaultContent4.addSubsRank(oshulockDefaultRank);
            oshulockDefaultRank.addSubs(oshulock);

            em.persist(oshulock);


            //음료3
            Subs bottleworks = new Subs("보틀웍스");
            bottleworks.updateImage(subsImageUrl+"bottleworks.png");

            bottleworks.addCategory(drink);
            bottleworks.addTag(man);
            bottleworks.addTag(woman);
            bottleworks.addTag(age30);
            bottleworks.addTag(age40);
            bottleworks.addTag(drinkTag);
            bottleworks.addTag(tea);


            bottleworks.updateInfo("아침 출근길마다 마시는 커피 한 잔. 누군가 보면 멋진 워커홀릭처럼 보이지만 사실 우린 알죠. 살기 위해 마신다는 걸\n" +
                    "점심 먹고 나면 삼삼오오 모여 커피를 마시는 사람들. 누군가 보면 식사 후의 티타임처럼 보이지만 사실 우린 알죠. 잠을 쫓기 위해 마신다는 걸\n" +
                    "\n" +
                    "그런데 왜 매번 커피를 마셔야 하나요? 우리는 생각했습니다.\n" +
                    " \"하루에 커피 한 잔으로 줄이고 더 다양하고 건강한 마실거리를 마시면 어떨까?\"\n" +
                    "\n" +
                    "커피를 입에 달고 살던, 하지만 이 질문에 공감하는 이들이 모여 보틀웍스가 되었습니다. 그리고 내 몸에 주는 건강한 휴식 한 잔, 더 이상 뭐를 마시나 고민의 종료, 세상에 없던 마시는 차 정기구독 서비스 보틀웍스를 시작합니다.");

            SubsRank bottleworksDefaultRank = new SubsRank("디스커버리 키트", 22800, RankLevel.DEFAULT);
            SubsContent bottleworksDefaultContent1 = new SubsContent("매달 추천받은 8종류의 다양한 차를 경험해요");
            bottleworksDefaultContent1.addSubsRank(bottleworksDefaultRank);
            bottleworksDefaultRank.addSubs(bottleworks);

            SubsRank bottleworksPrimeRank = new SubsRank("카페인 프리 키트", 22800, RankLevel.DEFAULT);
            SubsContent bottleworksPrimeContent1 = new SubsContent("매달 카페인 없는 차로만 경험해보세요");
            bottleworksPrimeContent1.addSubsRank(bottleworksPrimeRank);
            bottleworksPrimeRank.addSubs(bottleworks);

            em.persist(bottleworks);


            //음료4
            Subs kalascoffee = new Subs("칼라스 로스터스");
            kalascoffee.updateImage(subsImageUrl+"kalascoffee.png");

            kalascoffee.addCategory(drink);
            kalascoffee.addTag(man);
            kalascoffee.addTag(woman);
            kalascoffee.addTag(age30);
            kalascoffee.addTag(age40);
            kalascoffee.addTag(drinkTag);
            kalascoffee.addTag(coffee);


            kalascoffee.updateInfo("늘 마시는 커피, 바쁜 생활로 인해 주문하기 귀찮다. 새로운 커피에 도전하고 싶은데 실패할까 두렵다. 어떤 커피를 선택 해야 할 지 모르겠다. 칼라스커피가 도와 드리겠습니다.\n" +
                    "\n" +
                    "로스팅 챔피언이 추천하는 다양한 커피로 당신의 미각을 업그레이드 시켜보세요.");

            SubsRank kalascoffeeDefaultRank = new SubsRank("Espresso Roast", 220000, RankLevel.DEFAULT);
            SubsContent kalascoffeeDefaultContent1 = new SubsContent("매주 화요일 새로운 커피를 발송하는 서비스입니다.");
            kalascoffeeDefaultContent1.addSubsRank(kalascoffeeDefaultRank);
            kalascoffeeDefaultRank.addSubs(kalascoffee);

            SubsRank kalascoffeePrimeRank = new SubsRank("Filter Roast", 240000, RankLevel.PRIME);
            SubsContent kalascoffeePrimeContent1 = new SubsContent("매주 화요일 새로운 커피를 발송하는 서비스입니다.");
            kalascoffeePrimeContent1.addSubsRank(kalascoffeePrimeRank);
            kalascoffeePrimeRank.addSubs(kalascoffee);

            em.persist(kalascoffee);


            //음료4
            Subs dolce = new Subs("네스카페 돌체구스토");
            dolce.updateImage(subsImageUrl+"dolce.png");

            dolce.addCategory(drink);
            dolce.addTag(man);
            dolce.addTag(woman);
            dolce.addTag(age30);
            dolce.addTag(age40);
            dolce.addTag(drinkTag);
            dolce.addTag(coffee);


            dolce.updateInfo("가장 쉽고 편하게 정기배송으로 홈카페를 즐기는 방법홈카페를 즐기는 방법\n" +
                    "\n" +
                    "한 번의 버튼 터치로 집에서 탁월한 품질의 커피를 음미하는 시간을 상상해 보세요. 네스카페® 돌체구스토®와 함께라면, 다양한 커피숍 메뉴는 물론 나만의 취향에 정확히 맞춘 음료를 즐길 수 있습니다.");

            SubsRank dolceDefaultRank = new SubsRank("프렌드", 16530, RankLevel.DEFAULT);
            SubsContent dolceDefaultContent1 = new SubsContent("머신은 있지만 캡슐이 필요한 1인 가구에게 추천");
            SubsContent dolceDefaultContent2 = new SubsContent("매월 2박스/최대 10% 할인/모든 회차 무료배송");
            dolceDefaultContent1.addSubsRank(dolceDefaultRank);
            dolceDefaultContent2.addSubsRank(dolceDefaultRank);
            dolceDefaultRank.addSubs(dolce);

            SubsRank dolcePrimeRank = new SubsRank("마스터", 33060, RankLevel.PRIME);
            SubsContent dolcePrimeContent1 = new SubsContent("머신은 있지만 캡슐이 필요한 1인 가구에게 추천");
            SubsContent dolcePrimeContent2 = new SubsContent("매월 4박스 이상/최대 10% 할인/모든 회차 무료배송");
            dolcePrimeContent1.addSubsRank(dolcePrimeRank);
            dolcePrimeContent2.addSubsRank(dolcePrimeRank);
            dolcePrimeRank.addSubs(dolce);

            em.persist(dolce);


            //음료5
            Subs samdasu = new Subs("제주 삼다수");
            samdasu.updateImage(subsImageUrl+"samdasu.png");

            samdasu.addCategory(drink);
            samdasu.addTag(man);
            samdasu.addTag(woman);
            samdasu.addTag(age30);
            samdasu.addTag(age40);
            samdasu.addTag(drinkTag);
            samdasu.addTag(water);


            samdasu.updateInfo("믿으니까, 내 평생의 물로 삼다. 집 앞으로 쉽고 편하게 배송받는 제주 삼다수 가정배송!\n" +
                    "한 번만 신청하면 전국 125개처 삼다수 대리점에서 무거운 생수를 집 앞으로 무료배송해 드립니다!");

            SubsRank samdasuDefaultRank = new SubsRank("제주 삼다수 일반 2L", 25920, RankLevel.DEFAULT);
            SubsContent samdasuDefaultContent1 = new SubsContent("제주 삼다수 일반 (라벨이 있는 유라벨) 2L x 6개입");
            SubsContent samdasuDefaultContent2 = new SubsContent("최소 주문수량 4팩 기준입니다.");
            samdasuDefaultContent1.addSubsRank(samdasuDefaultRank);
            samdasuDefaultContent2.addSubsRank(samdasuDefaultRank);
            samdasuDefaultRank.addSubs(samdasu);

            SubsRank samdasuPrimeRank = new SubsRank("제주 삼다수 그린 2L", 25920, RankLevel.PRIME);
            SubsContent samdasuPrimeContent1 = new SubsContent("제주 삼다수 그린 (라벨이 없는 무라벨) 2L x 6개입");
            SubsContent samdasuPrimeContent2 = new SubsContent("최소 주문수량 4팩 기준입니다.");
            samdasuPrimeContent1.addSubsRank(samdasuPrimeRank);
            samdasuPrimeContent2.addSubsRank(samdasuPrimeRank);
            samdasuPrimeRank.addSubs(samdasu);

            em.persist(samdasu);


            //음료6
            Subs pupledog = new Subs("퍼플독 와인구독");
            pupledog.updateImage(subsImageUrl+"pupledog.png");

            pupledog.addCategory(drink);
            pupledog.addTag(man);
            pupledog.addTag(age20);
            pupledog.addTag(age30);
            pupledog.addTag(drinkTag);
            pupledog.addTag(alcohol);


            pupledog.updateInfo("퍼플독이 엄선하고 AI가 매칭해주는 나만의 맞춤 와인! 이제 와인을 몰라도 와인을 즐길 수 있습니다.\n" +
                    "전세계 다양한 와이너리와 독점 계약하여 국내에 단 한 곳! 퍼플독에서만 만날 수 있는 와인! 1차 서류심사, 2차 소믈리에 테이스팅을 통하여 퍼플독 품질 기준에 합격한 고품질의 와인만을 소싱합니다.\n" +
                    "\n" +
                    "특허받은 AI 와인 매칭으로 퍼플독 자체 개발 알고리즘을 바탕으로 각자의 와인 취향을 분석하고 매칭해 드립니다.\n" +
                    "와이너리, 품종, 음용 온도, 페어링, 스토리 등이 풍부하게 담긴 컨텐츠를 함께 받아볼 수 있습니다.\n" +
                    "한번 배송으로 끝내는 것이 아니라 고객 피드백 분석을 통한 양방향 서비스를 추구합니다.");

            SubsRank pupledogDefaultRank = new SubsRank("The Green Double", 39000, RankLevel.DEFAULT);
            SubsContent pupledogDefaultContent1 = new SubsContent("매월 Green 등급의 동일한 와인 2병(1+1)이 제공됩니다.");
            pupledogDefaultContent1.addSubsRank(pupledogDefaultRank);
            pupledogDefaultRank.addSubs(pupledog);

            SubsRank pupledogPrimetRank = new SubsRank("The Yellow Double", 73000, RankLevel.PRIME);
            SubsContent pupledogPrimeContent1 = new SubsContent("매월 Yellow 등급의 와인 2병이 제공됩니다.");
            SubsContent pupledogPrimeContent2 = new SubsContent("페이퍼 컨텐츠가 함께 제공됩니다.");
            pupledogPrimeContent1.addSubsRank(pupledogPrimetRank);
            pupledogPrimeContent2.addSubsRank(pupledogPrimetRank);
            pupledogPrimetRank.addSubs(pupledog);

            SubsRank pupledogPremiumtRank = new SubsRank("The Crimson", 99000, RankLevel.PREMIUM);
            SubsContent pupledogPremiumContent1 = new SubsContent("매월 Crimson 등급의 와인 1병이 제공됩니다.");
            SubsContent pupledogPremiumContent2 = new SubsContent("페이퍼 컨텐츠가 함께 제공됩니다.");
            pupledogPremiumContent1.addSubsRank(pupledogPremiumtRank);
            pupledogPremiumContent2.addSubsRank(pupledogPremiumtRank);
            pupledogPremiumtRank.addSubs(pupledog);

            em.persist(pupledog);


            //음료7
            Subs shakit = new Subs("쉐이킷 DIY 칵테일");
            shakit.updateImage(subsImageUrl+"shakit.png");

            shakit.addCategory(drink);
            shakit.addTag(man);
            shakit.addTag(woman);
            shakit.addTag(age20);
            shakit.addTag(age30);
            shakit.addTag(drinkTag);
            shakit.addTag(alcohol);


            shakit.updateInfo("Ready for your special day!\n" +
                    "쉐이킷이 칵테일을 어디서든 쉽고 재밌게 즐길 수 있도록 만들었습니다.\n" +
                    "\n" +
                    "평범한 일상에 특별한 순간을.\n" +
                    "매일같이 반복되는 일상, 특별한 순간이 필요하진 않나요? shakit 키트로 친구, 가족과 함께 칵테일을 만들어보세요. 여러분께 잊지못할 추억이 될거에요.\n" +
                    "레시피만 따라하면 오늘은 내가 바텐더!\n" +
                    "어려워 보이는 칵테일, 자격증이 있어야만 칵테일을 만들 수 있을까요? 월드클래스 바텐더가 만든 레시피와 영상으로 누구나 쉽게 전문가처럼 맛있는 칵테일을 만들 수 있습니다.\n" +
                    "구하기 어려운 칵테일 재료들, 이제 하나씩 따로 구매하지 마세요.\n" +
                    "칵테일 하나만 만들려고 했는데, 이것 저것 사다보니 배보다 배꼽이...! shakit은 4-6잔 분량의 칵테일에 필요한 모든 재료를 하나의 키트로 제공합니다.\n" +
                    "\n" +
                    "매달 집으로 찾아오는 칵테일 키트 큐레이션, 매달 다양한 칵테일 키트를 만나보세요.");

            SubsRank shakitDefaultRank = new SubsRank("집에서 즐기는 칵테일 키트", 29800, RankLevel.DEFAULT);
            SubsContent shakitDefaultContent1 = new SubsContent("매달 시즌에 어울리는 칵테일 키트");
            shakitDefaultContent1.addSubsRank(shakitDefaultRank);
            shakitDefaultRank.addSubs(shakit);

            SubsRank shakitPrimeRank = new SubsRank("집에서 즐기는 논알코올 칵테일 키트", 22500, RankLevel.DEFAULT);
            SubsContent shakitPrimeContent1 = new SubsContent("매달 시즌에 어울리는 논알코올 칵테일 키트");
            shakitPrimeContent1.addSubsRank(shakitPrimeRank);
            shakitPrimeRank.addSubs(shakit);

            em.persist(shakit);

            //음료8
            Subs seoulmilk = new Subs("서울우유 가정배달");
            seoulmilk.updateImage(subsImageUrl+"seoulmilk.png");

            seoulmilk.addCategory(drink);
            seoulmilk.addTag(man);
            seoulmilk.addTag(woman);
            seoulmilk.addTag(age20);
            seoulmilk.addTag(age30);
            seoulmilk.addTag(drinkTag);
            seoulmilk.addTag(milk);


            seoulmilk.updateInfo("Ready for your special day!\n" +
                    "마트까지 가지않아도 신선한 우유를 집에서!! 서울우유를 가정배달로 만나보세요.\n" +
                    "\n" +
                    "당신이 선택해야 할 좋은우유, 체세포수까지 1등급인 우유. 세균수 만으로는 좋은우유의 기준이 될 수 없습니다. 우유는 2개의 최고등급으로 채워야 가장 좋은 우유입니다. 이제는 체세포수 1등급입니다.");

            SubsRank seoulmilkDefaultRank = new SubsRank("나100% 1000ml", 13600, RankLevel.DEFAULT);
            SubsContent seoulmilkDefaultContent1 = new SubsContent("한달 간 매주 1개 배송 기준");
            seoulmilkDefaultContent1.addSubsRank(seoulmilkDefaultRank);
            seoulmilkDefaultRank.addSubs(seoulmilk);

            SubsRank seoulmilkPrimeRank = new SubsRank("나100% 1.8ℓ", 25200, RankLevel.PRIME);
            SubsContent seoulmilkPrimeContent1 = new SubsContent("한달 간 매주 1개 배송 기준");
            seoulmilkPrimeContent1.addSubsRank(seoulmilkPrimeRank);
            seoulmilkPrimeRank.addSubs(seoulmilk);

            em.persist(seoulmilk);


            //음료9
            Subs mailmilk = new Subs("매일다이렉트 가정배달");
            mailmilk.updateImage(subsImageUrl+"mailmilk.png");

            mailmilk.addCategory(drink);
            mailmilk.addTag(man);
            mailmilk.addTag(woman);
            mailmilk.addTag(age20);
            mailmilk.addTag(age30);
            mailmilk.addTag(drinkTag);
            mailmilk.addTag(milk);


            mailmilk.updateInfo("목장에서 집앞까지 신선하게! 매일우유.\n" +
                    " 매일아침, 목장에서 집앞까지 신선하고 편리하게 만나는 가정배달 전용 우유입니다.\n" +
                    "\n" +
                    "매일유업 콜드체인 시스템으로 생산에서 배달까지 목장의 신선함을 그대로! 안전하게 유통됩니다.");

            SubsRank mailmilkDefaultRank = new SubsRank("매일우유 가정배달 전용우유 930ml", 14400, RankLevel.DEFAULT);
            SubsContent mailmilkDefaultContent1 = new SubsContent("한달 간 매주 1개 배송 기준");
            mailmilkDefaultContent1.addSubsRank(mailmilkDefaultRank);
            mailmilkDefaultRank.addSubs(mailmilk);

            SubsRank mailmilkPrimeRank = new SubsRank("소화가 잘되는 우유 930ml", 16000, RankLevel.PRIME);
            SubsContent mailmilkPrimeContent1 = new SubsContent("한달 간 매주 1개 배송 기준");
            mailmilkPrimeContent1.addSubsRank(mailmilkPrimeRank);
            mailmilkPrimeRank.addSubs(mailmilk);

            em.persist(mailmilk);

            //패션잡화1
            Subs kangjipsa = new Subs("강집사 고양이모래");
            kangjipsa.updateImage(subsImageUrl+"kangjipsa.png");

            kangjipsa.addCategory(extra);
            kangjipsa.addTag(man);
            kangjipsa.addTag(woman);
            kangjipsa.addTag(age20);
            kangjipsa.addTag(age30);
            kangjipsa.addTag(extraTag);
            kangjipsa.addTag(pet);


            kangjipsa.updateInfo("전체 갈이와 화장실 청소 때 꼭 필요한 모래!\n" +
                    "매번 시기에 맞춰 결제하기 번거로우셨죠? 모래 정기구독만 신청하시면 원하는 날짜에 모래를 받아보실 수 있습니다.");

            SubsRank kangjipsaDefaultRank = new SubsRank("벤토나이트3", 39800, RankLevel.DEFAULT);
            SubsContent kangjipsaDefaultContent1 = new SubsContent("벤토나이트3 6kg X 3개");
            kangjipsaDefaultContent1.addSubsRank(kangjipsaDefaultRank);
            kangjipsaDefaultRank.addSubs(kangjipsa);

            SubsRank kangjipsaPrimeRank = new SubsRank("인디언모래 오리지널", 31800, RankLevel.PRIME);
            SubsContent kangjipsaPrimeContent1 = new SubsContent("인디언모래 오리지널 3kg X 6개");
            kangjipsaPrimeContent1.addSubsRank(kangjipsaPrimeRank);
            kangjipsaPrimeRank.addSubs(kangjipsa);

            SubsRank kangjipsaPremiumRank = new SubsRank("공룡모래 믹스", 29800, RankLevel.PREMIUM);
            SubsContent kangjipsaPremiumContent1 = new SubsContent("공룡모래 믹스 14kg X 1개");
            kangjipsaPremiumContent1.addSubsRank(kangjipsaPremiumRank);
            kangjipsaPremiumRank.addSubs(kangjipsa);

            em.persist(kangjipsa);


            //패션잡화2
            Subs scentmonster = new Subs("센트몬스터 차량용방향제");
            scentmonster.updateImage(subsImageUrl+"scentmonster.png");

            scentmonster.addCategory(extra);
            scentmonster.addTag(man);
            scentmonster.addTag(woman);
            scentmonster.addTag(age20);
            scentmonster.addTag(age30);
            scentmonster.addTag(extraTag);
            scentmonster.addTag(airFreshener);


            scentmonster.updateInfo("당신이 머무는 공간을 놀랍도록 변화시키는 프리미엄 차량용 방향제, 주변에 닿는 모든 곳이 변화할 수 있도록 나만의 향기로운 여정을 떠나보세요.\n" +
                    "\n" +
                    "'센트몬스터'만 가능한 차량용 방향제. 센트몬스터는 셀 수 없는 샘플 테스트를 거쳐 향이 은은하고 더 오래 지속되는 방법을 연구하고 개선해 왔습니다.\n" +
                    "특별이 제작된 '센트 태블릿'은 유아용 젖병에 쓰이는 BPA Free PE로 만들어져 온도차가 큰 차량 내 환경에서 안전성까지 더했습니다. 또한, 특유의 흡수성으로 향료 원액을 깊숙이 품어 풍부한 발향력과 그 지속성을 유지합니다.\n" +
                    "\n" +
                    "센트몬스터 카트리지는 매 주 소량 생산, 포장, 배송까지 직접 담당하여 철저한 품질 관리를 위해 노력합니다. 잠시나마 미뤄왔던 여유를 센트몬스터의 특별한 10가지 향기를 통해 나만의 향기로운 여정을 떠나보세요.");

            SubsRank scentmonsterDefaultRank = new SubsRank("방향제 카트리지 정기구독", 5500, RankLevel.DEFAULT);
            SubsContent scentmonsterDefaultContent1 = new SubsContent("방향제 카트리지 (정기구독) 1EA");
            SubsContent scentmonsterDefaultContent2 = new SubsContent("블랙체리/화이트솝/이슬 맺힌 풀잎/클린 스타트/패뷸러스 무브먼트/아침 도서관/가드니아/우드 트레일/딜라이트 메모리/프레쉬 레몬/블룸 앤 그로우");
            scentmonsterDefaultContent1.addSubsRank(scentmonsterDefaultRank);
            scentmonsterDefaultContent2.addSubsRank(scentmonsterDefaultRank);
            scentmonsterDefaultRank.addSubs(scentmonster);

            em.persist(scentmonster);

            //패션잡화3
            Subs biomist = new Subs("바이오미스트 아로마인테리어");
            biomist.updateImage(subsImageUrl+"biomist.png");

            biomist.addCategory(extra);
            biomist.addTag(man);
            biomist.addTag(woman);
            biomist.addTag(age20);
            biomist.addTag(age30);
            biomist.addTag(extraTag);
            biomist.addTag(airFreshener);


            biomist.updateInfo("언제였지? 얼마나 남았지? 매번 신경쓰이셨나요? 다 떨어져갈 때쯤 구매하자니 잊기 일쑤, 365일 간편하게 향기로운 일상, 바이오미스트가 함께 할게요.");

            SubsRank biomistDefaultRank = new SubsRank("베스트셀러 랜덤발송", 9000, RankLevel.DEFAULT);
            SubsContent biomistDefaultContent1 = new SubsContent("13%이상 \"오드 퍼퓸\"등급의 섬유 및 실내 공간 전용 탈취/방향제 리빙 퍼퓸");
            SubsContent biomistDefaultContent2 = new SubsContent("2개월 정기 결제/1개월 단위 가격");
            biomistDefaultContent1.addSubsRank(biomistDefaultRank);
            biomistDefaultContent2.addSubsRank(biomistDefaultRank);
            biomistDefaultRank.addSubs(biomist);

            em.persist(biomist);

            //패션잡화4
            Subs lael = new Subs("라엘 순면커버생리대");
            lael.updateImage(subsImageUrl+"lael.png");

            lael.addCategory(extra);
            lael.addTag(woman);
            lael.addTag(age20);
            lael.addTag(age30);
            lael.addTag(age40);
            lael.addTag(age50);
            lael.addTag(extraTag);
            lael.addTag(sanitaryPad);


            lael.updateInfo("라엘 시그니처&오리지널 100% 유기농 순면 커버 생리대.\n" +
                    "완전 무염소 표백(TCF) 공법을 적용한 유기농 순면 커버로 피부 자극을 최소화하였습니다. 내 몸에 꼭 맞는 바디핏 엠보싱의 쿠션감과 부드러움을 느껴보세요.");

            SubsRank laelDefaultRank = new SubsRank("유기농 순면커버 생리대 중형", 31600, RankLevel.DEFAULT);
            SubsContent laelDefaultContent1 = new SubsContent("중형(14개입)x4팩");
            laelDefaultContent1.addSubsRank(laelDefaultRank);
            laelDefaultRank.addSubs(lael);

            SubsRank laelPrimeRank = new SubsRank("유기농 순면커버 생리대 대형", 31600, RankLevel.PRIME);
            SubsContent laelPrimeContent1 = new SubsContent("대형(12개입)x4팩");
            laelPrimeContent1.addSubsRank(laelPrimeRank);
            laelPrimeRank.addSubs(lael);

            SubsRank laelPremiumRank = new SubsRank("유기농 순면커버 생리대 오버나이트", 31600, RankLevel.PRIME);
            SubsContent laelPremiumContent1 = new SubsContent("오버나이트(8개입)x4팩");
            laelPremiumContent1.addSubsRank(laelPremiumRank);
            laelPremiumRank.addSubs(lael);

            em.persist(lael);

            //패션잡화5
            Subs comport = new Subs("컴포트 발목보호양말");
            comport.updateImage(subsImageUrl+"comport.png");

            comport.addCategory(extra);
            comport.addTag(woman);
            comport.addTag(man);
            comport.addTag(age20);
            comport.addTag(age30);
            comport.addTag(extraTag);
            comport.addTag(socks);


            comport.updateInfo("발목 전문 양말. 아치 리프팅과 인파인 앵클쿠션을 통해 강한 압박으로 발목부를 보호합니다.\n" +
                    "컴포트는 2021년 4월부터 대한육상연맹의 공식 후원사로 선정되었습니다. 대한체육회 62개 종목의 모든 선수들이 자사 양말을 착용한다는 목표 아래, 컴포트는 34개 종목 55명의 국가대표를 후원하고 있습니다.");

            SubsRank comportDefaultRank = new SubsRank("C구독", 15000, RankLevel.DEFAULT);
            SubsContent comportDefaultContent1 = new SubsContent("한달에 한번, 컴포트 양말 전제품 선택 가능 배송");
            SubsContent comportDefaultContent2 = new SubsContent("4개월 정기구독/한달 기준 가격");
            comportDefaultContent1.addSubsRank(comportDefaultRank);
            comportDefaultContent2.addSubsRank(comportDefaultRank);
            comportDefaultRank.addSubs(comport);

            em.persist(comport);


            //패션잡화6
            Subs eyeluview = new Subs("아이러뷰");
            eyeluview.updateImage(subsImageUrl+"eyeluview.png");

            eyeluview.addCategory(extra);
            eyeluview.addTag(woman);
            eyeluview.addTag(man);
            eyeluview.addTag(age20);
            eyeluview.addTag(age30);
            eyeluview.addTag(extraTag);
            eyeluview.addTag(glasses);


            eyeluview.updateInfo("트렌디, 알뜰함, 설레임까지 새로운 경험으로 가득한 명품안경 구독서비스\n" +
                    "월 22,000원으로 백만 가지 명품안경을 도수렌즈 부담 없이 경험할 수 있는 기회\n" +
                    "간단한 스타일 입력 후 정기구독을 신청하면 AI가 스타일, 사이즈, 시력을 분석해 매달 새로운 안경 제공! 내가 선택한 안경원에서 도수렌즈까지 제작!");

            SubsRank eyeluviewDefaultRank = new SubsRank("EyeLuview 안경구독서비스", 22000, RankLevel.DEFAULT);
            SubsContent eyeluviewDefaultContent1 = new SubsContent("AI가 스타일, 사이즈, 시력을 분석한 매달 새로운 안경 제공");
            eyeluviewDefaultContent1.addSubsRank(eyeluviewDefaultRank);
            eyeluviewDefaultRank.addSubs(eyeluview);

            em.persist(eyeluview);


            //패션잡화7
            Subs motipia = new Subs("모티피아 디퓨저");
            motipia.updateImage(subsImageUrl+"motipia.png");

            motipia.addCategory(extra);
            motipia.addTag(woman);
            motipia.addTag(man);
            motipia.addTag(age20);
            motipia.addTag(age30);
            motipia.addTag(extraTag);
            motipia.addTag(airFreshener);


            motipia.updateInfo("디퓨저, 이제 똑똑한 정기구독 서비스로 이용하세요. 모티피아브랜드가 함께합니다.\n" +
                    "정기구독시 일반구매보다 할인된 금액으로 주기마다 디퓨저를 무료배송으로 제공하여 해피콜을 통한 각종 안내서비스를 진행해드리고 관리해 드립니다.");

            SubsRank motipiaDefaultRank = new SubsRank("리사이클 디퓨저세트 패키지", 9950, RankLevel.DEFAULT);
            SubsContent motipiaDefaultContent1 = new SubsContent("리사이클 디퓨저세트 패키지(120ml 3개입)");
            SubsContent motipiaDefaultContent2 = new SubsContent("2개월 정기 결제/1개월 단위 가격");
            motipiaDefaultContent1.addSubsRank(motipiaDefaultRank);
            motipiaDefaultContent2.addSubsRank(motipiaDefaultRank);
            motipiaDefaultRank.addSubs(motipia);

            SubsRank motipiaPrimeRank = new SubsRank("모티피아 디퓨저 3종세트", 14450, RankLevel.PRIME);
            SubsContent motipiaPrimeContent1 = new SubsContent("모티피아 디퓨저 3종 1SET(120ml 3개입)");
            SubsContent motipiaPrimeContent2 = new SubsContent("2개월 정기 결제/1개월 단위 가격");
            motipiaPrimeContent1.addSubsRank(motipiaPrimeRank);
            motipiaPrimeContent2.addSubsRank(motipiaPrimeRank);
            motipiaPrimeRank.addSubs(motipia);

            em.persist(motipia);


            //패션잡화8
            Subs monthNyang = new Subs("월간냥씨 리필장난감");
            monthNyang.updateImage(subsImageUrl+"monthNyang.png");

            monthNyang.addCategory(extra);
            monthNyang.addTag(woman);
            monthNyang.addTag(man);
            monthNyang.addTag(age20);
            monthNyang.addTag(age30);
            monthNyang.addTag(extraTag);
            monthNyang.addTag(pet);


            monthNyang.updateInfo("매달 새로운 장난감이 집 앞으로. 국내최초 고양이 리필장난감 구독서비스 월간냥씨.\n" +
                    "장난감에 쉽게 질리는 고양이를 위해 매달 새로운 낚시대 리필형 장난감을 받아볼 수 있는 집사맞춤형 구독서비스에요!");

            SubsRank monthNyangDefaultRank = new SubsRank("리필장난감 3종", 14900, RankLevel.DEFAULT);
            SubsContent monthNyangDefaultContent1 = new SubsContent("깃털형/리본형/인형 리필장난감 3종");
            monthNyangDefaultContent1.addSubsRank(monthNyangDefaultRank);
            monthNyangDefaultRank.addSubs(monthNyang);

            SubsRank monthNyangPrimeRank = new SubsRank("리필장난감 5종", 19900, RankLevel.PRIME);
            SubsContent monthNyangPrimeContent1 = new SubsContent("깃털형/작은 벌레형/리본형/지렁이형/인형 리필장난감 5종");
            monthNyangPrimeContent1.addSubsRank(monthNyangPrimeRank);
            monthNyangPrimeRank.addSubs(monthNyang);

            SubsRank monthNyangPremiumRank = new SubsRank("리필장난감 7종", 19900, RankLevel.PREMIUM);
            SubsContent monthNyangPremiumContent1 = new SubsContent("깃털형/작은 벌레형x2/리본형/지렁이형x2/인형 리필장난감 7종");
            monthNyangPremiumContent1.addSubsRank(monthNyangPremiumRank);
            monthNyangPremiumRank.addSubs(monthNyang);

            em.persist(monthNyang);


            //패션잡화9
            Subs closetshare = new Subs("클로젯셰어");
            closetshare.updateImage(subsImageUrl+"closetshare.png");

            closetshare.addCategory(extra);
            closetshare.addTag(woman);
            closetshare.addTag(age20);
            closetshare.addTag(age30);
            closetshare.addTag(extraTag);
            closetshare.addTag(clothing);
            closetshare.addTag(event);


            closetshare.updateInfo("더클로젯컴퍼니는 패션과 기술의 결합을 통해 사람들의 일상을 변화시키고자 하는 기업입니다. 패션 공유 커머스를 통해 고객에게 다양한 경험을 제공하고, 그로 인해 자연스럽게 세상을 변화시키는 일. 클로젯셰어의 존재이유입니다.\n" +
                    "\n" +
                    "세상의 모든 옷장을 연결하여, 그들이 마음껏 패션 아이템을 공유하는 경험을 제공하는 것. 클로젯셰어가 하는 일입니다.\n" +
                    "\n" +
                    "당신의 옷장을 공유하세요. 물론, 다른 사람의 옷장도 마음껏 경험하시구요.\n" +
                    "이제 쇼핑보다 쉬운 셰어링의 편리함을 경험하세요.\n" +
                    "패션셰어링은 새로운 소유방식입니다. 클로젯셰어와 함께라면 당신은 이미 한 발 앞서간 셈이죠.");

            SubsRank closetshareDefaultRank = new SubsRank("의류 8피스", 79000, RankLevel.DEFAULT);
            SubsContent closetshareefaultContent1 = new SubsContent("한 번에 의류 4피스, 월 2회 주문가능");
            SubsContent closetshareefaultContent2 = new SubsContent("아우터,드레스,투피스는 2피스 적용");
            SubsContent closetshareefaultContent3 = new SubsContent("신규 가입 첫달 특가");
            closetshareefaultContent1.addSubsRank(closetshareDefaultRank);
            closetshareefaultContent2.addSubsRank(closetshareDefaultRank);
            closetshareefaultContent3.addSubsRank(closetshareDefaultRank);
            closetshareDefaultRank.addSubs(closetshare);

            SubsRank closetsharePrimeRank = new SubsRank("가방 2개", 79000, RankLevel.DEFAULT);
            SubsContent closetsharePrimeContent1 = new SubsContent("한 번에 가방 1개, 월 2회 주문가능");
            SubsContent closetsharePrimeContent2 = new SubsContent("신규 가입 첫달 특가");
            closetsharePrimeContent1.addSubsRank(closetsharePrimeRank);
            closetsharePrimeContent2.addSubsRank(closetsharePrimeRank);
            closetsharePrimeRank.addSubs(closetshare);

            SubsRank closetsharePremiumRank = new SubsRank("투게더 (의류 8피스 + 가방 2개)", 149000, RankLevel.PREMIUM);
            SubsContent closetsharePremiumContent1 = new SubsContent("한 번에 의류 4피스 + 가방 1개, 월 2회 주문가능");
            SubsContent closetsharePremiumContent2 = new SubsContent("아우터,드레스,투피스는 2피스 적용");
            SubsContent closetsharePremiumContent3 = new SubsContent("신규 가입 첫달 특가");
            closetsharePremiumContent1.addSubsRank(closetsharePremiumRank);
            closetsharePremiumContent2.addSubsRank(closetsharePremiumRank);
            closetsharePremiumContent3.addSubsRank(closetsharePremiumRank);
            closetsharePremiumRank.addSubs(closetshare);

            em.persist(closetshare);


            //패션잡화10
            Subs wisely = new Subs("와이즐리 제로마진멤버십");
            wisely.updateImage(subsImageUrl+"wisely.png");

            wisely.addCategory(extra);
            wisely.addTag(woman);
            wisely.addTag(age20);
            wisely.addTag(age30);
            wisely.addTag(extraTag);
            wisely.addTag(mall);



            wisely.updateInfo("와이즐리 전 제품을 마진이 전혀 없는 가격에, 즉, 원가로 구매할 수 있는 서비스입니다.\n" +
                    "카테고리, 제품, 횟수 제한 없이 전 제품 무제한 원가로 구매. 제품 품질 불만족 시 30일 내, 전액 환불. 한 개만 사도 바로 이득이 돼요.");

            SubsRank wiselyDefaultRank = new SubsRank("제로마진멤버십", 2990, RankLevel.DEFAULT);
            SubsContent wiselyDefaultContent1 = new SubsContent("와이즐리 전 제품에 제로마진 적용");
            SubsContent wiselyDefaultContent2 = new SubsContent("최대 65%, 평균 22% 가격 절감");
            wiselyDefaultContent1.addSubsRank(wiselyDefaultRank);
            wiselyDefaultContent2.addSubsRank(wiselyDefaultRank);
            wiselyDefaultRank.addSubs(wisely);

            em.persist(wisely);


            //패션잡화11
            Subs cupang = new Subs("쿠팡 와우 멤버십");
            cupang.updateImage(subsImageUrl+"cupang.png");

            cupang.addCategory(extra);
            cupang.addTag(woman);
            cupang.addTag(man);
            cupang.addTag(age20);
            cupang.addTag(age30);
            cupang.addTag(age40);
            cupang.addTag(extraTag);
            cupang.addTag(mall);



            cupang.updateInfo("쿠팡은 고객이 앱을 여는 순간부터 주문이 집으로 배달되는 순간까지 고객을 감동시키는 것을 목표로 쇼핑 경험을 재창조하고 있습니다. 쿠팡은 고객의 일상을 바꿉니다. “쿠팡 없이 어떻게 살았을까”라는 생각이 드는 세상을 만들고 있습니다.\n" +
                    "\n" +
                    "로켓배송은 수백만 개의 상품 셀렉션을 평균 12시간 내 배송합니다. 최첨단 기술과 430만 ㎡ 규모의 물류 인프라를 통해 고객의 쇼핑 경험을 바꾸어나가고 있습니다. 대만에서는 다음날 배송 서비스를 제공합니다.\n" +
                    "로켓프레시는 국내 최대 규모의 온라인 장보기 서비스입니다. 고객이 필요한 모든 식품을 특허 받은 친환경 프레시백으로 배송합니다.\n" +
                    "로켓직구는 수백만 개의 품목을 해외에서 배송합니다. 한국과 대만에서 서비스가 제공되며 해외 직구의 가격과 배송을 혁신해 상품을 저렴하고 빠르게 받아볼 수 있게 합니다.\n" +
                    "\n" +
                    "쿠팡플레이는 쿠팡 로켓와우 멤버십 회원에게 제공되는 무료 콘텐츠 스트리밍 서비스입니다. (일부 유료 콘텐츠 제외) 주기적으로 업데이트 되는 국내 예능부터 영화, 애니메이션, 다큐멘터리, 쿠팡플레이 시리즈까지 다채로운 콘텐츠를 광고 없이 시청하실 수 있습니다.");

            SubsRank cupangDefaultRank = new SubsRank("쿠팡 와우 멤버십", 4990, RankLevel.DEFAULT);
            SubsContent cupangDefaultContent1 = new SubsContent("로켓배송상품 하나만 사도 무료배송");
            SubsContent cupangDefaultContent2 = new SubsContent("로켓프레시 신선식품 장보기");
            SubsContent cupangDefaultContent3 = new SubsContent("낮시간 주문 새벽도착");
            SubsContent cupangDefaultContent4 = new SubsContent("아침 주문 저녁도착");
            SubsContent cupangDefaultContent5 = new SubsContent("골드박스 회원전용 초특가");
            SubsContent cupangDefaultContent6 = new SubsContent("로켓배송상품 30일 무료반품");
            SubsContent cupangDefaultContent7 = new SubsContent("첫 30일 최대 5% 캐시적립");
            SubsContent cupangDefaultContent8 = new SubsContent("쿠팡플레이로 영화, 드라마 시청");

            cupangDefaultContent1.addSubsRank(cupangDefaultRank);
            cupangDefaultContent2.addSubsRank(cupangDefaultRank);
            cupangDefaultContent3.addSubsRank(cupangDefaultRank);
            cupangDefaultContent4.addSubsRank(cupangDefaultRank);
            cupangDefaultContent5.addSubsRank(cupangDefaultRank);
            cupangDefaultContent6.addSubsRank(cupangDefaultRank);
            cupangDefaultContent7.addSubsRank(cupangDefaultRank);
            cupangDefaultContent8.addSubsRank(cupangDefaultRank);
            cupangDefaultRank.addSubs(cupang);

            em.persist(cupang);


            //패션잡화12
            Subs launchbox = new Subs("런치박스 티셔츠");
            launchbox.updateImage(subsImageUrl+"launchbox.png");

            launchbox.addCategory(extra);
            launchbox.addTag(woman);
            launchbox.addTag(man);
            launchbox.addTag(age20);
            launchbox.addTag(age30);
            launchbox.addTag(extraTag);
            launchbox.addTag(clothing);



            launchbox.updateInfo("이제부터 티셔츠는 런치박스, 처음 만나는 티셔츠 정기구독.\n" +
                    "티셔츠, 이제 고민은 놓아주세요. 당신의 다음, 그 다음도 런치박스 하시면 됩니다.\n" +
                    "\n" +
                    "기준은 높게, 가격은 낮게.\n" +
                    "많은 티셔츠가 유통마진 및 브랜드로 포장되어 너무 비싸게 판매되고 있습니다. 런치박스는 엄선된 제품을 최적의 가격으로 제공합니다.\n" +
                    "\n" +
                    "매번 새로운 티셔츠와 그래픽\n" +
                    "매달 추가되는 런치박스만의 그래픽 티셔츠. 위트넘치는 그래픽 티셔츠가 매달 업데이트됩니다. 정기배송 제품에 추가하시면 예쁘게 프린팅 해 보내드립니다.");

            SubsRank launchboxDefaultRank = new SubsRank("베이직 티셔츠", 17900, RankLevel.DEFAULT);
            SubsContent launchboxDefaultContent1 = new SubsContent("매달 1장씩, 깔끔한 티셔츠");
            launchboxDefaultContent1.addSubsRank(launchboxDefaultRank);
            launchboxDefaultRank.addSubs(launchbox);

            SubsRank launchboxPrimeRank = new SubsRank("그래픽 티셔츠", 27900, RankLevel.DEFAULT);
            SubsContent launchboxPrimeContent1 = new SubsContent("매달 1장씩, 그래픽 티셔츠");
            launchboxPrimeContent1.addSubsRank(launchboxPrimeRank);
            launchboxPrimeRank.addSubs(launchbox);

            em.persist(launchbox);


            //패션잡화13
            Subs phonego = new Subs("폰고 리퍼기기");
            phonego.updateImage(subsImageUrl+"phonego.png");

            phonego.addCategory(extra);
            phonego.addTag(man);
            phonego.addTag(age20);
            phonego.addTag(age30);
            phonego.addTag(extraTag);
            phonego.addTag(electronicDevice);



            phonego.updateInfo("우리가 기다려온 착한 전자기기 플랫폼, 폰고\n" +
                    "주식회사 피에로컴퍼니는 글로벌 자원 선순환을 달성하는 것을 미션으로 전자기기 리퍼비시 시장을 개척하고 있어요. 탄소 배출과 무분별한 자원 채굴로 발생하는 환경 피해를 줄이고 보다 많은 사람들이 저렴하게 스마트기기의 혜택을 누릴 수 있도록 연구해요.\n" +
                    "\n" +
                    "폰고를 이용해야 하는 이유\n" +
                    "부담 없는 가격. 새 제품과 동일 기능이지만, 최대 60% 저렴한 최고 가성비!\n" +
                    "최고의 품질. 폰고 파트너스 전문 테크니션의 완벽한 기기 재정비!\n" +
                    "폰고 케어. 폰고에서 12개월 무상 보증까지!\n" +
                    "지구에 착한 리퍼기기. 폰고의 리퍼기기를 사용하면 탄소 배출을 크게 줄일 수 있어요.");

            SubsRank phonegoDefaultRank = new SubsRank("아이폰12", 14500, RankLevel.DEFAULT);
            SubsContent phonegoDefaultContent1 = new SubsContent("전문가가 검수한 S급 기기");
            SubsContent phonegoDefaultContent2 = new SubsContent("12개월 무상 보증, 폰고 케어");
            SubsContent phonegoDefaultContent3 = new SubsContent("배송비 무료/계약 후 검수, 출고까지 4-5일");
            phonegoDefaultContent1.addSubsRank(phonegoDefaultRank);
            phonegoDefaultContent2.addSubsRank(phonegoDefaultRank);
            phonegoDefaultContent3.addSubsRank(phonegoDefaultRank);
            phonegoDefaultRank.addSubs(phonego);

            SubsRank phonegoPrimeRank = new SubsRank("아이폰13", 19500, RankLevel.PRIME);
            SubsContent phonegoPrimeContent1 = new SubsContent("전문가가 검수한 S급 기기");
            SubsContent phonegoPrimeContent2 = new SubsContent("12개월 무상 보증, 폰고 케어");
            SubsContent phonegoPrimeContent3 = new SubsContent("배송비 무료/계약 후 검수, 출고까지 4-5일");
            phonegoPrimeContent1.addSubsRank(phonegoPrimeRank);
            phonegoPrimeContent2.addSubsRank(phonegoPrimeRank);
            phonegoPrimeContent3.addSubsRank(phonegoPrimeRank);
            phonegoPrimeRank.addSubs(phonego);

            SubsRank phonegoPremiumRank = new SubsRank("아이패드 10세대 와이파이", 19900, RankLevel.PREMIUM);
            SubsContent phonegoPremiumContent1 = new SubsContent("전문가가 검수한 S급 기기");
            SubsContent phonegoPremiumContent2 = new SubsContent("12개월 무상 보증, 폰고 케어");
            SubsContent phonegoPremiumContent3 = new SubsContent("배송비 무료/계약 후 검수, 출고까지 4-5일");
            phonegoPremiumContent1.addSubsRank(phonegoPremiumRank);
            phonegoPremiumContent2.addSubsRank(phonegoPremiumRank);
            phonegoPremiumContent3.addSubsRank(phonegoPremiumRank);
            phonegoPremiumRank.addSubs(phonego);

            em.persist(phonego);



            //글그림1
            Subs dbpia = new Subs("디비피아");
            dbpia.updateImage(subsImageUrl+"dbpia.png");

            dbpia.addCategory(book);
            dbpia.addTag(man);
            dbpia.addTag(woman);
            dbpia.addTag(age30);
            dbpia.addTag(age40);
            dbpia.addTag(age50);
            dbpia.addTag(bookTag);
            dbpia.addTag(paper);



            dbpia.updateInfo("연구자 중심의 한국형 학술정보 포털, DBpia\n" +
                    "\n" +
                    "400만 여편의 학술 논문과 4,700여 종의 학술지 열람!\n" +
                    "DBpia 개인 정기구독 상품을 통해 3,000여개의 발행기관에서 발행한 400만 여편의 학술 논문과 4,700여 종의 학술지를 무제한으로 보실 수 있습니다. DBpia는 신규 논문과 학술지를 매일매일 업데이트 하고 있습니다.\n" +
                    "\n" +
                    "개인 정기구독 상품으로 논문 이용을 더 쉽게!\n" +
                    "개인 정기구독 상품은 소속기관이 없거나, 소속된 기관에서 구독을 하지 않아 논문을 이용하고 싶은데도 이용하기 어려운 개인 회원님들을 위해 마련한 서비스입니다. 글·그림 1권의 가격으로 한 달 내내 모든 논문을 다 열람하실 수 있습니다. DBpia는 독립 연구자님들의 논문 접근성이 쉬워지기를 기대합니다.");

            SubsRank dbpiaDefaultRank = new SubsRank("Standard 정기구독", 14900, RankLevel.DEFAULT);
            SubsContent dbpiaDefaultContent1 = new SubsContent("논문 무제한 보기");
            SubsContent dbpiaDefaultContent2 = new SubsContent("다운로드 정가 구매");
            dbpiaDefaultContent1.addSubsRank(dbpiaDefaultRank);
            dbpiaDefaultContent2.addSubsRank(dbpiaDefaultRank);
            dbpiaDefaultRank.addSubs(dbpia);

            SubsRank dbpiaPrimeRank = new SubsRank("PREMIUM 정기구독", 29800, RankLevel.PRIME);
            SubsContent dbpiaPrimeContent1 = new SubsContent("논문 무제한 보기");
            SubsContent dbpiaPrimeContent2 = new SubsContent("무제한 다운로드 50% 할인 구매");
            dbpiaPrimeContent1.addSubsRank(dbpiaPrimeRank);
            dbpiaPrimeContent2.addSubsRank(dbpiaPrimeRank);
            dbpiaPrimeRank.addSubs(dbpia);

            em.persist(dbpia);

            //글그림2
            Subs samEbook = new Subs("샘 eBook");
            samEbook.updateImage(subsImageUrl+"samEbook.png");

            samEbook.addCategory(book);
            samEbook.addTag(man);
            samEbook.addTag(woman);
            samEbook.addTag(age20);
            samEbook.addTag(age30);
            samEbook.addTag(bookTag);
            samEbook.addTag(eBook);



            samEbook.updateInfo("eBook을 사랑하는 또 다른 방법, sam을 소개합니다.\n" +
                    "교보문고 sam은 ‘지성과 지혜의 샘' 이라는 뜻을 담은, 국내 최초 지식문화 구독서비스입니다. 대한민국 대표 서점다운, 따라잡을 수 없는 콘텐츠 보유량. 매일매일 즐겨도 끊임없는, 새로운 콘텐츠를 마음껏 만나보세요!");

            SubsRank samEbookDefaultRank = new SubsRank("sam 2", 7000, RankLevel.DEFAULT);
            SubsContent samEbookDefaultContent2 = new SubsContent("매월 2권씩만 알차게");
            SubsContent samEbookDefaultContent1 = new SubsContent("첫 달 천원");
            samEbookDefaultContent2.addSubsRank(samEbookDefaultRank);
            samEbookDefaultContent1.addSubsRank(samEbookDefaultRank);
            samEbookDefaultRank.addSubs(samEbook);

            SubsRank samEbookPrimeRank = new SubsRank("sam 무제한", 9900, RankLevel.PRIME);
            SubsContent samEbookPrimeContent2 = new SubsContent("무제한으로 즐기는 무한독서!");
            SubsContent samEbookPrimeContent1 = new SubsContent("첫 달 무료");
            samEbookPrimeContent2.addSubsRank(samEbookPrimeRank);
            samEbookPrimeContent1.addSubsRank(samEbookPrimeRank);
            samEbookPrimeRank.addSubs(samEbook);

            em.persist(samEbook);


            //글그림3
            Subs flipbox = new Subs("플립북 문제해결력");
            flipbox.updateImage(subsImageUrl+"flipbox.png");

            flipbox.addCategory(book);
            flipbox.addTag(man);
            flipbox.addTag(woman);
            flipbox.addTag(age30);
            flipbox.addTag(age40);
            flipbox.addTag(bookTag);
            flipbox.addTag(children);

            flipbox.updateInfo("아이의 문제해결력을 키우는 가장 확실한 방법\n" +
                    "\n" +
                    "1:1 온라인 클래스! 매주 25분 아이 성향 맞춤 화상 수업을 통해 즐거운 문제해결 경험을 쌓아요.\n" +
                    "직업 미션 도전! '직업 체험'이라는 놀이 주제로 매주 새로운 미션에 도전해요.\n" +
                    "역량 맞춤 교육! 아이의 역량 발달 수준을 평가하고 개별 맞춤 커리큘럼을 제공해요.\n" +
                    "\n" +
                    "플립박스는 단순히 직업을 체험해보는 활동이 아닙니다.\n" +
                    "잘 짜여진 직업 체험이라는 놀이 중심의 컨텐츠로 직업 미션에 도전하여 자기주도적으로 해결해보며 '문제해결력'을 키워주는 홈스쿨링 KIT입니다.\n" +
                    "미션 홈키트가 배송되고, 월 4회 1:1 온라인 클래스(25분)로 진행됩니다. 미션 홈키트의 종류는 플립박스에서 아이마다 맞춤 커리큘럼을 추천해드립니다.");

            SubsRank flipboxDefaultRank = new SubsRank("플립박스 문제해결력 미션 홈키트 정기구독 BASIC", 55000, RankLevel.DEFAULT);
            SubsContent flipboxDefaultContent1 = new SubsContent("1:1 온라인 클래스 + 미션소품(홈키트)");
            SubsContent flipboxDefaultContent2 = new SubsContent("수업 시간 : 월 4회, 1회 25분");
            SubsContent flipboxDefaultContent3 = new SubsContent("수업 연령 : 4세 ~ 9세");
            flipboxDefaultContent1.addSubsRank(flipboxDefaultRank);
            flipboxDefaultContent2.addSubsRank(flipboxDefaultRank);
            flipboxDefaultContent3.addSubsRank(flipboxDefaultRank);
            flipboxDefaultRank.addSubs(flipbox);

            em.persist(flipbox);


            //글그림4
            Subs ridibooks = new Subs("리디셀렉트");
            ridibooks.updateImage(subsImageUrl+"ridibooks.png");

            ridibooks.addCategory(book);
            ridibooks.addTag(man);
            ridibooks.addTag(woman);
            ridibooks.addTag(age20);
            ridibooks.addTag(age30);
            ridibooks.addTag(bookTag);
            ridibooks.addTag(eBook);
            ridibooks.addTag(event);

            ridibooks.updateInfo("리디셀렉트는 매월 리디에서 엄선된 신간 및 베스트셀러 콘텐츠를 무제한으로 만나볼 수 있는 구독형 서비스로, 리디셀렉트에서만 볼 수 있는 양질의 콘텐츠가 다양하게 제공되고 있습니다.");

            SubsRank ridibooksDefaultRank = new SubsRank("리디셀렉트", 4900, RankLevel.DEFAULT);
            SubsContent ridibooksDefaultContent1 = new SubsContent("매월 엄선된 신간 및 베스트셀러 콘텐츠 무제한");
            SubsContent ridibooksDefaultContent2 = new SubsContent("신규 구독시 1개월 무료 혜택");
            ridibooksDefaultContent1.addSubsRank(ridibooksDefaultRank);
            ridibooksDefaultContent2.addSubsRank(ridibooksDefaultRank);
            ridibooksDefaultRank.addSubs(ridibooks);

            em.persist(ridibooks);


            //글그림5
            Subs carrit = new Subs("캐릿");
            carrit.updateImage(subsImageUrl+"carrit.png");

            carrit.addCategory(book);
            carrit.addTag(man);
            carrit.addTag(woman);
            carrit.addTag(age20);
            carrit.addTag(age30);
            carrit.addTag(bookTag);
            carrit.addTag(text);

            carrit.updateInfo("하루 10분 트렌드 모니터링으로 요즘 사람 되기! 실무에 필요한 ‘진짜’ 트렌드를 쉽고 빠르게 알려드립니다.\n" +
                    "\n" +
                    "캐릿은 항상 최신을 다합니다. 당근 Carrot 같은 트렌드를 발 Feet 빠르게 전달하는 Careet.\n" +
                    "요즘 유행하는 게 뭐지? 요즘 친구들은 뭘 좋아하지? 근데 대체… 이걸 왜 좋아하는 거지?\n" +
                    "이런 궁금증을 가진 적 있다면, 잘 찾아오셨습니다. 캐릿 Careet은 MZ 세대가 지금 가장 열광하고 있는 트렌드를 캐치하고 그에 반응하는 속마음은 무엇인지 마케팅 인사이트를 제공하는 전문가 그룹입니다.\n" +
                    "시대 흐름을 읽고 싶다면, 마음만은 젊게 살고 싶다면, 요즘 사람들의 취향과 생각이 궁금하다면 캐릿 Careet을 구독하세요.\n" +
                    "\n" +
                    "이런 건 누가 안 알려주나? 에서 ‘누가’를 맡고 있는 에디터들이 ‘이런 것’을 매일 매일 알려드립니다.");

            SubsRank carritDefaultRank = new SubsRank("캐릿 멤버십", 8900, RankLevel.DEFAULT);
            SubsContent carritDefaultContent1 = new SubsContent("당일 배송되는 Z세대 트렌드 및 인사이트 콘텐츠 무제한 열람 가능");
            SubsContent carritDefaultContent2 = new SubsContent("뜻이 궁금한 신조어가 있다면? 캐릿이 정리해 놓은 MZ 용어 사전 열람 가능");
            SubsContent carritDefaultContent3 = new SubsContent("일잘러라면 놓치지 말아야 할 캐릿픽 트렌드 기사 매일 열람 가능");
            SubsContent carritDefaultContent4 = new SubsContent("월 5회 콘텐츠 무료 공유 가능, 공유 받은 친구는 로그인 없이 열람 가능");
            SubsContent carritDefaultContent5 = new SubsContent("한해 결산 및 새해 트렌드 이슈를 파악하는 가장 빠른 방법! 스페셜 트렌드 리포트 제공");
            SubsContent carritDefaultContent6 = new SubsContent("캐릿과 함께하는 트렌드 스터디, 오프라인 세미나 오픈 시 멤버십 회원 우선 초대");
            carritDefaultContent1.addSubsRank(carritDefaultRank);
            carritDefaultContent2.addSubsRank(carritDefaultRank);
            carritDefaultContent3.addSubsRank(carritDefaultRank);
            carritDefaultContent4.addSubsRank(carritDefaultRank);
            carritDefaultContent5.addSubsRank(carritDefaultRank);
            carritDefaultContent6.addSubsRank(carritDefaultRank);
            carritDefaultRank.addSubs(carrit);

            em.persist(carrit);

            //글그림6
            Subs pinzle = new Subs("핀즐");
            pinzle.updateImage(subsImageUrl+"pinzle.png");

            pinzle.addCategory(book);
            pinzle.addTag(woman);
            pinzle.addTag(age30);
            pinzle.addTag(age40);
            pinzle.addTag(bookTag);
            pinzle.addTag(picture);

            pinzle.updateInfo("매월 집으로 배송되는 진짜 아티스트의 프린트 에디션\n" +
                    "\n" +
                    "어? 나 그림 좋아했네!\n" +
                    "검은색 지관통을 열면 A1 사이즈의 그림과 아티스트의 인터뷰가 담긴 북클릿이 등장합니다. 그림을 펼쳐 감상하고 북클릿을 찬찬히 살펴보다 보면 어느새 나만의 생각을 덧붙여 그림을 새롭게 읽어내고 있는 자신을 발견하게 될 거예요. 그러고는 이내 깨달을 겁니다. 당신은 알고 있던 것 이상으로 그림을 좋아하고 또 즐길 줄도 아는 사람이었다는 것을 말이죠.\n" +
                    "\n" +
                    "아트마켓의 생생함이 담긴 제철 그림\n" +
                    "레드닷 디자인 어워드 수상으로 심미적 감각을 증명한 핀즐이 지금 가장 트렌디한 작품을 큐레이션합니다.\n" +
                    "지금, 당신에게 소개해주고 싶은 아티스트\n" +
                    "지금 주목받는 글로벌 아티스트를 국내에 단독 소개합니다. 파리, 암스테르담, 베를린, 밀라노, 도쿄, LA 등 글로벌 아트 신을 이끄는 아티스트가 지금 여기 있습니다.\n" +
                    "지관통에 돌돌 말아 넣은 영감\n" +
                    "감상하기 좋은 A1 사이즈 ‘아트 포스터 작품’과 아티스트의 라이프스타일, 인사이트를 담은 ‘북클릿’이 함께 제공됩니다.\n" +
                    "이제 당신의 TV, PC도 작품이 될 거예요\n" +
                    "매월 새로운 아티스트의 작품과 음악을 페어링하여 영상으로 릴리즈되는 아트 스트리밍 플레이리스트 ‘Pp.’ 이제 당신의 TV, 노트북, 그리고 스마트폰에서도 예술을 플레이하세요.");

            SubsRank pinzleDefaultRank = new SubsRank("그림 정기구독", 18900, RankLevel.DEFAULT);
            SubsContent pinzleDefaultContent1 = new SubsContent("Art Poster (594*841mm - A1 )");
            SubsContent pinzleDefaultContent2 = new SubsContent("Booklet (148*210mm / 16p)");
            SubsContent pinzleDefaultContent3 = new SubsContent("Paper Tube");
            pinzleDefaultContent1.addSubsRank(pinzleDefaultRank);
            pinzleDefaultContent2.addSubsRank(pinzleDefaultRank);
            pinzleDefaultContent3.addSubsRank(pinzleDefaultRank);
            pinzleDefaultRank.addSubs(pinzle);

            em.persist(pinzle);

            //글그림7
            Subs longblack = new Subs("롱블랙");
            longblack.updateImage(subsImageUrl+"longblack.png");

            longblack.addCategory(book);
            longblack.addTag(woman);
            longblack.addTag(man);
            longblack.addTag(age20);
            longblack.addTag(age30);
            longblack.addTag(age40);
            longblack.addTag(bookTag);
            longblack.addTag(text);

            longblack.updateInfo("하루 하나의 노트를 발행하고 오늘이 지나면 사라지는 구독 서비스\n" +
                    "지금 롱블랙에서 남들과 다른 한 끗을 만들어보세요\n" +
                    "\n" +
                    "매일 자정에 롱블랙 노트가 발행됩니다. 오늘 발행된 노트는 단 24시간 동안만 읽을 수 있어요. 놓치면 내일은 읽을 수 없습니다.\n" +
                    "지금이 롱블랙을 즐길 수 있는 가장 빠른 시간입니다.");

            SubsRank longblackDefaultRank = new SubsRank("롱블랙 멤버십 서비스", 4900, RankLevel.DEFAULT);
            SubsContent longblackDefaultContent1 = new SubsContent("자정에 발행되어 24시간 뒤 사라지는 매일 하나의 노트");
            SubsContent longblackDefaultContent2 = new SubsContent("자정에 발행되어 24시간 뒤 사라지는 매일 하나의 노트");
            longblackDefaultContent1.addSubsRank(longblackDefaultRank);
            longblackDefaultContent2.addSubsRank(longblackDefaultRank);
            longblackDefaultRank.addSubs(longblack);

            em.persist(longblack);

            //글그림8
            Subs millie = new Subs("밀리의 서재");
            millie.updateImage(subsImageUrl+"millie.png");

            millie.addCategory(book);
            millie.addTag(woman);
            millie.addTag(man);
            millie.addTag(age20);
            millie.addTag(age30);
            millie.addTag(age40);
            millie.addTag(bookTag);
            millie.addTag(text);
            millie.addTag(event);

            millie.updateInfo("독서와 무제한 친해지리\n" +
                    "밀리의 서재는 사람들의 일상을 조금 더 가치 있고 즐겁게 만드는 중입니다.\n" +
                    "당신을 기다리는 15만 권의 도서! 10년치 베스트셀러 신간 오디오북 장르 소설까지. 관심 분야에 있는 책도 딱 골라드려요.");

            SubsRank millieDefaultRank = new SubsRank("전자책 월 정기구독", 9900, RankLevel.DEFAULT);
            SubsContent millieDefaultContent1 = new SubsContent("무제한으로 즐기는 10만 권의 전자책");
            SubsContent millieDefaultContent2 = new SubsContent("첫 달 무료");
            millieDefaultContent1.addSubsRank(millieDefaultRank);
            millieDefaultContent2.addSubsRank(millieDefaultRank);
            millieDefaultRank.addSubs(millie);

            SubsRank milliePrimeRank = new SubsRank("종이책 월 정기구독", 15900, RankLevel.PRIME);
            SubsContent milliePrimeContent1 = new SubsContent("밀리의 모든 콘텐츠를 무제한으로 보고 2달에 1번 종이책+전자책 배송까지");
            SubsContent milliePrimeContent2 = new SubsContent("첫 달 무료");
            milliePrimeContent1.addSubsRank(milliePrimeRank);
            milliePrimeContent1.addSubsRank(milliePrimeRank);
            milliePrimeRank.addSubs(millie);

            em.persist(millie);


            //식물1
            Subs kukka = new Subs("꾸까");
            kukka.updateImage(subsImageUrl+"kukka.png");

            kukka.addCategory(plant);
            kukka.addTag(woman);
            kukka.addTag(age20);
            kukka.addTag(age30);
            kukka.addTag(plantTag);
            kukka.addTag(flower);
            kukka.addTag(event);

            kukka.updateInfo("특별한 이유가 있을 때만이 아닌 일상에서도 꽃을 즐기는 문화를 지향하는 플라워 기반 라이프 스타일 브랜드 꾸까(KUKKA)는 2014년 1월 런칭한 브랜드로, 꽃이 필요한 순간을 언제나 함께 합니다.\n" +
                    "꾸까(KUKKA)는 핀란드어로 #꽃을 의미하며, 꽃을 일상에서 자연스럽게 즐기는 핀란드의 #꽃문화를 새로운 라이프 스타일 문화로서 꾸까만의 방식으로 널리 전파하자는 브랜드 철학을 담고 있습니다.\n" +
                    "꾸까 커뮤니티는 #FLOWER 문화를 기반으로 활동하며, 이는 각각 F(Fun)-L(Love)-O(Only)-W(Whatever)-E(Easy)-R(Respect)을 내포하고 있습니다.\n" +
                    "꾸까는 항상 존중·존경심을 기반으로 나 자신(yourself)과 상대방(you)를 위한 흥미로운 컨텐츠로 사랑을 표현하며, 차별화된 혁신을 통한 라이프 스타일 커뮤니케이션을 지향합니다.");

            SubsRank kukkaDefaultRank = new SubsRank("합리적으로 적당히, 파머스 믹스", 58710, RankLevel.DEFAULT);
            SubsContent kukkaDefaultContent1 = new SubsContent("6개월동안 2주마다, 총 12회 꽃다발 구독(M)");
            SubsContent kukkaDefaultContent2 = new SubsContent("화병 무료 증정, 전 회차 플라워푸드(영양제 제공)");
            SubsContent kukkaDefaultContent3 = new SubsContent("장기간 구독시 추가 5~10% 할인");
            kukkaDefaultContent1.addSubsRank(kukkaDefaultRank);
            kukkaDefaultContent2.addSubsRank(kukkaDefaultRank);
            kukkaDefaultContent3.addSubsRank(kukkaDefaultRank);
            kukkaDefaultRank.addSubs(kukka);

            SubsRank kukkaPrimeRank = new SubsRank("선물로 좋은, 꽃다발 구독", 77710, RankLevel.PRIME);
            SubsContent kukkaPrimeContent1 = new SubsContent("6개월동안 2주마다, 총 12회 꽃다발 구독(L)");
            SubsContent kukkaPrimeContent2 = new SubsContent("화병 무료 증정, 전 회차 플라워푸드(영양제 제공)");
            SubsContent kukkaPrimeContent3 = new SubsContent("장기간 구독시 추가 5~10% 할인");
            kukkaPrimeContent1.addSubsRank(kukkaPrimeRank);
            kukkaPrimeContent2.addSubsRank(kukkaPrimeRank);
            kukkaPrimeContent3.addSubsRank(kukkaPrimeRank);
            kukkaPrimeRank.addSubs(kukka);

            em.persist(kukka);

            //식물2
            Subs dailoz = new Subs("데일로즈");
            dailoz.updateImage(subsImageUrl+"dailoz.png");

            dailoz.addCategory(plant);
            dailoz.addTag(woman);
            dailoz.addTag(age40);
            dailoz.addTag(age50);
            dailoz.addTag(plantTag);
            dailoz.addTag(flower);

            dailoz.updateInfo("꽃은 사람의 일생과 기쁜 일과 슬픈 일에 함께 해왔습니다. 꽃은 그 동안 축하, 감사, 사랑, 위로의 의미를 담아 왔습니다. 꽃은 우리 삶의 질을 향상시키는 역할을 할 수 있습니다. 이제 꽃은 선물의 역할과 더불어 주변에 꽃을 두고 우리의 삶을 힐링시켜야 합니다. 우리의 주변을 둘러보면 전자기기들만 가득합니다. 그 속에서 꽃을 통해 작은 자연과 함께 한다면 우리의 삶은 좀 더 행복해질 수 있을 것입니다.\n" +
                    "데일로즈는 '일상 속 꽃향기'라는 슬로건으로 생활 속 꽃문화의 정착을 위하여 오늘도 하루를 시작합니다.");

            SubsRank dailozDefaultRank = new SubsRank("petit dail vase [실속형]", 39800, RankLevel.DEFAULT);
            SubsContent dailozDefaultContent1 = new SubsContent("행복한 하루의 시작이 될 소담한 꽃");
            SubsContent dailozDefaultContent2 = new SubsContent("프렌치스타일의 세련된 꽃을 2주에 한번씩 지정해주신 요일마다 전달");
            SubsContent dailozDefaultContent3 = new SubsContent("특별한 세일 첫배송 업그레이드 + 꽃구독 12+1 상품");
            dailozDefaultContent1.addSubsRank(dailozDefaultRank);
            dailozDefaultContent2.addSubsRank(dailozDefaultRank);
            dailozDefaultContent3.addSubsRank(dailozDefaultRank);
            dailozDefaultRank.addSubs(dailoz);

            SubsRank dailozPrimeRank = new SubsRank("standard dail vase [일반형]", 59800, RankLevel.PRIME);
            SubsContent dailozPrimeContent1 = new SubsContent("내 글·그림상 위 은은한 향기를 안겨줄 꽃");
            SubsContent dailozPrimeContent2 = new SubsContent("프렌치스타일의 세련된 꽃을 2주에 한번씩 지정해주신 요일마다 전달");
            SubsContent dailozPrimeContent3 = new SubsContent("특별한 세일 첫배송 업그레이드 + 꽃구독 12+1 상품");
            dailozPrimeContent1.addSubsRank(dailozPrimeRank);
            dailozPrimeContent2.addSubsRank(dailozPrimeRank);
            dailozPrimeContent3.addSubsRank(dailozPrimeRank);
            dailozPrimeRank.addSubs(dailoz);


            SubsRank dailozPremiumRank = new SubsRank("premium dail vase [고급형]", 79800, RankLevel.PREMIUM);
            SubsContent dailozPremiumContent1 = new SubsContent("소중한 사람을 위한 마음 같은 꽃");
            SubsContent dailozPremiumContent2 = new SubsContent("프렌치스타일의 세련된 꽃을 2주에 한번씩 지정해주신 요일마다 전달");
            SubsContent dailozPremiumContent3 = new SubsContent("특별한 세일 첫배송 업그레이드 + 꽃구독 12+1 상품");
            dailozPremiumContent1.addSubsRank(dailozPremiumRank);
            dailozPremiumContent2.addSubsRank(dailozPremiumRank);
            dailozPremiumContent3.addSubsRank(dailozPremiumRank);
            dailozPremiumRank.addSubs(dailoz);


            em.persist(dailoz);

            //식물3
            Subs florano = new Subs("플로라노");
            florano.updateImage(subsImageUrl+"florano.png");

            florano.addCategory(plant);
            florano.addTag(woman);
            florano.addTag(age30);
            florano.addTag(age40);
            florano.addTag(plantTag);
            florano.addTag(flower);

            florano.updateInfo("꽃 정기구독, 왜? 플로라노여야 할까요?\n" +
                    "\n" +
                    "하나, 합리적인 가격.\n" +
                    "등급별로 생화의 가격은 천차만별 입니다. 1등급의 꽃을 유통하기 위하여, 매일 새벽 전문 플로리스트가 발로 뛰어다니며 최고의 꽃을 선별합니다.\n" +
                    "\n" +
                    "둘, 전 직원 플로리스트 구성\n" +
                    "꽃 디자인, 제작, 배송 검수, CS까지 모두 플로리스트로 구성된 플로라노 팀! 고객 경험을 최우선으로 생각하며 고객중심의 사고로 친절한 피드백을 드립니다.\n" +
                    "\n" +
                    "셋, 높은 만족도\n" +
                    "1등급 꽃을 사용하기 때문에 고객만족도가 매우 높습니다. 만약 받아보신 꽃이 컨디션이 안 좋다고 하더라도 언제든 플로라노 고객센터로 연락 주시면 만족하실 때까지 재배송해 드리고 있습니다.");

            SubsRank floranoDefaultRank = new SubsRank("정기구독 S", 39800, RankLevel.DEFAULT);
            SubsContent floranoDefaultContent1 = new SubsContent("공간의 활력을 더하고 싶은 고객님들에게 추천드리는 디자인 꽃다발");
            SubsContent floranoDefaultContent2 = new SubsContent("꽃다발 포장 마무리, 꽃 구성 엽서 동");
            SubsContent floranoDefaultContent3 = new SubsContent("2주 1회 기준 가격");
            floranoDefaultContent1.addSubsRank(floranoDefaultRank);
            floranoDefaultContent2.addSubsRank(floranoDefaultRank);
            floranoDefaultContent3.addSubsRank(floranoDefaultRank);
            floranoDefaultRank.addSubs(florano);

            SubsRank floranoPrimeRank = new SubsRank("정기구독 M", 55800, RankLevel.PRIME);
            SubsContent floranoPrimeContent1 = new SubsContent("공간의 활력을 더하고 싶은 고객님들에게 추천드리는 디자인 꽃다발");
            SubsContent floranoPrimeContent2 = new SubsContent("꽃다발 포장 마무리, 꽃 구성 엽서 동봉");
            SubsContent floranoPrimeContent3 = new SubsContent("2주 1회 기준 가격");
            floranoPrimeContent1.addSubsRank(floranoPrimeRank);
            floranoPrimeContent2.addSubsRank(floranoPrimeRank);
            floranoPrimeContent3.addSubsRank(floranoPrimeRank);
            floranoPrimeRank.addSubs(florano);

            em.persist(florano);

            //식물4
            Subs moooi = new Subs("모이");
            moooi.updateImage(subsImageUrl+"moooi.png");

            moooi.addCategory(plant);
            moooi.addTag(woman);
            moooi.addTag(age20);
            moooi.addTag(age30);
            moooi.addTag(plantTag);
            moooi.addTag(flower);

            moooi.updateInfo("유러피언 스타일의 꽃을 일상에서 정기적으로 받아보세요!\n" +
                    "계절감을 담은 6~8종의 꽃으로 구성되며 모이 플로리스트가 2주 단위로 새로운 디자인을 공개해요.");

            SubsRank moooiDefaultRank = new SubsRank("베이직", 55600, RankLevel.DEFAULT);
            SubsContent moooiDefaultContent1 = new SubsContent("계절감을 담은 6~8종의 꽃다발");
            SubsContent moooiDefaultContent2 = new SubsContent("전용박스/모이화병(첫 배송시)/사진 엽서/플라워푸드");
            SubsContent moooiDefaultContent3 = new SubsContent("2주 1회 기준 가격");
            moooiDefaultContent1.addSubsRank(moooiDefaultRank);
            moooiDefaultContent2.addSubsRank(moooiDefaultRank);
            moooiDefaultContent3.addSubsRank(moooiDefaultRank);
            moooiDefaultRank.addSubs(moooi);

            SubsRank moooiPrimeRank = new SubsRank("볼륨업", 73800, RankLevel.PRIME);
            SubsContent moooiPrimeContent1 = new SubsContent("계절감을 담은 6~8종의 꽃다발 (1.5배 더 풍성하게)");
            SubsContent moooiPrimeContent2 = new SubsContent("전용박스/모이화병(첫 배송시)/사진 엽서/플라워푸드");
            SubsContent moooiPrimeContent3 = new SubsContent("2주 1회 기준 가격");
            moooiPrimeContent1.addSubsRank(moooiPrimeRank);
            moooiPrimeContent2.addSubsRank(moooiPrimeRank);
            moooiPrimeContent3.addSubsRank(moooiPrimeRank);
            moooiPrimeRank.addSubs(moooi);

            em.persist(moooi);

            //식물5
            Subs honestflower = new Subs("어니스트플라워");
            honestflower.updateImage(subsImageUrl+"honestflower.png");

            honestflower.addCategory(plant);
            honestflower.addTag(woman);
            honestflower.addTag(age20);
            honestflower.addTag(age30);
            honestflower.addTag(plantTag);
            honestflower.addTag(flower);
            honestflower.addTag(event);

            honestflower.updateInfo("누구나 쉽게 좋은 꽃을\n" +
                    "\n" +
                    "꽃을 살 때, 아쉬웠던 적 없었나요?\n" +
                    "누구나 한 번쯤은 꽃을 살 때 너무 비싸다거나 너무 아깝다고 생각합니다. 그래서인지 꽃은 쉽게 소비되기보다는 특별한 날에 가끔 준비되는 선물로 여겨지죠. 특히 꽃집에 방문한 경험이 적거나, 꽃 구매가 처음인 분들은 이런 경험이 있을 겁니다.\n" +
                    "\"지금 이 상태면 좋은 품질이겠지? 크기와 양은 이 정도면 적당한 건가... 근데 이 가격은 너무 비싼 거 아니야!\"\n" +
                    "결국 합리적인 소비를 했다는 만족감이 적기 때문에, 꽃이 주는 행복을 온전히 다 느끼기가 어렵죠.\n" +
                    "\n" +
                    " 합리적인 가격으로 좋은 상품을 사는 것. 이 당연한 이야기를 꽃에서도 만들기 위해, 어니스트플라워에 모였습니다.");

            SubsRank honestflowerDefaultRank = new SubsRank("라이트 구독", 23800, RankLevel.DEFAULT);
            SubsContent honestflowerDefaultContent1 = new SubsContent("가벼운 가격으로 꽃을 곁에 두고 싶을 때");
            SubsContent honestflowerDefaultContent2 = new SubsContent("2-3종, 10대 이하");
            SubsContent honestflowerDefaultContent3 = new SubsContent("매 회차 플라워푸드 0원");
            SubsContent honestflowerDefaultContent4 = new SubsContent("2주 1회 기준 가격");
            honestflowerDefaultContent1.addSubsRank(honestflowerDefaultRank);
            honestflowerDefaultContent2.addSubsRank(honestflowerDefaultRank);
            honestflowerDefaultContent3.addSubsRank(honestflowerDefaultRank);
            honestflowerDefaultContent4.addSubsRank(honestflowerDefaultRank);
            honestflowerDefaultRank.addSubs(honestflower);

            SubsRank honestflowerPrimeRank = new SubsRank("오리지널 구독", 59800, RankLevel.PRIME);
            SubsContent honestflowerPrimeContent1 = new SubsContent("풍성하게 다양한 재료를 경험하고 싶을 때");
            SubsContent honestflowerPrimeContent2 = new SubsContent("3종 이상, 15대 이상");
            SubsContent honestflowerPrimeContent3 = new SubsContent("매 회차 플라워푸드 0원");
            SubsContent honestflowerPrimeContent4 = new SubsContent("2주 1회 기준 가격");
            honestflowerPrimeContent1.addSubsRank(honestflowerPrimeRank);
            honestflowerPrimeContent2.addSubsRank(honestflowerPrimeRank);
            honestflowerPrimeContent3.addSubsRank(honestflowerPrimeRank);
            honestflowerPrimeContent4.addSubsRank(honestflowerPrimeRank);
            honestflowerPrimeRank.addSubs(honestflower);


            em.persist(honestflower);


            //식물6
            Subs friendlyflower = new Subs("프렌들리플라워");
            friendlyflower.updateImage(subsImageUrl+"friendlyflower.png");

            friendlyflower.addCategory(plant);
            friendlyflower.addTag(woman);
            friendlyflower.addTag(age20);
            friendlyflower.addTag(age30);
            friendlyflower.addTag(plantTag);
            friendlyflower.addTag(flower);
            friendlyflower.addTag(event);

            friendlyflower.updateInfo("Friendly Flower는 나 혹은 사랑하는 가족과 함께하는 공간을 아름다운 풍경으로 채울 수 있도록 신선한 꽃을 전해드리는 FLower Brand Shop입니다.\n" +
                    "\n" +
                    "FRESH FROM THE FARM. 배송 당일 농장에서 온 신선한 제철꽃으로 제작한 상품을 고객님께 전해드립니다.\n" +
                    "SAFETY PACKAGING. 배송 과정 중 생길 수 있는 충격과 흔들림에 의한 꽃 손상을 최소화시키기 위해 고정틀과 보온팩을 사용하여, 안전하게 받아보실 수 있어요.\n" +
                    "PHOTO SERVICE. 사랑하는 사람을 위해 세심하게 고른 내 상품이 어떻게 전달될지 사진으로 받아보실 수 있어요.\n" +
                    "RESPONSIBILITY. 배송 중 사고로 인해 꽃의 상품가치가 훼손되었을 경우, 수령 당일에 고객센터로 사진과 함께 접수해 주시면 신속히 재배송 도와드리겠습니다.\n" +
                    "IMPORTANT OF DESIGN. 꽃과 함께하는 소중한 경험을 더욱 가치있게 하기 위해 지속적은 디자인 향상과 신중한 품질관리를 약속합니다.\n" +
                    "NATURE LOVE. 자연과 상생하는 식물·환경 사랑 실천. 환경오염을 줄일 수 있는 크라프트 포장지를 사용한 상품출시 등 다양한 방법을 통해 환경보호 활동에 함께합니다." +
                    "\n" +
                    "합리적인 가격으로 좋은 상품을 사는 것. 이 당연한 이야기를 꽃에서도 만들기 위해, 어니스트플라워에 모였습니다.");

            SubsRank friendlyflowerDefaultRank = new SubsRank("S size 꽃다발 구독", 50000, RankLevel.DEFAULT);
            SubsContent friendlyflowerDefaultContent1 = new SubsContent("20*35(cm) S size 꽃다발 정기배송");
            SubsContent friendlyflowerDefaultContent2 = new SubsContent("2주 1회 기준 가격");
            friendlyflowerDefaultContent1.addSubsRank(friendlyflowerDefaultRank);
            friendlyflowerDefaultContent2.addSubsRank(friendlyflowerDefaultRank);
            friendlyflowerDefaultRank.addSubs(friendlyflower);

            SubsRank friendlyflowerPrimeRank = new SubsRank("M size 꽃다발 구독", 80000, RankLevel.PRIME);
            SubsContent friendlyflowerPrimeContent1 = new SubsContent("25*45(cm) M size 꽃다발 정기배송");
            SubsContent friendlyflowerPrimeContent2 = new SubsContent("2주 1회 기준 가격");
            friendlyflowerPrimeContent1.addSubsRank(friendlyflowerPrimeRank);
            friendlyflowerPrimeContent2.addSubsRank(friendlyflowerPrimeRank);
            friendlyflowerPrimeRank.addSubs(friendlyflower);

            em.persist(friendlyflower);

            //식물7
            Subs rebekah = new Subs("레베카");
            rebekah.updateImage(subsImageUrl+"rebekah.png");

            rebekah.addCategory(plant);
            rebekah.addTag(woman);
            rebekah.addTag(age40);
            rebekah.addTag(age50);
            rebekah.addTag(plantTag);
            rebekah.addTag(flower);

            rebekah.updateInfo("레베카는 성경에 나오는 리브가의 영어식 이름입니다. 리브가는 아브라함의 아들 이삭(Issac)의 아내입니다. 나그네가 마실 물을 청하자 나그네의 낙타들에게까지 마실 물을 제공합니다. 배려와 사랑이 넉넉한 아름다운 여인을 의미합니다. 레베카는 당신을 위해 최상의 먹거리를 공인 최저가로 제공하는 동시에, 수익금 전액으로 열방에 영원한 복음을 전하는 텐트메이커입니다. 레베카를 통해, 나를 위한 소비에서 끝나지 않고 나의 소비가 가난한 이웃까지 돌볼 수 있게 됩니다. 레베카의 삶으로 초대합니다.\n" +
                    "\n" +
                    "계절에 따라 매월 새로운 꽃을 받아보시게 됩니다. 독일 플로리스트가 선정, 디자인한 꽃다발이 안전하게 포장되어 집 앞으로 배송됩니다. 정기구독시 년 4회는 농장직송 스페셜 꽃다발도 받아보실 수 있습니다.\n" +
                    "꽃 정기구독을 통해 수익금은 소외된 이웃을 돕고, 전세계 성경보내는 일에 사용됩니다. 물론 어려움을 겪고 있는 화훼농가에도 큰 도움이 됩니다.");

            SubsRank rebekahDefaultRank = new SubsRank("레베카 꽃 정기구독", 18600, RankLevel.DEFAULT);
            SubsContent rebekahDefaultContent1 = new SubsContent("매월 셋째 주 꽃다발 정기배송");
            SubsContent rebekahDefaultContent2 = new SubsContent("7~8가지 꽃 종류, 꽃의 길이는 31~35cm");
            SubsContent rebekahDefaultContent3 = new SubsContent("한달 1회 기준 가격");
            rebekahDefaultContent1.addSubsRank(rebekahDefaultRank);
            rebekahDefaultContent2.addSubsRank(rebekahDefaultRank);
            rebekahDefaultContent3.addSubsRank(rebekahDefaultRank);
            rebekahDefaultRank.addSubs(rebekah);

            em.persist(rebekah);


            //교통1
            Subs hyundai = new Subs("현대셀렉션");
            hyundai.updateImage(subsImageUrl+"hyundai.png");

            hyundai.addCategory(ride);
            hyundai.addTag(man);
            hyundai.addTag(age30);
            hyundai.addTag(age40);
            hyundai.addTag(rideTag);
            hyundai.addTag(car);

            hyundai.updateInfo("이제는 자동차도 무제한 스트리밍해보세요.\n" +
                    "현대셀렉션 레귤러팩(Regular Pack)에서는 현대자동차의 다양한 차종 중에서 원하는 차를 골라 무제한으로 이용할 수 있습니다. 월 단위 요금제에 따라 월 2회 교체도 가능하고, 친구 또는 가족과 함께 이용할 수도 있습니다.");

            SubsRank hyundaiDefaultRank = new SubsRank("Entry", 490000, RankLevel.DEFAULT);
            SubsContent hyundaiDefaultContent1 = new SubsContent("선택 가능 차종 : 캐스퍼");
            SubsContent hyundaiDefaultContent2 = new SubsContent("주행 거리 : 무제한");
            SubsContent hyundaiDefaultContent3 = new SubsContent("선납금/위약금 : 없음");
            SubsContent hyundaiDefaultContent4 = new SubsContent("첫 달 이후 자유 해지 : 가능");
            hyundaiDefaultContent1.addSubsRank(hyundaiDefaultRank);
            hyundaiDefaultContent2.addSubsRank(hyundaiDefaultRank);
            hyundaiDefaultContent3.addSubsRank(hyundaiDefaultRank);
            hyundaiDefaultContent4.addSubsRank(hyundaiDefaultRank);
            hyundaiDefaultRank.addSubs(hyundai);

            SubsRank hyundaiPrimeRank = new SubsRank("Standard", 750000, RankLevel.PRIME);
            SubsContent hyundaiPrimeContent1 = new SubsContent("선택 가능 차종 : 쏘나타, 투싼, 아반떼, 베뉴");
            SubsContent hyundaiPrimeContent2 = new SubsContent("주행 거리 : 무제한");
            SubsContent hyundaiPrimeContent3 = new SubsContent("선납금/위약금 : 없음");
            SubsContent hyundaiPrimeContent4 = new SubsContent("첫 달 이후 자유 해지 : 가능");
            SubsContent hyundaiPrimeContent5 = new SubsContent("스폐셜 팩 할인 : 월 1회 50% 할인");
            SubsContent hyundaiPrimeContent6 = new SubsContent("지역 간 구독 로밍 : 내륙 타지역 스페셜팩 무료 이용권 월 1회 제공 / 제주 지역 스페셜팩 50% 할인권 월 1회 제공");
            SubsContent hyundaiPrimeContent7 = new SubsContent("차량 교체 가능 : 월 1회");
            SubsContent hyundaiPrimeContent8 = new SubsContent("사용자 추가 : +1인");
            hyundaiPrimeContent1.addSubsRank(hyundaiPrimeRank);
            hyundaiPrimeContent2.addSubsRank(hyundaiPrimeRank);
            hyundaiPrimeContent3.addSubsRank(hyundaiPrimeRank);
            hyundaiPrimeContent4.addSubsRank(hyundaiPrimeRank);
            hyundaiPrimeContent5.addSubsRank(hyundaiPrimeRank);
            hyundaiPrimeContent6.addSubsRank(hyundaiPrimeRank);
            hyundaiPrimeContent7.addSubsRank(hyundaiPrimeRank);
            hyundaiPrimeContent8.addSubsRank(hyundaiPrimeRank);
            hyundaiPrimeRank.addSubs(hyundai);


            SubsRank hyundaiPremiumRank = new SubsRank("Premium", 990000, RankLevel.PREMIUM);
            SubsContent hyundaiPremiumContent1 = new SubsContent("선택 가능 차종 : 그랜저, 팰리세이드, 싼타페 캘리그래피, 쏘나타, 투싼, 아반떼, 베뉴");
            SubsContent hyundaiPremiumContent2 = new SubsContent("주행 거리 : 무제한");
            SubsContent hyundaiPremiumContent3 = new SubsContent("선납금/위약금 : 없음");
            SubsContent hyundaiPremiumContent4 = new SubsContent("첫 달 이후 자유 해지 : 가능");
            SubsContent hyundaiPremiumContent5 = new SubsContent("스폐셜 팩 할인 : 월 1회 50% 할인");
            SubsContent hyundaiPremiumContent6 = new SubsContent("지역 간 구독 로밍 : 내륙 타지역 스페셜팩 무료 이용권 월 1회 제공 / 제주 지역 스페셜팩 50% 할인권 월 1회 제공");
            SubsContent hyundaiPremiumContent7 = new SubsContent("차량 교체 가능 : 월 2회");
            SubsContent hyundaiPremiumContent8 = new SubsContent("사용자 추가 : +2인");
            hyundaiPremiumContent1.addSubsRank(hyundaiPremiumRank);
            hyundaiPremiumContent2.addSubsRank(hyundaiPremiumRank);
            hyundaiPremiumContent3.addSubsRank(hyundaiPremiumRank);
            hyundaiPremiumContent4.addSubsRank(hyundaiPremiumRank);
            hyundaiPremiumContent5.addSubsRank(hyundaiPremiumRank);
            hyundaiPremiumContent6.addSubsRank(hyundaiPremiumRank);
            hyundaiPremiumContent7.addSubsRank(hyundaiPremiumRank);
            hyundaiPremiumContent8.addSubsRank(hyundaiPremiumRank);
            hyundaiPremiumRank.addSubs(hyundai);

            em.persist(hyundai);

            //교통2
            Subs mycaro = new Subs("카로");
            mycaro.updateImage(subsImageUrl+"mycaro.png");

            mycaro.addCategory(ride);
            mycaro.addTag(man);
            mycaro.addTag(age20);
            mycaro.addTag(age30);
            mycaro.addTag(rideTag);
            mycaro.addTag(car);

            mycaro.updateInfo("카로에서 인생 자동차를 만나다.\n" +
                    "\"다양하게\" 더 많은 수입차를 경험할 수 있도록 포르쉐부터 벤츠까지 다양하게 준비했어요. 타고 싶은 차량을 바꿔가며 이용하세요.\n" +
                    "\"자유롭게\" 자동차가 필요한 순간이라면 언제든지, 하루부터 장기까지 원하는 만큼 자유롭게 타세요.\n" +
                    "\"편리하게\" 앱으로 신청하면 바로 운전할 수 있도록 다음 날 집 앞으로 배송해드려요.\n" +
                    "\"즐겁게\" 차에 대해 잘 몰라도 즐겁게 운행할 수 있도록 차량 정비, 보험 등 번거로운 일은 카로가 대신할게요.");

            SubsRank mycaroDefaultRank = new SubsRank("카로3", 1050000, RankLevel.DEFAULT);
            SubsContent mycaroDefaultContent1 = new SubsContent("요금제 적용 차종 : 벤츠C200, 벤츠GLB220, 벤츠CLA250, BMW 320d, 랜드로버 디스커버리스포츠");
            SubsContent mycaroDefaultContent2 = new SubsContent("매 1개월마다 구독기간 자동 갱신, 첫 가입 시 가입비 120만원 별도");
            mycaroDefaultContent1.addSubsRank(mycaroDefaultRank);
            mycaroDefaultContent2.addSubsRank(mycaroDefaultRank);
            mycaroDefaultRank.addSubs(mycaro);

            SubsRank mycaroPrimeRank = new SubsRank("카로5", 1270000, RankLevel.PRIME);
            SubsContent mycaroPrimeContent1 = new SubsContent("요금제 적용 차종 : 벤츠E250/E220d, 벤츠GLC220d/300, BMW 520d, BMW X4");
            SubsContent mycaroPrimeContent2 = new SubsContent("매 1개월마다 구독기간 자동 갱신, 첫 가입 시 가입비 120만원 별도");
            mycaroPrimeContent1.addSubsRank(mycaroPrimeRank);
            mycaroPrimeContent2.addSubsRank(mycaroPrimeRank);
            mycaroPrimeRank.addSubs(mycaro);

            SubsRank mycaroPremiumRank = new SubsRank("카로7", 2070000, RankLevel.PREMIUM);
            SubsContent mycaroPremiumContent1 = new SubsContent("요금제 적용 차종 : 벤츠CLS300d, 벤츠GLE300d, BMW X5, 레인지로버 벨라 D300");
            SubsContent mycaroPremiumContent2 = new SubsContent("매 1개월마다 구독기간 자동 갱신, 첫 가입 시 가입비 120만원 별도");
            mycaroPremiumContent1.addSubsRank(mycaroPremiumRank);
            mycaroPremiumContent2.addSubsRank(mycaroPremiumRank);
            mycaroPremiumRank.addSubs(mycaro);

            em.persist(mycaro);

            //교통3
            Subs thetrive = new Subs("트라이브");
            thetrive.updateImage(subsImageUrl+"thetrive.png");

            thetrive.addCategory(ride);
            thetrive.addTag(man);
            thetrive.addTag(age30);
            thetrive.addTag(age40);
            thetrive.addTag(rideTag);
            thetrive.addTag(car);

            thetrive.updateInfo("트라이브 자동차 구독 서비스는 매월 정해진 금액을 지불하고 일정 기간 자동차를 이용하는 서비스에요.\n" +
                    "구독을 이용하는 동안 안전하게 이용하실 수 있도록 정기 안전 점검과 사고/고장/경고등 점등 시 빠른 상담을 해드리고 있어요. 또 쾌적하고 원활한 운행을 위해 매월 방문 세차와 소모품 무상 교환(엔진오일 등)을 제공하고 있어요. 구독을 신청하면 자동차를 받은 날을 기준으로 매월 선불로 결제돼요. 구독이 시작되면 약정기간은 1년이지만 7개월 차부터는 언제든 중도해지 위약금이 없이 구독을 종료할 수 있어요. 단, 구독을 종료하실 때는 14일 전에 미리 알려주셔야 해요.");

            SubsRank thetriveDefaultRank = new SubsRank("Mercedes-Benz E 200 Avantgarde", 990000, RankLevel.DEFAULT);
            SubsContent thetriveDefaultContent1 = new SubsContent("약정 운행거리 : 20000km");
            SubsContent thetriveDefaultContent2 = new SubsContent("구독 옵션 : 6~12개월");
            SubsContent thetriveDefaultContent3 = new SubsContent("고장/수리 : 트라이브 보증");
            SubsContent thetriveDefaultContent4 = new SubsContent("정기 점검 : 4, 8개월 차 제공");
            SubsContent thetriveDefaultContent5 = new SubsContent("세차 : 매월 방문 세차");
            thetriveDefaultContent1.addSubsRank(thetriveDefaultRank);
            thetriveDefaultContent2.addSubsRank(thetriveDefaultRank);
            thetriveDefaultContent3.addSubsRank(thetriveDefaultRank);
            thetriveDefaultContent4.addSubsRank(thetriveDefaultRank);
            thetriveDefaultContent5.addSubsRank(thetriveDefaultRank);
            thetriveDefaultRank.addSubs(thetrive);

            SubsRank thetrivePrimeRank = new SubsRank("Mercedes-Benz E 220d Cabriolet", 1590000, RankLevel.PRIME);
            SubsContent thetrivePrimeContent1 = new SubsContent("약정 운행거리 : 20000km");
            SubsContent thetrivePrimeContent2 = new SubsContent("구독 옵션 : 6~12개월");
            SubsContent thetrivePrimeContent3 = new SubsContent("고장/수리 : 트라이브 보증");
            SubsContent thetrivePrimeContent4 = new SubsContent("정기 점검 : 4, 8개월 차 제공");
            SubsContent thetrivePrimeContent5 = new SubsContent("세차 : 매월 방문 세차");
            thetrivePrimeContent1.addSubsRank(thetrivePrimeRank);
            thetrivePrimeContent2.addSubsRank(thetrivePrimeRank);
            thetrivePrimeContent3.addSubsRank(thetrivePrimeRank);
            thetrivePrimeContent4.addSubsRank(thetrivePrimeRank);
            thetrivePrimeContent5.addSubsRank(thetrivePrimeRank);
            thetrivePrimeRank.addSubs(thetrive);

            SubsRank thetrivePremiumRank = new SubsRank("Porsche", 2590000, RankLevel.PREMIUM);
            SubsContent thetrivePremiumContent1 = new SubsContent("약정 운행거리 : 20000km");
            SubsContent thetrivePremiumContent2 = new SubsContent("구독 옵션 : 6~12개월");
            SubsContent thetrivePremiumContent3 = new SubsContent("고장/수리 : 트라이브 보증");
            SubsContent thetrivePremiumContent4 = new SubsContent("정기 점검 : 4, 8개월 차 제공");
            SubsContent thetrivePremiumContent5 = new SubsContent("세차 : 매월 방문 세차");
            thetrivePremiumContent1.addSubsRank(thetrivePremiumRank);
            thetrivePremiumContent2.addSubsRank(thetrivePremiumRank);
            thetrivePremiumContent3.addSubsRank(thetrivePremiumRank);
            thetrivePremiumContent4.addSubsRank(thetrivePremiumRank);
            thetrivePremiumContent5.addSubsRank(thetrivePremiumRank);
            thetrivePremiumRank.addSubs(thetrive);
            em.persist(thetrive);

            //교통4
            Subs bikeseoul = new Subs("따릉이 정기권");
            bikeseoul.updateImage(subsImageUrl+"bikeseoul.png");

            bikeseoul.addCategory(ride);
            bikeseoul.addTag(man);
            bikeseoul.addTag(woman);
            bikeseoul.addTag(age20);
            bikeseoul.addTag(age30);
            bikeseoul.addTag(rideTag);
            bikeseoul.addTag(bicycle);

            bikeseoul.updateInfo("누구나 쉽고 편리하게 이용할 수 있는 저탄소 이동수단, 서울자전거 따릉이.\n" +
                    "\n" +
                    "건강한 자전거 도시! 자전거 이용의 생활화를 통한 시민 건강 증진 실현.\n" +
                    "깨끗한 자전거 도시! 자전거 교통수단 분담률을 향상시켜 CO2 발생 감소 실현.\n" +
                    "녹색 성장 선도 도시! 국가 비전인 \"저탄소 녹색 성장\" 실현.\n" +
                    "편리한 자전거 도시! 곳곳에 배치된 자전거로 이동 편리성 증진 실현.");

            SubsRank bikeseoulDefaultRank = new SubsRank("일반권(60분)", 5000, RankLevel.DEFAULT);
            SubsContent bikeseoulDefaultContent1 = new SubsContent("1회 대여 시 기본 대여시간은 60(분)입니다. 60(분)을 초과하는 경우 추가요금을 지불해야하므로 반납 후 다시 대여하여 이용하세요.");
            SubsContent bikeseoulDefaultContent2 = new SubsContent("30일권 기준 금액입니다.");
            bikeseoulDefaultContent1.addSubsRank(bikeseoulDefaultRank);
            bikeseoulDefaultContent2.addSubsRank(bikeseoulDefaultRank);
            bikeseoulDefaultRank.addSubs(bikeseoul);

            SubsRank bikeseoulPrimeRank = new SubsRank("프리미엄권(120분)", 7000, RankLevel.PRIME);
            SubsContent bikeseoulPrimeContent1 = new SubsContent("1회 대여 시 기본 대여시간은 120(분)입니다. 120(분)을 초과하는 경우 추가요금을 지불해야하므로 반납 후 다시 대여하여 이용하세요.");
            SubsContent bikeseoulPrimeContent2 = new SubsContent("30일권 기준 금액입니다.");
            bikeseoulPrimeContent1.addSubsRank(bikeseoulPrimeRank);
            bikeseoulPrimeContent2.addSubsRank(bikeseoulPrimeRank);
            bikeseoulPrimeRank.addSubs(bikeseoul);
            em.persist(bikeseoul);

            //건강뷰티1
            Subs dongitoothPaste = new Subs("동이오감 고체치약");
            dongitoothPaste.updateImage(subsImageUrl+"dongitoothPaste.png");

            dongitoothPaste.addCategory(health);
            dongitoothPaste.addTag(man);
            dongitoothPaste.addTag(woman);
            dongitoothPaste.addTag(age20);
            dongitoothPaste.addTag(age30);
            dongitoothPaste.addTag(healthTag);
            dongitoothPaste.addTag(toothPaste);
            dongitoothPaste.addTag(event);

            dongitoothPaste.updateInfo("엄선된 생약 중심의 원료로 완성한 안심 고체 치약.\n" +
                    "\n" +
                    "사라지지 않는 입 냄새의 원인, 양치 후 잠든 사이 입을 마르게 하여 구취 유발 세균이 더욱 왕성해지고 입냄새가 악화될 수 있소.\n" +
                    "동이오감 고체 치약의 솔루션! 이오 오리지널 고체 치약은 구취 제거 및 구강내를 상쾌하게 하여 아침 입냄새를 개선시킬 수 있소!");

            SubsRank dongitoothPasteDefaultRank = new SubsRank("고체 치약 1인 세트", 14400, RankLevel.DEFAULT);
            SubsContent dongitoothPasteDefaultContent1 = new SubsContent("고체 치약 리필형 150정*1개 + 미세모 칫솔 1개");
            dongitoothPasteDefaultContent1.addSubsRank(dongitoothPasteDefaultRank);
            dongitoothPasteDefaultRank.addSubs(dongitoothPaste);

            SubsRank dongitoothPastePrimeRank = new SubsRank("고체 치약 2인 커플 세트", 27300, RankLevel.PRIME);
            SubsContent dongitoothPastePrimeContent1 = new SubsContent("고체 치약 리필형 150정*2개 + 미세모 칫솔 2개");
            dongitoothPastePrimeContent1.addSubsRank(dongitoothPastePrimeRank);
            dongitoothPastePrimeRank.addSubs(dongitoothPaste);

            SubsRank dongitoothPastePremiumRank = new SubsRank("고체 치약 4인 패밀리 세트", 48900, RankLevel.PREMIUM);
            SubsContent dongitoothPastePremiumContent1 = new SubsContent("고체 치약 리필형 150정*4개 + 미세모 칫솔 4개");
            dongitoothPastePremiumContent1.addSubsRank(dongitoothPastePremiumRank);
            dongitoothPastePremiumRank.addSubs(dongitoothPaste);

            em.persist(dongitoothPaste);

            //건강뷰티2
            Subs toun28 = new Subs("톤28");
            toun28.updateImage(subsImageUrl+"toun28.png");

            toun28.addCategory(health);
            toun28.addTag(woman);
            toun28.addTag(age20);
            toun28.addTag(age30);
            toun28.addTag(healthTag);
            toun28.addTag(cosmetic);

            toun28.updateInfo("균형잡힌 식단이 건강한 몸을 만들 듯 균형 잡힌 성분이 건강한 피부를 만듭니다.\n" +
                    "피부는 매달 달라지는데, 같은 화장품을 구입하는 것이 맞을까요? 기초 스킨케어 만큼은 선호나 감성이 아니라 진단 결과에 따라 빅데이터와 예측분석에 의해 제조되어야 합니다.\n" +
                    "\n" +
                    "프리미엄 제철 원료의 좋은 성분만 선정한 가장 신선한 맞춤 바를거리.\n" +
                    "피부가 필요로 하는 영양성분을 과학적으로 분석하여 매달 균형 잡힌 성분을 배송합니다. 톤28은 최상의 재료로 신선하게 맞춤 바를거리를 빚어냅니다.");

            SubsRank toun28DefaultRank = new SubsRank("톤28 맞춤 구독 Standard", 39000, RankLevel.DEFAULT);
            SubsContent toun28DefaultContent1 = new SubsContent("톤TOUN, 부위별 맞춤");
            SubsContent toun28DefaultContent2 = new SubsContent("28일 신선 주기 구독");
            SubsContent toun28DefaultContent3 = new SubsContent("유기농/자연/자연유래성분");
            SubsContent toun28DefaultContent4 = new SubsContent("주문 후 신선 제조");
            SubsContent toun28DefaultContent5 = new SubsContent("빅데이터 알고리즘 반영");
            SubsContent toun28DefaultContent6 = new SubsContent("이니셜 가죽 각인");
            SubsContent toun28DefaultContent7 = new SubsContent("일반 제품 월 1개 선택 혜택(회차별 차등 혜택)");
            SubsContent toun28DefaultContent8 = new SubsContent("월 정기결제, 1부위 기준");
            toun28DefaultContent1.addSubsRank(toun28DefaultRank);
            toun28DefaultContent2.addSubsRank(toun28DefaultRank);
            toun28DefaultContent3.addSubsRank(toun28DefaultRank);
            toun28DefaultContent4.addSubsRank(toun28DefaultRank);
            toun28DefaultContent5.addSubsRank(toun28DefaultRank);
            toun28DefaultContent6.addSubsRank(toun28DefaultRank);
            toun28DefaultContent7.addSubsRank(toun28DefaultRank);
            toun28DefaultContent8.addSubsRank(toun28DefaultRank);
            toun28DefaultRank.addSubs(toun28);

            SubsRank toun28PrimeRank = new SubsRank("톤28 맞춤 구독 Premium",100000 , RankLevel.PRIME);
            SubsContent toun28PrimeContent1 = new SubsContent("Standard 서비스 기본 제공");
            SubsContent toun28PrimeContent2 = new SubsContent("피부 정기 케어");
            SubsContent toun28PrimeContent3 = new SubsContent("프리미엄 성분 함유");
            SubsContent toun28PrimeContent4 = new SubsContent("바를거리 연구소장 처방 확정");
            SubsContent toun28PrimeContent5 = new SubsContent("스탠다드 L4 등급에 상응하는 혜택");
            SubsContent toun28PrimeContent6 = new SubsContent("프리미엄을 위한 추가 혜택은 별도 문의");

            toun28PrimeContent1.addSubsRank(toun28PrimeRank);
            toun28PrimeContent2.addSubsRank(toun28PrimeRank);
            toun28PrimeContent3.addSubsRank(toun28PrimeRank);
            toun28PrimeContent4.addSubsRank(toun28PrimeRank);
            toun28PrimeContent5.addSubsRank(toun28PrimeRank);
            toun28PrimeContent6.addSubsRank(toun28PrimeRank);

            toun28PrimeRank.addSubs(toun28);
            em.persist(toun28);

            //건강뷰티3
            Subs philli = new Subs("필리 영양제");
            philli.updateImage(subsImageUrl+"philli.png");

            philli.addCategory(health);
            philli.addTag(woman);
            philli.addTag(age20);
            philli.addTag(age30);
            philli.addTag(healthTag);
            philli.addTag(supplements);
            philli.addTag(event);

            philli.updateInfo("AI영양추천 받고 구매 시 3000원 할인!\n" +
                    "필리 영양제 정기구독할 때 AI 영양추천 통해 추천받고 구독신청하시면 바로 사용가능한 3000원 할인쿠폰을 드려요.\n" +
                    "\n" +
                    "정기결제 시 3000원 할인!\n" +
                    "지정된 회차에 정기결제 시 할인쿠폰을 드려요. 회차가 늘어날 수록 쿠폰 금액도 높아진다는 사실! (10회차에는 무려 7000원!)\n" +
                    "\n" +
                    "친구 초대할 때마다 5000원 할인!\n" +
                    "한 명 초대할 때마다 2000포인트에 5000원의 할인쿠폰까지 드려요.");

            SubsRank philliDefaultRank = new SubsRank("이뮨밸런스 : 롤러코스터 하차 선언", 19500, RankLevel.DEFAULT);
            SubsContent philliDefaultContent1 = new SubsContent("비타민D, 아연을 비롯해 여성 건강에 도움이 되는 베리 5종까지 7종을 배합해 만든 구미 제형의 건강기능식품");
            SubsContent philliDefaultContent2 = new SubsContent("월경으로 인한 불편감 완화에 도움이 될 수 있습니다");
            philliDefaultContent1.addSubsRank(philliDefaultRank);
            philliDefaultContent2.addSubsRank(philliDefaultRank);
            philliDefaultRank.addSubs(philli);


            SubsRank philliPrimeRank = new SubsRank("P콰이엇 : 거침없이 이별 통보", 29500, RankLevel.PRIME);
            SubsContent philliPrimeContent1 = new SubsContent("여성 건강에 도움이 되는 13종 원료를 과학적으로 배합하고, 고함량 영양성분을 간편하게 맛있게 섭취할 수 있는 레시피");
            SubsContent philliPrimeContent2 = new SubsContent("복합적인 월경 불편감을 완화하는데 도움이 될 수 있습니다");
            philliPrimeContent1.addSubsRank(philliPrimeRank);
            philliPrimeContent2.addSubsRank(philliPrimeRank);
            philliPrimeRank.addSubs(philli);

            SubsRank philliPremiumRank = new SubsRank("피스&프리 : 그날의 극적 화해", 32500, RankLevel.PREMIUM);
            SubsContent philliPremiumContent1 = new SubsContent("최대 함량 감마리놀렌산(300mg)과 비타민D, 비타민E, 여성 건강 부원료의 특화 배합");
            SubsContent philliPremiumContent2 = new SubsContent("월경과의 애증 관계를 종결할 여성 건강 특화 레시피입니다");
            philliPremiumContent1.addSubsRank(philliPremiumRank);
            philliPremiumContent2.addSubsRank(philliPremiumRank);
            philliPremiumRank.addSubs(philli);

            em.persist(philli);

            //건강뷰티4
            Subs mypuzzle = new Subs("마이퍼즐");
            mypuzzle.updateImage(subsImageUrl+"mypuzzle.png");

            mypuzzle.addCategory(health);
            mypuzzle.addTag(man);
            mypuzzle.addTag(woman);
            mypuzzle.addTag(age40);
            mypuzzle.addTag(age50);
            mypuzzle.addTag(age60);
            mypuzzle.addTag(healthTag);
            mypuzzle.addTag(supplements);

            mypuzzle.updateInfo("오직 한 사람을 위한 맞춤 건강기능식품. 나만의 건강 한 조각, 마이퍼즐.\n" +
                    "\n" +
                    "모두가 좋다고 하는 영양제, 과연 나에게도 진짜 필요한걸까요?\n" +
                    "우리는 생활 환경, 식습관, 건강 상태에 따라 각자 다른 건강 관리가 필요합니다. 마이퍼즐은 고객 한 분, 한 분에 맞춘 건강기능식품이 필요하다는 생각으로부터 출발했습니다. 마지막 한 조각으로 완성되는 퍼즐처럼, 개인의 건강도 나에게 꼭 맞는 한 조각이 필요합니다. 마이퍼즐은 분석합니다. 건강과 영양을 제일 잘 아는 의사, 약사, 영양학 박사로 구성된 마이퍼즐 건강 자문단과 함께 최적화된 맞춤 설계를 완성했습니다.\n" +
                    "이제 마이퍼즐이 당신의 건강 한 조각을 빈틈없이 채워드릴게요.");

            SubsRank mypuzzleDefaultRank = new SubsRank("[종합기초건강] 트리플엑스 127", 25600, RankLevel.DEFAULT);
            SubsContent mypuzzleDefaultContent1 = new SubsContent("영양보충 & 뼈건강 & 혈행건강을 한번에!");
            SubsContent mypuzzleDefaultContent2 = new SubsContent("알티지 오메가3(EPA 및 DHA 함유유지) 500 mg으로 혈행을!");
            SubsContent mypuzzleDefaultContent3 = new SubsContent("종합비타민 12종, 미네랄 7종으로 영양보충을!");
            SubsContent mypuzzleDefaultContent4 = new SubsContent("칼슘과 마그네슘도 함께 뼈와 근육 건강까지!");
            mypuzzleDefaultContent1.addSubsRank(mypuzzleDefaultRank);
            mypuzzleDefaultContent2.addSubsRank(mypuzzleDefaultRank);
            mypuzzleDefaultContent3.addSubsRank(mypuzzleDefaultRank);
            mypuzzleDefaultContent4.addSubsRank(mypuzzleDefaultRank);
            mypuzzleDefaultRank.addSubs(mypuzzle);


            SubsRank mypuzzlePrimeRank = new SubsRank("[활력] 멀티비타민 미네랄 139", 13600, RankLevel.PRIME);
            SubsContent mypuzzlePrimeContent1 = new SubsContent("13종 멀티비타민 & 9종의 미네랄. 기초 건강에 필요한 총 22종의 영양소를 하루 한 정으로 챙기세요!");
            mypuzzlePrimeContent1.addSubsRank(mypuzzlePrimeRank);
            mypuzzlePrimeRank.addSubs(mypuzzle);

            SubsRank mypuzzlePremiumRank = new SubsRank("[노화 인지력 개선] 브레인알파 피에스", 40000, RankLevel.PREMIUM);
            SubsContent mypuzzlePremiumContent1 = new SubsContent("스스로 지키는 두뇌건강! 노화로 인해 저하된 인지력 개선은 포스파티딜세린!");
            mypuzzlePremiumContent1.addSubsRank(mypuzzlePremiumRank);
            mypuzzlePremiumRank.addSubs(mypuzzle);

            em.persist(mypuzzle);

            //건강뷰티5
            Subs balanceFix = new Subs("발란스 맞춤형화장품");
            balanceFix.updateImage(subsImageUrl+"balanceFix.png");

            balanceFix.addCategory(health);
            balanceFix.addTag(man);
            balanceFix.addTag(woman);
            balanceFix.addTag(age40);
            balanceFix.addTag(age50);
            balanceFix.addTag(age60);
            balanceFix.addTag(healthTag);
            balanceFix.addTag(supplements);

            balanceFix.updateInfo("다시 찾은 유수분 밸런스.\n" +
                    "자연을 생각한, 업사이클링 원료. 상품성이 떨어지는 못난이 원물을 더 가치 있게 하는 업사이클링 원료.\n" +
                    "\n" +
                    "진단 정확도 98.3%, 특허 기술 검증에 전문가가 함께 참여하여 정확도가 높아지는 자가 진단으로 만드는 맞춤형 화장품.\n" +
                    "피부의 언어를 이해하다. 지문처럼 저마다 다른 피부 성격을 40가지로 정의한 피부 MBTI, 빅데이터와 알고리즘 기반의 과학적 분석.\n" +
                    "단 한 병의 맞춤 레시피. 수만 가지 중 내 피부를 이해한 최적의 추천 조합, 그리고 취향을 고려한 비스포크 시스템.");

            SubsRank balanceFixDefaultRank = new SubsRank("발란스 맞춤형 로션 28일분 (30ml)", 33000, RankLevel.DEFAULT);
            SubsContent balanceFixDefaultContent1 = new SubsContent("피부에게 주는 깊은 휴식과 보습, 업사이클링 원료인 청정 제주 편백수를 가득담은 맞춤 로션 28일분 (30ml)");
            SubsContent balanceFixDefaultContent2 = new SubsContent("주요 성분 : 제주 편백수");
            SubsContent balanceFixDefaultContent3 = new SubsContent("피부타입 : 지성/중복합성(라이트),중복합성/건성(헤비)");
            SubsContent balanceFixDefaultContent4 = new SubsContent("향 : 무향");
            balanceFixDefaultContent1.addSubsRank(balanceFixDefaultRank);
            balanceFixDefaultContent2.addSubsRank(balanceFixDefaultRank);
            balanceFixDefaultContent3.addSubsRank(balanceFixDefaultRank);
            balanceFixDefaultContent4.addSubsRank(balanceFixDefaultRank);
            balanceFixDefaultRank.addSubs(balanceFix);


            SubsRank balanceFixPrimeRank = new SubsRank("발란스 맞춤형 에센스 28일분 (30ml)", 36000, RankLevel.PRIME);
            SubsContent balanceFixPrimeContent1 = new SubsContent("피부에게 주는 깊은 휴식과 보습, 업사이클링 원료인 청정 제주 편백수를 가득담은 맞춤 에센스 28일분 (30ml)");
            SubsContent balanceFixPrimeContent2 = new SubsContent("주요 성분 : 제주 편백수");
            SubsContent balanceFixPrimeContent3 = new SubsContent("피부타입 : 지성(라이트),모든 피부(미디움),헤비(건성)");
            SubsContent balanceFixPrimeContent4 = new SubsContent("향 : 무향");
            balanceFixPrimeContent1.addSubsRank(balanceFixPrimeRank);
            balanceFixPrimeContent2.addSubsRank(balanceFixPrimeRank);
            balanceFixPrimeContent3.addSubsRank(balanceFixPrimeRank);
            balanceFixPrimeContent4.addSubsRank(balanceFixPrimeRank);
            balanceFixPrimeRank.addSubs(balanceFix);

            SubsRank balanceFixPremiumRank = new SubsRank("발란스 맞춤형 스킨 토너 28일분 (250ml)", 43000, RankLevel.PREMIUM);
            SubsContent balanceFixPremiumContent1 = new SubsContent("피부에게 주는 깊은 휴식과 보습, 업사이클링 원료인 청정 제주 편백수를 가득담은 맞춤 에센스 28일분 (30ml)");
            SubsContent balanceFixPremiumContent2 = new SubsContent("주요 성분 : 제주 편백수, 병풀추출물, 무화과추출물");
            SubsContent balanceFixPremiumContent3 = new SubsContent("피부타입 : 지성(라이트),중복합성(미디움),건성(헤비)");
            SubsContent balanceFixPremiumContent4 = new SubsContent("향 : 무향");

            balanceFixPremiumContent1.addSubsRank(balanceFixPremiumRank);
            balanceFixPremiumContent2.addSubsRank(balanceFixPremiumRank);
            balanceFixPremiumContent3.addSubsRank(balanceFixPremiumRank);
            balanceFixPremiumContent4.addSubsRank(balanceFixPremiumRank);
            balanceFixPremiumRank.addSubs(balanceFix);

            em.persist(balanceFix);

            //이벤트1
            EventSubs appleMusicEvent = new EventSubs(appleMusic);
            appleMusicEvent.updateEventSubs("1개월 무료",
                    "1억 곡의 음악 및 전문가가 엄선한 30,000개 이상의 플레이리스트",
                    LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()),
                    LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()),
                    eventImageUrl+"appleMusicEvent.png",
                    true
            );
            em.persist(appleMusicEvent);


            //이벤트 2 등록
            EventSubs closetShareEvent = new EventSubs(closetshare);
            closetShareEvent.updateEventSubs("첫달 특가",
                    "쇼핑보다 쉬운 셰어링의 편리함을 경험하세요.",
                    LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()),
                    LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()),
                    eventImageUrl+"closetShareEvent.png",
                    true
                    );
            em.persist(closetShareEvent);


            //이벤트3
            EventSubs kukkaEvent = new EventSubs(kukka);
            kukkaEvent.updateEventSubs("15% 할인",
                    "특별 계절 꽃다발 구독 최대 15% 즉시 할인!",
                    LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()),
                    LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()),
                    eventImageUrl+"kukkaEvent.png",
                    true
            );
            em.persist(kukkaEvent);

//
//
//
//            Subs dosirak = new Subs("dosirak");
//            Subs healthcare2 = new Subs("healthCare2");
//            Subs netfilx = new Subs("netfilx");
//            Subs melon = new Subs("melon");
//            Subs watcha = new Subs("watcha");
//
//
//
//            Subs movie1 = new Subs("스마트비디오");
//            Subs movie2 = new Subs("클립비젼");
//            Subs movie3 = new Subs("월드픽");
//            Subs movie4 = new Subs("무비스톤");
//            Subs movie5 = new Subs("뷰온");
//            Subs movie6 = new Subs("메디앙");
//            Subs movie7 = new Subs("플레이캐스트");
//            Subs movie8 = new Subs("포토랩");
//            Subs movie9 = new Subs("무한티비");
//            Subs movie10 = new Subs("멜로디데이");
//            Subs movie11 = new Subs("뮤직파크");
//            Subs movie12 = new Subs("소울플레이");
//            Subs movie13 = new Subs("하모니스");
//            Subs movie14 = new Subs("힙스테이션");
//            Subs movie15 = new Subs("사운드메이트");
//            Subs movie16 = new Subs("플레이쳐");
//            Subs movie17 = new Subs("소울카페");
//            Subs movie18 = new Subs("헤비볼륨");
//            Subs movie19 = new Subs("올드멜로디");
//            Subs movie20 = new Subs("푸드뱅크");
//            Subs movie21 = new Subs("맛의달인");
//            Subs movie23 = new Subs("크림드벨리");
//            Subs movie24 = new Subs("프레시풋");
//            Subs movie25 = new Subs("고메마켓");
//
//
//            Subs movie26 = new Subs("푸드팜");
//            Subs movie27 = new Subs("푸드샵");
//            Subs movie28 = new Subs("맛나랑");
//            Subs movie29 = new Subs("헬시식품");
//            Subs movie30 = new Subs("알뜰식품");
//            Subs movie31 = new Subs("헬스킹");
//            Subs movie32 = new Subs("웰니스힐");
//            Subs movie33 = new Subs("리커버업");
//            Subs movie34 = new Subs("비타민케어");
//
//
//            Subs movie35 = new Subs("라이프프리");
//            Subs movie36 = new Subs("비건라이프");
//            Subs movie37 = new Subs("스포츠클럽");
//            Subs movie38 = new Subs("피트업");
//            Subs movie39 = new Subs("알로하웰");
//            Subs movie40 = new Subs("포인트웰");
//            Subs movie41 = new Subs("프레쉬브루");
//            Subs movie42 = new Subs("슈퍼스무디");
//            Subs movie43 = new Subs("탄산아트");
//
//            Subs movie44 = new Subs("커피베이");
//            Subs movie45 = new Subs("오가닉티");
//            Subs movie46 = new Subs("프루티칵테일");
//            Subs movie47 = new Subs("힐링티");
//            Subs movie48 = new Subs("밀크오션");
//            Subs movie49 = new Subs("프리미엄스무디");
//            Subs movie50 = new Subs("빙그레이");
//            Subs movie51 = new Subs("우리식물");
//            Subs movie52 = new Subs("그린그레스");
//
//
//
//            subsList.add(movie1);
//            subsList.add(movie2);
//            subsList.add(movie3);
//            subsList.add(movie4);
//            subsList.add(movie5);
//            subsList.add(movie6);
//            subsList.add(movie7);
//            subsList.add(movie8);
//            subsList.add(movie9);
//            subsList.add(movie10);
//            subsList.add(movie11);
//            subsList.add(movie12);
//            subsList.add(movie13);
//            subsList.add(movie14);
//            subsList.add(movie15);
//            subsList.add(movie16);
//            subsList.add(movie17);
//            subsList.add(movie18);
//            subsList.add(movie19);
//            subsList.add(movie20);
//            subsList.add(movie21);
//            subsList.add(movie23);
//            subsList.add(movie24);
//            subsList.add(movie5);
//            subsList.add(movie26);
//            subsList.add(movie27);
//            subsList.add(movie28);
//            subsList.add(movie29);
//            subsList.add(movie30);
//            subsList.add(movie31);
//            subsList.add(movie32);
//            subsList.add(movie33);
//            subsList.add(movie34);
//            subsList.add(movie35);
//            subsList.add(movie36);
//            subsList.add(movie37);
//            subsList.add(movie38);
//            subsList.add(movie39);
//            subsList.add(movie40);
//            subsList.add(movie41);
//            subsList.add(movie42);
//            subsList.add(movie43);
//            subsList.add(movie44);
//            subsList.add(movie45);
//            subsList.add(movie46);
//            subsList.add(movie47);
//            subsList.add(movie48);
//            subsList.add(movie49);
//            subsList.add(movie50);
//            subsList.add(movie51);
//            subsList.add(movie52);
//
//
//            netfilx.addCategory(culture);
//            melon.addCategory(culture);
//            watcha.addCategory(culture);
//            dosirak.addCategory(food);
//            healthcare2.addCategory(health);
//
//            for (Subs subs : subsList) {
//                em.persist(subs);
//            }
//
//            for (int i = 0; i < subsList.size(); i++) {
//                Subs subs = subsList.get(i);
//                SubsRank subsRankDef = new SubsRank("기본",  (i+1)* 400, RankLevel.DEFAULT);
//                SubsRank subsRankPrime = new SubsRank("프라임", (i+2) * 600, RankLevel.PRIME);
//                SubsRank subsRankPremium = new SubsRank("프리미엄", (i+2) * 900, RankLevel.PREMIUM);
//
//                subsRankDef.addSubs(subs);
//                subsRankPrime.addSubs(subs);
//                subsRankPremium.addSubs(subs);
//
//                SubsContent defCon1 = new SubsContent(subs.getSubsName() + " 기본 구독혜택 1");
//                SubsContent defCon2 = new SubsContent(subs.getSubsName() + " 기본 구독혜택 2");
//
//                SubsContent primeCon1 = new SubsContent(subs.getSubsName() + " 프라임 구독혜택 1");
//                SubsContent primeCon2 = new SubsContent(subs.getSubsName() + " 프라임 구독혜택 2");
//                SubsContent primeCon3 = new SubsContent(subs.getSubsName() + " 프라임 구독혜택 3");
//
//                SubsContent primiumCon1 = new SubsContent(subs.getSubsName() + " 프리미엄 구독혜택 1");
//                SubsContent primiumCon2 = new SubsContent(subs.getSubsName() + " 프리미엄 구독혜택 2");
//                SubsContent primiumCon3 = new SubsContent(subs.getSubsName() + " 프리미엄 구독혜택 3");
//
//
//
//                defCon1.addSubsRank(subsRankDef);
//                defCon2.addSubsRank(subsRankDef);
//
//                primeCon1.addSubsRank(subsRankPrime);
//                primeCon2.addSubsRank(subsRankPrime);
//                primeCon3.addSubsRank(subsRankPrime);
//
//                primiumCon1.addSubsRank(subsRankPremium);
//                primiumCon2.addSubsRank(subsRankPremium);
//                primiumCon3.addSubsRank(subsRankPremium);
//
//            }
//
//            em.persist(netfilx);
//            em.persist(melon);
//            em.persist(watcha);
//            em.persist(dosirak);
//            em.persist(healthcare2);
//
//            SubsRank netfilxDef = new SubsRank("기본", 4500, RankLevel.DEFAULT);
//            SubsRank netfilxPrime = new SubsRank("프리미엄", 5500, RankLevel.PRIME);
//
//            netfilxDef.addSubs(netfilx);
//            netfilxPrime.addSubs(netfilx);
//
//            SubsRank melonxDef = new SubsRank("스트리밍", 2500, RankLevel.DEFAULT);
//            SubsRank melonPrime = new SubsRank("다운포함", 6500, RankLevel.PRIME);
//
//            melonxDef.addSubs(melon);
//            melonPrime.addSubs(melon);
//
//            SubsRank watchaDef = new SubsRank("왓챠 기본", 6900, RankLevel.DEFAULT);
//            SubsRank watchaPrime = new SubsRank("왓챠 프리미엄", 7500, RankLevel.PRIME);
//
//            watchaDef.addSubs(watcha);
//            watchaPrime.addSubs(watcha);
//
//            SubsContent netdefC1 = new SubsContent("넷플릭스 기본 구독혜택1");
//            SubsContent netdefC2 = new SubsContent("넷플릭스 기본 구독혜택2");
//
//            SubsContent netdefP1 = new SubsContent("넷플릭스 프리미엄 구독혜택1");
//            SubsContent netdefP2 = new SubsContent("넷플릭스 프리미엄 구독혜택2");
//            SubsContent netdefP3 = new SubsContent("넷플릭스 프리미엄 구독혜택3");
//
//
//            netdefC1.addSubsRank(netfilxDef);
//            netdefC2.addSubsRank(netfilxDef);
//
//            netdefP1.addSubsRank(netfilxPrime);
//            netdefP2.addSubsRank(netfilxPrime);
//            netdefP3.addSubsRank(netfilxPrime);
//
//            SubsContent meldefC1 = new SubsContent("멜론 기본 구독혜택1");
//            SubsContent meldefC2 = new SubsContent("멜론 기본 구독혜택2)");
//
//            SubsContent meldefP1 = new SubsContent("멜론 프리미엄 구독혜택1");
//            SubsContent meldefP2 = new SubsContent("멜론 프리미엄 구독혜택2");
//            SubsContent meldefP3 = new SubsContent("멜론 프리미엄 구독혜택3");
//
//
//            meldefC1.addSubsRank(melonxDef);
//            meldefC2.addSubsRank(melonxDef);
//
//            meldefP1.addSubsRank(melonPrime);
//            meldefP2.addSubsRank(melonPrime);
//            meldefP3.addSubsRank(melonPrime);
//
//            SubsContent watchadefC1 = new SubsContent("watcha 기본 구독혜택1");
//            SubsContent watchadefC2 = new SubsContent("watcha 기본 구독혜택2");
//
//            SubsContent watchadefP1 = new SubsContent("watcha 프리미엄 구독혜택1");
//            SubsContent watchadefP2 = new SubsContent("watcha 프리미엄 구독혜택2");
//            SubsContent watchadefP3 = new SubsContent("watcha 프리미엄 구독혜택3");
//
//
//            watchadefC1.addSubsRank(watchaDef);
//            watchadefC2.addSubsRank(watchaDef);
//
//            watchadefP1.addSubsRank(watchaPrime);
//            watchadefP2.addSubsRank(watchaPrime);
//            watchadefP3.addSubsRank(watchaPrime);
//
//
//            SubsRank healthCareDef = new SubsRank("기본", 7500, RankLevel.DEFAULT);
//            SubsRank healthCarePrime = new SubsRank("프리미엄", 8500, RankLevel.PRIME);
//
//            SubsRank dosirakxDef = new SubsRank("아침", 2500, RankLevel.DEFAULT);
//            SubsRank dosirakPrime = new SubsRank("아침및점심", 6500, RankLevel.PRIME);
//
//            dosirakxDef.addSubs(dosirak);
//            dosirakPrime.addSubs(dosirak);
//
//            SubsRank healthcare2Def = new SubsRank("헬스 기본", 3900, RankLevel.DEFAULT);
//            SubsRank healthcare2Prime = new SubsRank("헬스 프리미엄", 9500, RankLevel.PRIME);
//
//            healthcare2Def.addSubs(healthcare2);
//            healthcare2Prime.addSubs(healthcare2);
//
//            SubsContent healthC1 = new SubsContent("헬스케어 기본 구독혜택1");
//            SubsContent healthC2 = new SubsContent("헬스케어  기본 구독혜택2");
//
//            SubsContent healthP1 = new SubsContent("헬스케어 프리미엄 구독혜택1");
//            SubsContent healthP2 = new SubsContent("헬스케어 프리미엄 구독혜택2");
//            SubsContent healthP3 = new SubsContent("헬스케어 프리미엄 구독혜택3");
//
//
//            healthC1.addSubsRank(healthCareDef);
//            healthC2.addSubsRank(healthCareDef);
//
//            healthP1.addSubsRank(healthCarePrime);
//            healthP2.addSubsRank(healthCarePrime);
//            healthP3.addSubsRank(healthCarePrime);
//
//            SubsContent dosidefC1 = new SubsContent("도시락 기본 구독혜택1");
//            SubsContent dosidefC2 = new SubsContent("도시락 기본 구독혜택2)");
//
//            SubsContent dosidefP1 = new SubsContent("도시락 프리미엄 구독혜택1");
//            SubsContent dosidefP2 = new SubsContent("도시락 프리미엄 구독혜택2");
//            SubsContent dosidefP3 = new SubsContent("도시락 프리미엄 구독혜택3");
//
//
//            dosidefC1.addSubsRank(dosirakxDef);
//            dosidefC2.addSubsRank(dosirakxDef);
//
//            dosidefP1.addSubsRank(dosirakPrime);
//            dosidefP2.addSubsRank(dosirakPrime);
//            dosidefP3.addSubsRank(dosirakPrime);
//
//            SubsContent healthcare2defC1 = new SubsContent("healthcare2 기본 구독혜택1");
//            SubsContent healthcare2defC2 = new SubsContent("healthcare2 기본 구독혜택2");
//
//            SubsContent healthcare2defP1 = new SubsContent("healthcare2 프리미엄 구독혜택1");
//            SubsContent healthcare2defP2 = new SubsContent("healthcare2 프리미엄 구독혜택2");
//            SubsContent healthcare2defP3 = new SubsContent("healthcare2 프리미엄 구독혜택3");
//
//
//            healthcare2defC1.addSubsRank(healthcare2Def);
//            healthcare2defC2.addSubsRank(healthcare2Def);
//
//            healthcare2defP1.addSubsRank(healthcare2Prime);
//            healthcare2defP2.addSubsRank(healthcare2Prime);
//            healthcare2defP3.addSubsRank(healthcare2Prime);
//
//
//
//            Tag age0 = new Tag("10대");
//            Tag age1 = new Tag("20대");
//            Tag age2 = new Tag("30대");
//            Tag age3 = new Tag("40대");
//            Tag age4 = new Tag("50대");
//
//            Tag gender1 = new Tag("여성");
//            Tag gender2 = new Tag("남성");
//
//            Tag foodTag = new Tag("식품");
//            Tag healthTag = new Tag("건강");
//
//            Tag category1 = new Tag("문화");
//            Tag category5 = new Tag("음료");
//            Tag category6 = new Tag("식물");
//            Tag category7 = new Tag("패션잡화");
//            Tag category8 = new Tag("책");
//            Tag category9 = new Tag("교통");
//
//
//            List<Tag> categoryTag = new ArrayList<>();
//            categoryTag.add(category1);
//            categoryTag.add(category5);
//            categoryTag.add(category6);
//            categoryTag.add(category7);
//            categoryTag.add(category8);
//            categoryTag.add(category9);
//            categoryTag.add(healthTag);
//            categoryTag.add(foodTag);
//
//            em.persist(age1);
//            em.persist(age2);
//            em.persist(age3);
//
//            em.persist(gender1);
//            em.persist(gender2);
//
//            em.persist(foodTag);
//            em.persist(healthTag);
//
//            em.persist(category1);
//            em.persist(category5);
//            em.persist(category6);
//            em.persist(category7);
//            em.persist(category8);
//            em.persist(category9);
//
//            List<Tag> tagList = new ArrayList<>();
//            tagList.add(age1);
//            tagList.add(age2);
//            tagList.add(age3);
//            tagList.add(gender1);
//            tagList.add(gender2);
//            tagList.add(foodTag);
//            tagList.add(healthTag);
//            tagList.add(category1);
//            tagList.add(category5);
//            tagList.add(category6);
//            tagList.add(category7);
//            tagList.add(category8);
//            tagList.add(category9);
//
//
//
//
//            for (Subs subs : subsList) {
//
//                String categoryName = subs.getCategory().getCategoryName();
//                Tag tag = categoryTag.stream().filter(t -> t.getTagName().equals(categoryName)).findAny().get();
//                subs.addTag(tag);
//
//            }
//
//
//            dosirak.addTag(age1);
//            dosirak.addTag(age2);
//            dosirak.addTag(foodTag);
//
//            healthcare2.addTag(gender1);
//            healthcare2.addTag(gender2);
//            healthcare2.addTag(healthTag);
//
//            netfilx.addTag(age1);
//            netfilx.addTag(age2);
//            netfilx.addTag(age3);
//            netfilx.addTag(category1);
//
//            netfilx.addTag(gender1);
//            netfilx.addTag(gender2);
//
//            melon.addTag(age1);
//            melon.addTag(age2);
//            melon.addTag(category1);
//
//
//            watcha.addTag(gender1);
//            watcha.addTag(gender2);
//            watcha.addTag(category1);

        }

    }


}
