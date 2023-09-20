package com.alham.ggudok.service.member;

import com.alham.ggudok.controller.subs.member.MemberLoginDto;
import com.alham.ggudok.controller.subs.member.MemberRegisterDto;
import com.alham.ggudok.controller.subs.member.MemberUpdateDto;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.MemberHaveSubs;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SubsRepository subsRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void createMemberHaveSubs()throws Exception{
        //given
        Member member = new Member("injun", 10);
        memberRepository.save(member);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);
        MemberHaveSubs memberHaveSubs = memberService.createMemberHaveSubs(member, subs);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getMemberId()).get();
        List<MemberHaveSubs> memberHaveSubsList = findMember.getMemberHaveSubsList();


        for (MemberHaveSubs haveSubs : memberHaveSubsList) {
            System.out.println("haveSubs = " + haveSubs.getSubs().getSubsName());
        }
        //when

        //then
        assertEquals(memberHaveSubs.getMember().getMemberName(),"injun");

    }

    @Test
    @Rollback(value = false)
    public void registerMember()throws Exception{
        //given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607");

        MemberRegisterDto memberRegisterDto2 = new MemberRegisterDto();
        memberRegisterDto2.setMemberName("injun");
        memberRegisterDto2.setAge(20);
        memberRegisterDto2.setPassword("123123");
        memberRegisterDto2.setGender(Gender.MAN);
        memberRegisterDto2.setLoginId("injun0607");

        //when
        boolean check = memberService.registerMember(memberRegisterDto);
        boolean check2 = memberService.registerMember(memberRegisterDto2);
        //then
        assertEquals(check,true);
        assertNotEquals(check2,true);



    }

    @Test
    public void memberLoginCheck()throws Exception{
        //given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607");

        MemberRegisterDto memberRegisterDto2 = new MemberRegisterDto();
        memberRegisterDto2.setMemberName("seohee");
        memberRegisterDto2.setAge(20);
        memberRegisterDto2.setPassword("321321");
        memberRegisterDto2.setGender(Gender.WOMAN);
        memberRegisterDto2.setLoginId("seohee");

        memberService.registerMember(memberRegisterDto);
        memberService.registerMember(memberRegisterDto2);

        em.flush();
        em.clear();


        //when
        Member memberInjun = memberService.memberLoginCheck(new MemberLoginDto("injun", "123123"));
        Member seohee = memberService.memberLoginCheck(new MemberLoginDto("seohee", "321321"));
        //then
        assertEquals(memberInjun.getMemberName(),"injun") ;
        assertEquals(seohee.getMemberName(),"seohee");

    }

    @Test
    public void updateTest()throws Exception{
        //given
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun0607");

        memberService.registerMember(memberRegisterDto);
        em.flush();
        em.clear();
        //when
        MemberUpdateDto updateDto = new MemberUpdateDto();
        updateDto.setMemberId(1l);
        updateDto.setPassword("123");
        updateDto.setPhoneNumber("0101234");

        memberService.updateMember(updateDto);

        em.flush();
        em.clear();

        Member member = memberRepository.findById(updateDto.getMemberId()).get();
        //then
        assertEquals(member.getPassword(), "123");
        assertEquals(member.getPhoneNumber(), "0101234");

    }


}