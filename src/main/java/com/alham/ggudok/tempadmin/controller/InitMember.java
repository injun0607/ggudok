package com.alham.ggudok.tempadmin.controller;

import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
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


    @PostConstruct
    public void init() {
        initMemberService.init();

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
