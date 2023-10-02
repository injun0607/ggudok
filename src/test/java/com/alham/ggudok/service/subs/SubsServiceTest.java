package com.alham.ggudok.service.subs;

import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.subs.CategoryRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
import com.querydsl.core.Tuple;
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
        org.assertj.core.api.Assertions.assertThat(tagsBySubsId).extracting("tagName").containsExactly("tag1", "tag2", "tag3");
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
        List<Tuple> tuples = subsService.countHaveSubs();
        for (Tuple tuple : tuples) {


        }


        //then

    }

}