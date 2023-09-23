package com.alham.ggudok.repository.subs;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.*;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.tempadmin.dto.subs.AdminSubsRankDto;
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
class SubsRepositoryTest {

    @Autowired
    SubsRepository subsRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AdminSubsService adminSubsService;

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

    @Test
    public void findSubsByIdWithTag()throws Exception{
        //given

        Category category = new Category("cat1");
        categoryRepository.save(category);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);
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

        Subs subs1 = subsRepository.findSubsByIdWithTag(subs.getSubsId()).get();
        List<SubsRelTag> subsRelTags = subs1.getSubsRelTags();

        for (SubsRelTag subsRelTag : subsRelTags) {
            System.out.println(subsRelTag.getTag().getTagName());
        }
    }

    @Test
    public void findSubsByIdWithRank()throws Exception{
        //given
        Category category = new Category("cat1");
        categoryRepository.save(category);

        Subs subs = new Subs("subs1");
        subsRepository.save(subs);
        subs.addCategory(category);

        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");

        tagRepository.save(tag1);
        tagRepository.save(tag2);


        adminSubsService.addSubsRank(subs.getSubsId(), new AdminSubsRankDto("rank1", 4500, RankLevel.DEFAULT));
        adminSubsService.addSubsRank(subs.getSubsId(), new AdminSubsRankDto("rank2", 5500, RankLevel.PRIME));

        em.flush();
        em.clear();
        //when
        Subs subs1 = subsRepository.findSubsByIdWithRank(subs.getSubsId()).get();
        List<SubsRank> subsRanks = subs1.getSubsRanks();
        for (SubsRank subsRank : subsRanks) {
            System.out.println("subsRank.getRankName() = " + subsRank.getRankName());
            System.out.println("subsRank.getPrice() = " + subsRank.getPrice());
            System.out.println("subsRank.getRankLevel() = " + subsRank.getRankLevel());


        }


        //then

    }
    
    @Test
    public void findSubsByIdWithAllContent()throws Exception{
        //given
        Category movie = adminSubsService.createCategory("movie");
        Category music = adminSubsService.createCategory("music");

        //when
        Subs netflix = adminSubsService.createSubs("netflix", movie.getCategoryId());
        Subs melon = adminSubsService.createSubs("melon", music.getCategoryId());

        AdminSubsRankDto premium = new AdminSubsRankDto("premium", 5500, RankLevel.PRIME);
        AdminSubsRankDto def = new AdminSubsRankDto("default", 3500, RankLevel.DEFAULT);

        AdminSubsRankDto musicDef = new AdminSubsRankDto("basic", 2500, RankLevel.DEFAULT);
        AdminSubsRankDto musicPre = new AdminSubsRankDto("super", 4500, RankLevel.PRIME);

        adminSubsService.addSubsRank(netflix.getSubsId(), premium);
        adminSubsService.addSubsRank(netflix.getSubsId(), def);

        adminSubsService.addSubsRank(melon.getSubsId(), musicDef);
        adminSubsService.addSubsRank(melon.getSubsId(), musicPre);

        em.flush();
        em.clear();
        //when

        adminSubsService.addSubsContent(netflix.getSubsId(), 1l, "gift1");
        adminSubsService.addSubsContent(netflix.getSubsId(), 1l, "gift2");
        int netfilxPr = adminSubsService.addSubsContent(netflix.getSubsId(), 1l, "gift3");

        adminSubsService.addSubsContent(netflix.getSubsId(), 2l, "gift4");
        int netfilxDef = adminSubsService.addSubsContent(netflix.getSubsId(), 2l, "gift5");


        int memlonBasic = adminSubsService.addSubsContent(melon.getSubsId(), 3l, "gift6");
        adminSubsService.addSubsContent(melon.getSubsId(), 4l, "gift7");
        int melonSu = adminSubsService.addSubsContent(melon.getSubsId(), 4l, "gift8");


        em.flush();
        em.clear();



        List<SubsRank> subsRanks = subsRepository.findSubsByIdWithAllContent(netflix.getSubsId());
        assertEquals(subsRanks.size(),2);
        subsRanks.stream().forEach(subsRank -> subsRank.getContents().stream().forEach(c-> System.out.println(c.getContent())));

    }

    @Test
    public void findSubsByTag()throws Exception{
        //given
        Category category = new Category("cat1");
        categoryRepository.save(category);

        Subs subs1 = new Subs("subs1");
        Subs subs2 = new Subs("subs2");
        Subs subs3 = new Subs("subs3");

        Subs subs4 = new Subs("subs4");
        Subs subs5 = new Subs("subs5");



        subsRepository.save(subs1);
        subsRepository.save(subs2);
        subsRepository.save(subs3);
        subsRepository.save(subs4);
        subsRepository.save(subs5);
        subs1.addCategory(category);
        subs2.addCategory(category);
        subs3.addCategory(category);
        subs4.addCategory(category);
        subs5.addCategory(category);


        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");

        tagRepository.save(tag1);
        tagRepository.save(tag2);

        em.flush();
        em.clear();

        Tag savedTag1 = tagRepository.findById(1l).get();
        Tag savedTag2 = tagRepository.findById(2l).get();

        Subs findSubs1 = subsRepository.findById(1l).get();
        Subs findSubs2 = subsRepository.findById(2l).get();
        Subs findSubs3 = subsRepository.findById(3l).get();
        Subs findSubs4 = subsRepository.findById(4l).get();
        Subs findSubs5 = subsRepository.findById(5l).get();
        findSubs1.addTag(savedTag1);
        findSubs2.addTag(savedTag1);
        findSubs3.addTag(savedTag1);

        findSubs1.addTag(savedTag2);
        findSubs4.addTag(savedTag2);



        em.flush();
        em.clear();

        //when
        List<Subs> subsList1 = subsRepository.findSubsListByTag(savedTag1).get();
        List<Subs> subsList2 = subsRepository.findSubsListByTag(savedTag2).get();
        //then
        assertEquals(subsList1.size(),3);
        assertEquals(subsList2.size(),2);

        for (Subs subs : subsList1) {
            System.out.println("subs.getSubsName() = " + subs.getSubsName());

        }




    }

}