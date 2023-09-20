package com.alham.ggudok.repository;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.repository.subs.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TagRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TagRepository tagRepository;

    @Test
    public void findByCategoryNameLikeAge()throws Exception{
        //given

        Tag tag1 = new Tag("20대");
        Tag tag2 = new Tag("30대");
        Tag tag3 = new Tag("40대");
        Tag tag4 = new Tag("50대");

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        tagRepository.save(tag4);

        em.flush();
        em.clear();
        //when
        Tag tag = tagRepository.findByTagNameLikeAge("30");

        //then
        assertEquals(tag.getTagName(),"30대");


    }
}