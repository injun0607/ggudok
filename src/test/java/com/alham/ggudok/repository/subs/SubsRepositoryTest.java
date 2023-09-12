package com.alham.ggudok.repository.subs;

import com.alham.ggudok.entity.subs.Subs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SubsRepositoryTest {

    @Autowired
    SubsRepository subsRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void save()throws Exception{
        //given
        Subs subs = new Subs("subs1");
        //when
        Subs saveSubs = subsRepository.save(subs);


        em.flush();
        em.clear(); //영속성 컨텐츠가 다날아감


        //then
        Subs findSubs = subsRepository.findById(subs.getSubsId()).get();


        assertEquals(findSubs.getSubsName(), "subs1");


    }

}