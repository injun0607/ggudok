package com.alham.ggudok.service;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TagServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagService tagService;



    @Test
    public void checkAgeTag()throws Exception{
        tagRepository.save(new Tag("20대"));
        tagRepository.save(new Tag("30대"));

        em.flush();
        em.clear();

        //given
        Tag tag = tagService.checkAgeTag(24);

        //when
        //then
        assertEquals(tag.getTagName(),"20대");

    }

    @Test
    public void checkGender() throws Exception{
        //given
        tagRepository.save(new Tag("남성"));
        tagRepository.save(new Tag("여성"));

        em.flush();
        em.clear();

        Tag gender = tagService.checkGender(Gender.MAN);
        Tag gender1 = tagService.checkGender(Gender.WOMAN);
        //when
        //then
        assertEquals(gender.getTagName(), "남성");
        assertEquals(gender1.getTagName(), "여성");



    }


}