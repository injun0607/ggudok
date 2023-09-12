package com.alham.ggudok.tempadmin.service.subs;

import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.subs.CategoryRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.tempadmin.dto.subs.AdminSubsRankDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
class AdminSubsServiceTest {

    @Autowired
    AdminSubsService subsAdminService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubsRepository subsRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    public void createAndDeleteCategory()throws Exception{

        Category movie = subsAdminService.createCategory("movie");
        Category music = subsAdminService.createCategory("music");


        assertEquals(categoryRepository.findAll().size(),2);

        subsAdminService.deleteCategory(movie.getCategoryId());

        assertEquals(categoryRepository.findAll().size(),1);

    }

    @Test
    public void createSubsTest()throws Exception{
        //given
        Category movie = subsAdminService.createCategory("movie");
        Category music = subsAdminService.createCategory("music");

        //when
        Subs netflix = subsAdminService.createSubs("netflix", movie.getCategoryId());
        Subs melon = subsAdminService.createSubs("melon", music.getCategoryId());

        em.flush();
        em.clear();

        Subs findNet = subsRepository.findById(netflix.getSubsId()).get();
        Subs findMel = subsRepository.findById(melon.getSubsId()).get();
        //then
        assertEquals(findNet.getCategory().getCategoryName(),"movie");
        assertEquals(findMel.getCategory().getCategoryName(),"music");



    }

    @Test
    public void addSubsRank()throws Exception{
        //given
        Category movie = subsAdminService.createCategory("movie");
        Category music = subsAdminService.createCategory("music");

        //when
        Subs netflix = subsAdminService.createSubs("netflix", movie.getCategoryId());
        Subs melon = subsAdminService.createSubs("melon", music.getCategoryId());

        em.flush();
        em.clear();

        AdminSubsRankDto premium = new AdminSubsRankDto("premium", 5500, RankLevel.PRIME);
        AdminSubsRankDto def = new AdminSubsRankDto("default", 3500, RankLevel.DEFAULT);

        AdminSubsRankDto musicDef = new AdminSubsRankDto("basic", 2500, RankLevel.DEFAULT);
        AdminSubsRankDto musicPre = new AdminSubsRankDto("super", 4500, RankLevel.PRIME);



        //when
        subsAdminService.addSubsRank(netflix.getSubsId(), premium);
        subsAdminService.addSubsRank(netflix.getSubsId(), def);

        subsAdminService.addSubsRank(melon.getSubsId(), musicDef);
        subsAdminService.addSubsRank(melon.getSubsId(), musicPre);


        Subs findNet = subsRepository.findById(netflix.getSubsId()).get();
        Subs findMel = subsRepository.findById(melon.getSubsId()).get();
        //then
        assertEquals(findNet.getSubsRanks().size(),2);
        assertEquals(findMel.getSubsRanks().size(),2);

    }

    @Test
    public void addSubsContent()throws Exception{
        //given
        Category movie = subsAdminService.createCategory("movie");
        Category music = subsAdminService.createCategory("music");

        //when
        Subs netflix = subsAdminService.createSubs("netflix", movie.getCategoryId());
        Subs melon = subsAdminService.createSubs("melon", music.getCategoryId());

        AdminSubsRankDto premium = new AdminSubsRankDto("premium", 5500, RankLevel.PRIME);
        AdminSubsRankDto def = new AdminSubsRankDto("default", 3500, RankLevel.DEFAULT);

        AdminSubsRankDto musicDef = new AdminSubsRankDto("basic", 2500, RankLevel.DEFAULT);
        AdminSubsRankDto musicPre = new AdminSubsRankDto("super", 4500, RankLevel.PRIME);

        subsAdminService.addSubsRank(netflix.getSubsId(), premium);
        subsAdminService.addSubsRank(netflix.getSubsId(), def);

        subsAdminService.addSubsRank(melon.getSubsId(), musicDef);
        subsAdminService.addSubsRank(melon.getSubsId(), musicPre);

        em.flush();
        em.clear();
        //when

        subsAdminService.addSubsContent(netflix.getSubsId(), "premium", "gift1");
        subsAdminService.addSubsContent(netflix.getSubsId(), "premium", "gift2");
        int netfilxPr = subsAdminService.addSubsContent(netflix.getSubsId(), "premium", "gift3");

        subsAdminService.addSubsContent(netflix.getSubsId(), "default", "gift4");
        int netfilxDef = subsAdminService.addSubsContent(netflix.getSubsId(), "default", "gift5");


        int memlonBasic = subsAdminService.addSubsContent(melon.getSubsId(), "basic", "gift6");
        subsAdminService.addSubsContent(melon.getSubsId(), "super", "gift7");
        int melonSu = subsAdminService.addSubsContent(melon.getSubsId(), "super", "gift8");



        //then

        assertEquals(netfilxPr,3);
        assertEquals(netfilxDef,2);
        assertEquals(memlonBasic,1);
        assertEquals(melonSu,2);





    }

}