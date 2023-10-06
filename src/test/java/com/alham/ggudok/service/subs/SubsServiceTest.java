package com.alham.ggudok.service.subs;

import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.dto.subs.SubsRecommendDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.subs.CategoryRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.member.ReviewService;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@Transactional

class SubsServiceTest {

    @Autowired
    SubsService subsService;

    @Autowired
    AdminSubsService adminSubsService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubsRepository subsRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    TagRepository tagRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void findTagsBySubsId()throws Exception {
        //given
        Category movie = adminSubsService.createCategory("movie");
        categoryRepository.save(movie);

        //when
        Subs netflix = adminSubsService.createSubs("netflix", movie.getCategoryId());
        netflix.addCategory(movie);
        subsRepository.save(netflix);

        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);

        netflix.addTag(tag1);
        netflix.addTag(tag2);
        netflix.addTag(tag3);

        em.flush();
        em.clear();
        //when
        List<Tag> tagsBySubsId = subsService.findTagsBySubsId(netflix.getSubsId());
        // then

        assertEquals(tagsBySubsId.size(), 3);
        assertThat(tagsBySubsId).extracting("tagName").containsExactly("tag1", "tag2", "tag3");
    }

    @Test
    public void countHaveSubs()throws Exception{
        //given
        Category movie = adminSubsService.createCategory("movie");
        categoryRepository.save(movie);

        //when
        Subs netflix = adminSubsService.createSubs("netflix", movie.getCategoryId());
        Subs watcha = adminSubsService.createSubs("watcha", movie.getCategoryId());
        Subs disney = adminSubsService.createSubs("disney", movie.getCategoryId());
        Subs tving = adminSubsService.createSubs("tving", movie.getCategoryId());
        Subs laftel = adminSubsService.createSubs("laftel", movie.getCategoryId());
        netflix.addCategory(movie);
        watcha.addCategory(movie);
        disney.addCategory(movie);
        tving.addCategory(movie);
        laftel.addCategory(movie);

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
        memberRegisterDto2.setLoginId("injun0601@naver.com");

        memberService.registerMember(memberRegisterDto2);

        MemberRegisterDto memberRegisterDto3 = new MemberRegisterDto();
        memberRegisterDto3.setMemberName("injun");
        memberRegisterDto3.setAge(20);
        memberRegisterDto3.setPassword("123123");
        memberRegisterDto3.setPasswordCheck("123123");
        memberRegisterDto3.setGender(Gender.MAN);
        memberRegisterDto3.setLoginId("injun0602@naver.com");

        memberService.registerMember(memberRegisterDto3);

        MemberRegisterDto memberRegisterDto4 = new MemberRegisterDto();
        memberRegisterDto4.setMemberName("injun");
        memberRegisterDto4.setAge(20);
        memberRegisterDto4.setPassword("123123");
        memberRegisterDto4.setPasswordCheck("123123");
        memberRegisterDto4.setGender(Gender.MAN);
        memberRegisterDto4.setLoginId("injun0603@naver.com");

        memberService.registerMember(memberRegisterDto4);

        MemberRegisterDto memberRegisterDto5 = new MemberRegisterDto();
        memberRegisterDto5.setMemberName("injun");
        memberRegisterDto5.setAge(20);
        memberRegisterDto5.setPassword("123123");
        memberRegisterDto5.setPasswordCheck("123123");
        memberRegisterDto5.setGender(Gender.MAN);
        memberRegisterDto5.setLoginId("injun0604@naver.com");

        memberService.registerMember(memberRegisterDto5);

        memberService.buySubs("injun0607@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", disney, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", tving, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", laftel, RankLevel.DEFAULT);


        memberService.buySubs("injun0601@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", disney, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", tving, RankLevel.DEFAULT);

        memberService.buySubs("injun0602@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", disney, RankLevel.DEFAULT);

        memberService.buySubs("injun0603@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0603@naver.com", watcha, RankLevel.DEFAULT);

        memberService.buySubs("injun0604@naver.com", netflix, RankLevel.DEFAULT);

        //when
        Member memberWithSubs = memberService.findByLoginIdWithHaveSubs("injun0602@naver.com");

        //then
        assertEquals(memberWithSubs.getMemberHaveSubsList().size(),3);

    }

    @Test
    public void recommendHaveSubs()throws Exception{
        //given
        Category movie = adminSubsService.createCategory("movie");
        categoryRepository.save(movie);

        //when
        Subs netflix = adminSubsService.createSubs("netflix", movie.getCategoryId());
        Subs watcha = adminSubsService.createSubs("watcha", movie.getCategoryId());
        Subs disney = adminSubsService.createSubs("disney", movie.getCategoryId());
        Subs tving = adminSubsService.createSubs("tving", movie.getCategoryId());
        Subs laftel = adminSubsService.createSubs("laftel", movie.getCategoryId());
        netflix.addCategory(movie);
        watcha.addCategory(movie);
        disney.addCategory(movie);
        tving.addCategory(movie);
        laftel.addCategory(movie);

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
        memberRegisterDto2.setLoginId("injun0601@naver.com");

        memberService.registerMember(memberRegisterDto2);

        MemberRegisterDto memberRegisterDto3 = new MemberRegisterDto();
        memberRegisterDto3.setMemberName("injun");
        memberRegisterDto3.setAge(20);
        memberRegisterDto3.setPassword("123123");
        memberRegisterDto3.setPasswordCheck("123123");
        memberRegisterDto3.setGender(Gender.MAN);
        memberRegisterDto3.setLoginId("injun0602@naver.com");

        memberService.registerMember(memberRegisterDto3);

        MemberRegisterDto memberRegisterDto4 = new MemberRegisterDto();
        memberRegisterDto4.setMemberName("injun");
        memberRegisterDto4.setAge(20);
        memberRegisterDto4.setPassword("123123");
        memberRegisterDto4.setPasswordCheck("123123");
        memberRegisterDto4.setGender(Gender.MAN);
        memberRegisterDto4.setLoginId("injun0603@naver.com");

        memberService.registerMember(memberRegisterDto4);

        MemberRegisterDto memberRegisterDto5 = new MemberRegisterDto();
        memberRegisterDto5.setMemberName("injun");
        memberRegisterDto5.setAge(20);
        memberRegisterDto5.setPassword("123123");
        memberRegisterDto5.setPasswordCheck("123123");
        memberRegisterDto5.setGender(Gender.MAN);
        memberRegisterDto5.setLoginId("injun0604@naver.com");

        memberService.registerMember(memberRegisterDto5);

        memberService.buySubs("injun0607@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", disney, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", tving, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", laftel, RankLevel.DEFAULT);


        memberService.buySubs("injun0601@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", disney, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", tving, RankLevel.DEFAULT);

        memberService.buySubs("injun0602@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", disney, RankLevel.DEFAULT);

        memberService.buySubs("injun0603@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0603@naver.com", watcha, RankLevel.DEFAULT);

        memberService.buySubs("injun0604@naver.com", netflix, RankLevel.DEFAULT);

        //when
        List<SubsRecommendDto> subsRecommendDtoList = subsService.countHaveSubs();
        for (SubsRecommendDto subsRecommendDto : subsRecommendDtoList) {
            System.out.println(subsRecommendDto);
        }

        //then


    }

    @Test
    public void recommendSubs()throws Exception{
        //given
        Category movie = adminSubsService.createCategory("movie");
        categoryRepository.save(movie);

        //when
        Subs netflix = adminSubsService.createSubs("netflix", movie.getCategoryId());
        Subs watcha = adminSubsService.createSubs("watcha", movie.getCategoryId());
        Subs disney = adminSubsService.createSubs("disney", movie.getCategoryId());
        Subs tving = adminSubsService.createSubs("tving", movie.getCategoryId());
        Subs laftel = adminSubsService.createSubs("laftel", movie.getCategoryId());

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

        em.persist(age0);
        em.persist(age1);
        em.persist(age2);
        em.persist(age3);
        em.persist(age4);

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

        netflix.addTag(age1);
        netflix.addTag(age2);
        netflix.addTag(age3);

        netflix.addTag(gender1);
        netflix.addTag(gender2);

        watcha.addTag(age1);
        watcha.addTag(age2);

        watcha.addTag(gender1);
        watcha.addTag(gender2);


        disney.addTag(age0);
        disney.addTag(age3);
        disney.addTag(age4);

        disney.addTag(gender1);
        disney.addTag(gender2);

        tving.addTag(age1);
        tving.addTag(age2);

        tving.addTag(gender1);
        tving.addTag(gender2);

        laftel.addTag(age0);
        laftel.addTag(age3);
        laftel.addTag(age4);

        laftel.addTag(gender1);
        laftel.addTag(gender2);


        netflix.addCategory(movie);
        watcha.addCategory(movie);
        disney.addCategory(movie);
        tving.addCategory(movie);
        laftel.addCategory(movie);

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
        memberRegisterDto2.setLoginId("injun0601@naver.com");

        memberService.registerMember(memberRegisterDto2);

        MemberRegisterDto memberRegisterDto3 = new MemberRegisterDto();
        memberRegisterDto3.setMemberName("injun");
        memberRegisterDto3.setAge(20);
        memberRegisterDto3.setPassword("123123");
        memberRegisterDto3.setPasswordCheck("123123");
        memberRegisterDto3.setGender(Gender.MAN);
        memberRegisterDto3.setLoginId("injun0602@naver.com");

        memberService.registerMember(memberRegisterDto3);

        MemberRegisterDto memberRegisterDto4 = new MemberRegisterDto();
        memberRegisterDto4.setMemberName("injun");
        memberRegisterDto4.setAge(20);
        memberRegisterDto4.setPassword("123123");
        memberRegisterDto4.setPasswordCheck("123123");
        memberRegisterDto4.setGender(Gender.MAN);
        memberRegisterDto4.setLoginId("injun0603@naver.com");

        memberService.registerMember(memberRegisterDto4);

        MemberRegisterDto memberRegisterDto5 = new MemberRegisterDto();
        memberRegisterDto5.setMemberName("injun");
        memberRegisterDto5.setAge(20);
        memberRegisterDto5.setPassword("123123");
        memberRegisterDto5.setPasswordCheck("123123");
        memberRegisterDto5.setGender(Gender.MAN);
        memberRegisterDto5.setLoginId("injun0604@naver.com");

        memberService.registerMember(memberRegisterDto5);

        memberService.buySubs("injun0607@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", disney, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", tving, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", laftel, RankLevel.DEFAULT);


        memberService.buySubs("injun0601@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", disney, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", tving, RankLevel.DEFAULT);

        memberService.buySubs("injun0602@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", disney, RankLevel.DEFAULT);

        memberService.buySubs("injun0603@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0603@naver.com", watcha, RankLevel.DEFAULT);

        memberService.buySubs("injun0604@naver.com", netflix, RankLevel.DEFAULT);

        Member injun1 = memberService.findByLoginIdWithFavorSubs("injun0607@naver.com");
        Member injun2 = memberService.findByLoginIdWithFavorSubs("injun0601@naver.com");
        Member injun3 = memberService.findByLoginIdWithFavorSubs("injun0602@naver.com");
        Member injun4 = memberService.findByLoginIdWithFavorSubs("injun0603@naver.com");
        Member injun5 = memberService.findByLoginIdWithFavorSubs("injun0604@naver.com");

        memberService.likeSubs(injun1, netflix);
        memberService.likeSubs(injun2, netflix);



        memberService.likeSubs(injun1, disney);
        memberService.likeSubs(injun2, disney);
        memberService.likeSubs(injun3, disney);
        memberService.likeSubs(injun4, disney);
        memberService.likeSubs(injun5, disney);


        memberService.likeSubs(injun1, watcha);
        memberService.likeSubs(injun2, watcha);
        memberService.likeSubs(injun3, watcha);
        memberService.likeSubs(injun4, watcha);
        memberService.likeSubs(injun5, watcha);

        memberService.likeSubs(injun1, tving);
        memberService.likeSubs(injun2, tving);

        memberService.likeSubs(injun1, laftel);
        memberService.likeSubs(injun2, laftel);
        memberService.likeSubs(injun3, laftel);

        reviewService.writeReview(injun1, netflix, "넷플1", 5);
        reviewService.writeReview(injun2, netflix, "넷플1", 4);
        reviewService.writeReview(injun3, netflix, "넷플1", 3);
        reviewService.writeReview(injun4, netflix, "넷플1", 2);
        reviewService.writeReview(injun5, netflix, "넷플1", 1);

        reviewService.writeReview(injun1, watcha, "왓챠1", 5);
        reviewService.writeReview(injun2, watcha, "왓챠1", 5);
        reviewService.writeReview(injun3, watcha, "왓챠1", 5);
        reviewService.writeReview(injun4, watcha, "왓챠1", 4);
        reviewService.writeReview(injun5, watcha, "왓챠1", 3);

        reviewService.writeReview(injun1, disney, "디즈니1", 5);
        reviewService.writeReview(injun2, disney, "디즈니1", 5);
        reviewService.writeReview(injun3, disney, "디즈니1", 5);
        reviewService.writeReview(injun4, disney, "디즈니1", 5);

        reviewService.writeReview(injun1, tving, "tving1", 4);
        reviewService.writeReview(injun2, tving, "tving1", 4);
        reviewService.writeReview(injun3, tving, "tving1", 5);
        reviewService.writeReview(injun4, tving, "tving1", 5);

        reviewService.writeReview(injun1, laftel, "laftel1", 4);
        reviewService.writeReview(injun2, laftel, "laftel1", 5);
        reviewService.writeReview(injun3, laftel, "laftel1", 5);
        reviewService.writeReview(injun4, laftel, "laftel1", 5);
        reviewService.writeReview(injun5, laftel, "laftel1", 5);

        em.flush();
        em.clear();
        //when
        subsService.updateRecommendSort();
        em.flush();
        em.clear();

        List<Subs> recommendSubsList = subsService.findRecommendSubsList();

        assertEquals(recommendSubsList.get(0).getRecommendSort(),1);
        //then

    }


    @Test
    public void findSubsListByTagList()throws Exception{
        //given
        //given
        Category movie = adminSubsService.createCategory("movie");
        categoryRepository.save(movie);

        //when
        Subs netflix = adminSubsService.createSubs("netflix", movie.getCategoryId());
        Subs watcha = adminSubsService.createSubs("watcha", movie.getCategoryId());
        Subs disney = adminSubsService.createSubs("disney", movie.getCategoryId());
        Subs tving = adminSubsService.createSubs("tving", movie.getCategoryId());
        Subs laftel = adminSubsService.createSubs("laftel", movie.getCategoryId());

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

        em.persist(age0);
        em.persist(age1);
        em.persist(age2);
        em.persist(age3);
        em.persist(age4);

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

        netflix.addTag(age1);
        netflix.addTag(age2);
        netflix.addTag(age3);
        netflix.addTag(category1);

        netflix.addTag(gender1);
        netflix.addTag(gender2);

        watcha.addTag(age1);
        watcha.addTag(age2);
        watcha.addTag(category2);

        watcha.addTag(gender1);
        watcha.addTag(gender2);


        disney.addTag(age0);
        disney.addTag(age3);
        disney.addTag(age4);
        disney.addTag(category3);

        disney.addTag(gender1);
        disney.addTag(gender2);

        tving.addTag(age1);
        tving.addTag(age2);
        tving.addTag(category4);

        tving.addTag(gender1);
        tving.addTag(gender2);

        laftel.addTag(age0);
        laftel.addTag(age3);
        laftel.addTag(age4);
        laftel.addTag(category5);

        laftel.addTag(gender1);
        laftel.addTag(gender2);


        netflix.addCategory(movie);
        watcha.addCategory(movie);
        disney.addCategory(movie);
        tving.addCategory(movie);
        laftel.addCategory(movie);

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
        memberRegisterDto2.setLoginId("injun0601@naver.com");

        memberService.registerMember(memberRegisterDto2);

        MemberRegisterDto memberRegisterDto3 = new MemberRegisterDto();
        memberRegisterDto3.setMemberName("injun");
        memberRegisterDto3.setAge(20);
        memberRegisterDto3.setPassword("123123");
        memberRegisterDto3.setPasswordCheck("123123");
        memberRegisterDto3.setGender(Gender.MAN);
        memberRegisterDto3.setLoginId("injun0602@naver.com");

        memberService.registerMember(memberRegisterDto3);

        MemberRegisterDto memberRegisterDto4 = new MemberRegisterDto();
        memberRegisterDto4.setMemberName("injun");
        memberRegisterDto4.setAge(20);
        memberRegisterDto4.setPassword("123123");
        memberRegisterDto4.setPasswordCheck("123123");
        memberRegisterDto4.setGender(Gender.MAN);
        memberRegisterDto4.setLoginId("injun0603@naver.com");

        memberService.registerMember(memberRegisterDto4);

        MemberRegisterDto memberRegisterDto5 = new MemberRegisterDto();
        memberRegisterDto5.setMemberName("injun");
        memberRegisterDto5.setAge(20);
        memberRegisterDto5.setPassword("123123");
        memberRegisterDto5.setPasswordCheck("123123");
        memberRegisterDto5.setGender(Gender.MAN);
        memberRegisterDto5.setLoginId("injun0604@naver.com");

        memberService.registerMember(memberRegisterDto5);

        memberService.buySubs("injun0607@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", disney, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", tving, RankLevel.DEFAULT);
        memberService.buySubs("injun0607@naver.com", laftel, RankLevel.DEFAULT);


        memberService.buySubs("injun0601@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", disney, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", tving, RankLevel.DEFAULT);

        memberService.buySubs("injun0602@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", disney, RankLevel.DEFAULT);

        memberService.buySubs("injun0603@naver.com", netflix, RankLevel.DEFAULT);
        memberService.buySubs("injun0603@naver.com", watcha, RankLevel.DEFAULT);

        memberService.buySubs("injun0604@naver.com", netflix, RankLevel.DEFAULT);

        Member injun1 = memberService.findByLoginIdWithFavorSubs("injun0607@naver.com");
        Member injun2 = memberService.findByLoginIdWithFavorSubs("injun0601@naver.com");
        Member injun3 = memberService.findByLoginIdWithFavorSubs("injun0602@naver.com");
        Member injun4 = memberService.findByLoginIdWithFavorSubs("injun0603@naver.com");
        Member injun5 = memberService.findByLoginIdWithFavorSubs("injun0604@naver.com");

        memberService.likeSubs(injun1, netflix);
        memberService.likeSubs(injun2, netflix);



        memberService.likeSubs(injun1, disney);
        memberService.likeSubs(injun2, disney);
        memberService.likeSubs(injun3, disney);
        memberService.likeSubs(injun4, disney);
        memberService.likeSubs(injun5, disney);


        memberService.likeSubs(injun1, watcha);
        memberService.likeSubs(injun2, watcha);
        memberService.likeSubs(injun3, watcha);
        memberService.likeSubs(injun4, watcha);
        memberService.likeSubs(injun5, watcha);

        memberService.likeSubs(injun1, tving);
        memberService.likeSubs(injun2, tving);

        memberService.likeSubs(injun1, laftel);
        memberService.likeSubs(injun2, laftel);
        memberService.likeSubs(injun3, laftel);

        reviewService.writeReview(injun1, netflix, "넷플1", 5);
        reviewService.writeReview(injun2, netflix, "넷플1", 4);
        reviewService.writeReview(injun3, netflix, "넷플1", 3);
        reviewService.writeReview(injun4, netflix, "넷플1", 2);
        reviewService.writeReview(injun5, netflix, "넷플1", 1);

        reviewService.writeReview(injun1, watcha, "왓챠1", 5);
        reviewService.writeReview(injun2, watcha, "왓챠1", 5);
        reviewService.writeReview(injun3, watcha, "왓챠1", 5);
        reviewService.writeReview(injun4, watcha, "왓챠1", 4);
        reviewService.writeReview(injun5, watcha, "왓챠1", 3);

        reviewService.writeReview(injun1, disney, "디즈니1", 5);
        reviewService.writeReview(injun2, disney, "디즈니1", 5);
        reviewService.writeReview(injun3, disney, "디즈니1", 5);
        reviewService.writeReview(injun4, disney, "디즈니1", 5);

        reviewService.writeReview(injun1, tving, "tving1", 4);
        reviewService.writeReview(injun2, tving, "tving1", 4);
        reviewService.writeReview(injun3, tving, "tving1", 5);
        reviewService.writeReview(injun4, tving, "tving1", 5);

        reviewService.writeReview(injun1, laftel, "laftel1", 4);
        reviewService.writeReview(injun2, laftel, "laftel1", 5);
        reviewService.writeReview(injun3, laftel, "laftel1", 5);
        reviewService.writeReview(injun4, laftel, "laftel1", 5);
        reviewService.writeReview(injun5, laftel, "laftel1", 5);

        em.flush();
        em.clear();
        //when
        subsService.updateRecommendSort();

        //20대여성
        List<Tag> tagList1 = new ArrayList<>();
        tagList1.add(age1);
        tagList1.add(gender1);

        //20대남성
        List<Tag> tagList2 = new ArrayList<>();
        tagList2.add(age1);
        tagList2.add(gender2);




        //when
        List<Subs> allSubsList = subsService.findAllSubsList();
        List<Subs> subsListByTagList = subsService.findSubsListByTag(allSubsList,age1);
        List<Subs> subsListByTagList1 = subsService.findSubsListByTagList(allSubsList,tagList1);
        //then
        assertThat(subsListByTagList1).extracting(subs -> subs.getSubsName()).containsExactly( "watcha","netflix", "tving");
        assertEquals(subsListByTagList.size(),3);
    }



}