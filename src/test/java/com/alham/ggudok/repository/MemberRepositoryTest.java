package com.alham.ggudok.repository;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.MemberFavorSubs;
import com.alham.ggudok.entity.member.MemberRelTag;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save()throws Exception{
        Member member = new Member("injun", 10);
        System.out.println("==========");
        Member savedMember = memberRepository.save(member);

        em.flush();
        em.clear();

        System.out.println("==========");

        Member findMember = memberRepository.findById(member.getMemberId()).get();

        assertEquals(findMember.getMemberName(),"injun");


    }

    @Test
    public void findByLoginId()throws Exception{
        //given
        memberRepository.save(new Member("injun0607", 23,"injun0607@naver.com","1234", Gender.MAN,"0101234"));
        memberRepository.save(new Member("seohee0826", 23,"seohee0826@naver.com","1234", Gender.WOMAN,"0101234"));
        em.flush();
        em.clear();
        //when
        Member member = memberRepository.findByLoginId("injun0607@naver.com").get();

        //then
        assertEquals(member.getAge(),23);

    }

    @Test
    public void findByLoginIdWithFavorSubs()throws Exception{
        //given
        Member member = memberRepository.save(new Member("injun0607", 23, "injun0607@naver.com", "1234", Gender.MAN, "0101234"));
        Member member2 = memberRepository.save(new Member("seohee0826", 23, "seohee0826@naver.com", "1234", Gender.WOMAN, "0101234"));

        Subs subs1 = new Subs("subs1");
        Subs subs2 = new Subs("subs2");

        em.persist(subs1);
        em.persist(subs2);

        MemberFavorSubs.createFavorSubs(member, subs1);
        MemberFavorSubs.createFavorSubs(member, subs2);

        em.flush();
        em.clear();

        //when
        Member findMember= memberRepository.findByLoginIdWithFavorSubs("injun0607@naver.com").get();


        //then
        List<MemberFavorSubs> memberFavorSubsList = findMember.getMemberFavorSubsList();
        for (MemberFavorSubs memberFavorSubs : memberFavorSubsList) {
            memberFavorSubs.getSubs().getSubsName();
            System.out.println( memberFavorSubs.getSubs().getSubsName());
        }


    }

    @Test
    public void findByLoginIdWithTags()throws Exception{
        //given
        memberRepository.save(new Member("injun0607", 23,"injun0607@naver.com","1234", Gender.MAN,"0101234"));
        memberRepository.save(new Member("seohee0826", 23,"seohee0826@naver.com","1234", Gender.WOMAN,"0101234"));

        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        Tag tag4 = new Tag("tag4");

        em.persist(tag1);
        em.persist(tag2);
        em.persist(tag3);
        em.persist(tag4);

        em.flush();
        em.clear();

        //when
        Member member = memberRepository.findByLoginId("injun0607@naver.com").get();
        MemberRelTag.createRelTag(member, tag1);
        MemberRelTag.createRelTag(member, tag2);
        MemberRelTag.createRelTag(member, tag3);

        em.flush();
        em.clear();
        //then
        Member member1 = memberRepository.findByLoginIdWithTags("injun0607@naver.com").get();
        List<MemberRelTag> memberRelTags = member1.getMemberRelTags();
        Assertions.assertThat(memberRelTags)
                .extracting(memberRelTag -> memberRelTag.getTag().getTagName())
                .containsExactly("tag1", "tag2", "tag3");

    }


}