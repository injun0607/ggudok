package com.alham.ggudok.repository.subs;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRelTag;
import com.alham.ggudok.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    SubsRepository subsRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    @Rollback(value = false)
    public void findSubsByIdWithTag()throws Exception{
        //given

        Category category = new Category("cat1","ott");
        categoryRepository.save(category);

        Subs subs = new Subs("subs1");
        Subs subs2 = new Subs("subs2");
        subsRepository.save(subs);
        subsRepository.save(subs2);

        subs2.addCategory(category);
        subs.addCategory(category);

        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");

        tagRepository.save(tag1);
        tagRepository.save(tag2);

        em.flush();
        em.clear();

        Tag savedTag1 = tagRepository.findById(1l).get();
        Tag savedTag2 = tagRepository.findById(2l).get();

        Subs findSubs = subsRepository.findById(1l).get();
        findSubs.addTag(savedTag1);
        findSubs.addTag(savedTag2);


        em.flush();
        em.clear();

        Category category1 = categoryRepository.findCateByEngWithSubs("ott").get();
        assertEquals(category1.getSubsList().size(),2);




    }
}