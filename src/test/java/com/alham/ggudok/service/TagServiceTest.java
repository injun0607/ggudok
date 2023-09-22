package com.alham.ggudok.service;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.subs.CategoryRepository;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
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
class TagServiceTest {


    @Autowired
    TagService tagService;
    @Autowired
    AdminSubsService adminSubsService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;
    @PersistenceContext
    EntityManager em;



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

    @Test
    public void findTagsBySubsId()throws Exception {
        //given
        Category movie = adminSubsService.createCategory("movie");
        categoryRepository.save(movie);

        //when
        Subs netflix = adminSubsService.createSubs("netflix", movie.getCategoryId());
        netflix.addCategory(movie);
        em.persist(netflix);

        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");

        em.persist(tag1);
        em.persist(tag2);
        em.persist(tag3);

        netflix.addTag(tag1);
        netflix.addTag(tag2);
        netflix.addTag(tag3);

        em.flush();
        em.clear();
        //when
        List<Tag> tagsBySubsId = tagService.findTagsBySubsId(netflix.getSubsId());
        // then

        assertEquals(tagsBySubsId.size(), 3);
        org.assertj.core.api.Assertions.assertThat(tagsBySubsId).extracting("tagName").containsExactly("tag1", "tag2", "tag3");
    }


}