package com.alham.ggudok.tempadmin.controller;

import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.service.member.MemberService;
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
public class InitMember {

    private final InitMemberService initMemberService;
    private final MemberService memberService;


    @PostConstruct
    public void init() {
        initMemberService.init();
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("깡깡지");
        memberRegisterDto.setAge(4);
        memberRegisterDto.setLoginId("choiseo26@naver.com");
        memberRegisterDto.setGender(Gender.WOMAN);
        memberRegisterDto.setPassword("10qp10qp^^");
        memberRegisterDto.setPhoneNumber("01012345678");
        memberRegisterDto.setPasswordCheck("10qp10qp^^");
        memberService.registerMember(memberRegisterDto);

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

}
