package com.alham.ggudok.repository;

import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

}