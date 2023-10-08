package com.alham.ggudok.repository.subs;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.*;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.tempadmin.dto.subs.AdminSubsRankDto;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @BeforeEach
    @Rollback
    public void beforeEach() {

    }

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

        Tag savedTag1 = tagRepository.findById(tag1.getTagId()).get();
        Tag savedTag2 = tagRepository.findById(tag2.getTagId()).get();

        Subs findSubs = subsRepository.findById(subs.getSubsId()).get();
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

        //when
        Subs netfilxSubsRank = subsRepository.findSubsByIdWithRank(netflix.getSubsId()).get();
        SubsRank subsRank1 = netfilxSubsRank.getSubsRanks().stream().filter(subsRank -> subsRank.getRankName().equals("premium")).findFirst().get();
        SubsRank subsRank2 = netfilxSubsRank.getSubsRanks().stream().filter(subsRank -> subsRank.getRankName().equals("default")).findFirst().get();
        Subs melonSubsRank = subsRepository.findSubsByIdWithRank(melon.getSubsId()).get();
        SubsRank subsRank3 = melonSubsRank.getSubsRanks().stream().filter(subsRank -> subsRank.getRankName().equals("basic")).findFirst().get();
        SubsRank subsRank4 = melonSubsRank.getSubsRanks().stream().filter(subsRank -> subsRank.getRankName().equals("super")).findFirst().get();


        adminSubsService.addSubsContent(netflix.getSubsId(), subsRank1.getRankId(), "gift1");
        adminSubsService.addSubsContent(netflix.getSubsId(), subsRank1.getRankId(), "gift2");
        int netfilxPr = adminSubsService.addSubsContent(netflix.getSubsId(), subsRank1.getRankId(), "gift3");

        adminSubsService.addSubsContent(netflix.getSubsId(), subsRank2.getRankId(), "gift4");
        int netfilxDef = adminSubsService.addSubsContent(netflix.getSubsId(), subsRank2.getRankId(), "gift5");


        int memlonBasic = adminSubsService.addSubsContent(melon.getSubsId(), subsRank3.getRankId(), "gift6");
        adminSubsService.addSubsContent(melon.getSubsId(), subsRank4.getRankId(), "gift7");
        int melonSu = adminSubsService.addSubsContent(melon.getSubsId(), subsRank4.getRankId(), "gift8");


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


        Subs saveSubs1 = subsRepository.save(subs1);
        Subs saveSubs2 = subsRepository.save(subs2);
        Subs saveSubs3 = subsRepository.save(subs3);
        Subs saveSubs4 = subsRepository.save(subs4);
        Subs saveSubs5 = subsRepository.save(subs5);
        subs1.addCategory(category);
        subs2.addCategory(category);
        subs3.addCategory(category);
        subs4.addCategory(category);
        subs5.addCategory(category);


        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");

        tagRepository.save(tag1);
        tagRepository.save(tag2);

        Tag tag11 = tagRepository.findTagByTagName("tag1");
        Tag tag21 = tagRepository.findTagByTagName("tag2");

        Tag savedTag1 = tagRepository.findById(tag11.getTagId()).get();
        Tag savedTag2 = tagRepository.findById(tag21.getTagId()).get();

        Subs findSubs1 = subsRepository.findById(saveSubs1.getSubsId()).get();
        Subs findSubs2 = subsRepository.findById(saveSubs2.getSubsId()).get();
        Subs findSubs3 = subsRepository.findById(saveSubs3.getSubsId()).get();
        Subs findSubs4 = subsRepository.findById(saveSubs4.getSubsId()).get();
        Subs findSubs5 = subsRepository.findById(saveSubs5.getSubsId()).get();
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

    @Test
    public void findSubsBysubsIdListWithTag()throws Exception{
        //given
        Category category = new Category("cat1");
        categoryRepository.save(category);

        Subs subs1 = new Subs("subs1");
        Subs subs2 = new Subs("subs2");
        Subs subs3 = new Subs("subs3");

        Subs subs4 = new Subs("subs4");
        Subs subs5 = new Subs("subs5");


        Subs saveSubs1 = subsRepository.save(subs1);
        Subs saveSubs2 = subsRepository.save(subs2);
        Subs saveSubs3 = subsRepository.save(subs3);
        Subs saveSubs4 = subsRepository.save(subs4);
        Subs saveSubs5 = subsRepository.save(subs5);
        subs1.addCategory(category);
        subs2.addCategory(category);
        subs3.addCategory(category);
        subs4.addCategory(category);
        subs5.addCategory(category);


        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        Tag tag4 = new Tag("tag4");
        Tag tag5 = new Tag("tag5");
        Tag tag6 = new Tag("tag6");

        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        tagRepository.save(tag4);
        tagRepository.save(tag5);
        tagRepository.save(tag6);


        Tag findTag1 = tagRepository.findTagByTagName("tag1");
        Tag findTag2 = tagRepository.findTagByTagName("tag2");
        Tag findTag3 = tagRepository.findTagByTagName("tag2");
        Tag findTag4 = tagRepository.findTagByTagName("tag2");
        Tag findTag5 = tagRepository.findTagByTagName("tag2");
        Tag findTag6 = tagRepository.findTagByTagName("tag2");

        Tag savedTag1 = tagRepository.findById(findTag1.getTagId()).get();
        Tag savedTag2 = tagRepository.findById(findTag2.getTagId()).get();
        Tag savedTag3 = tagRepository.findById(findTag3.getTagId()).get();
        Tag savedTag4 = tagRepository.findById(findTag4.getTagId()).get();
        Tag savedTag5 = tagRepository.findById(findTag5.getTagId()).get();
        Tag savedTag6 = tagRepository.findById(findTag6.getTagId()).get();

        Subs findSubs1 = subsRepository.findById(saveSubs1.getSubsId()).get();
        Subs findSubs2 = subsRepository.findById(saveSubs2.getSubsId()).get();
        Subs findSubs3 = subsRepository.findById(saveSubs3.getSubsId()).get();
        Subs findSubs4 = subsRepository.findById(saveSubs4.getSubsId()).get();
        Subs findSubs5 = subsRepository.findById(saveSubs5.getSubsId()).get();
        findSubs1.addTag(savedTag1);
        findSubs2.addTag(savedTag1);
        findSubs3.addTag(savedTag1);

        findSubs1.addTag(savedTag2);
        findSubs2.addTag(savedTag2);
        findSubs3.addTag(savedTag2);
        findSubs4.addTag(savedTag2);

        findSubs1.addTag(savedTag3);
        findSubs2.addTag(savedTag3);
        findSubs3.addTag(savedTag3);
        findSubs4.addTag(savedTag3);

        findSubs3.addTag(savedTag4);
        findSubs4.addTag(savedTag4);

        findSubs3.addTag(savedTag5);
        findSubs4.addTag(savedTag5);
        findSubs5.addTag(savedTag5);

        List<Subs> subsList = new ArrayList<>();
        subsList.add(subs1);
        subsList.add(subs2);
        subsList.add(subs3);
        subsList.add(subs4);
        subsList.add(subs5);

        em.flush();
        em.clear();

        //when
        List<Long> subsIdList = subsList.stream().map(subs -> subs.getSubsId()).toList();
        List<Subs> subsBySubsIdListWithTag = subsRepository.findSubsBySubsIdListWithTag(subsIdList);

        //then

        assertEquals(subsBySubsIdListWithTag.stream().filter(subs -> subs.getSubsName().equals("subs1")).findFirst().get().getSubsRelTags().size(),3);
        assertEquals(subsBySubsIdListWithTag.stream().filter(subs -> subs.getSubsName().equals("subs2")).findFirst().get().getSubsRelTags().size(),3);
        assertEquals(subsBySubsIdListWithTag.stream().filter(subs -> subs.getSubsName().equals("subs3")).findFirst().get().getSubsRelTags().size(),5);
        assertEquals(subsBySubsIdListWithTag.stream().filter(subs -> subs.getSubsName().equals("subs4")).findFirst().get().getSubsRelTags().size(),4);
        assertEquals(subsBySubsIdListWithTag.stream().filter(subs -> subs.getSubsName().equals("subs5")).findFirst().get().getSubsRelTags().size(),1);


        for (Subs subs : subsBySubsIdListWithTag) {
            System.out.println("subs.getSubsName() = " + subs.getSubsName());

        }

    }


}