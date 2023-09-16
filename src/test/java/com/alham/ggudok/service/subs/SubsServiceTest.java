package com.alham.ggudok.service.subs;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.subs.CategoryRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

}