package com.alham.ggudok.tempadmin.service.subs;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.*;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.subs.CategoryRepository;
import com.alham.ggudok.repository.subs.EventRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.tempadmin.dto.TagForm;
import com.alham.ggudok.tempadmin.dto.subs.AdminSubsRankDto;
import com.alham.ggudok.tempadmin.dto.subs.EventRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 기본적인 Transactional 설정
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminSubsService {

    private final CategoryRepository categoryRepository;
    private final SubsRepository subsRepository;

    private final TagRepository tagRepository;

    private final EventRepository eventRepository;

    public Category createCategory(String categoryName) {

        Category saveCategory = categoryRepository.save(new Category(categoryName));

        return saveCategory;

    }

    public void createCategory(String categoryName, String categoryEng,String categoryImage) {
        categoryRepository.save(new Category(categoryName, categoryEng, categoryImage));
    }


    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId).ifPresent(category -> categoryRepository.delete(category));

    }


    public Subs createSubs(String subsName, Long categoryId) {

        Subs subs = new Subs(subsName);
        categoryRepository.findById(categoryId).ifPresent(category -> subs.addCategory(category));
        subsRepository.save(subs);

        return subs;
    }


    public Subs createSubs(Long categoryId,String subsName,String subsImage,List<Tag> tagList,List<AdminSubsRankDto> subsRankDtoList) {

        //subsName으로 subs생성
        Subs subs = new Subs(subsName);
        //subsImage 생성
        subs.updateImage(subsImage);
        //category추가
        categoryRepository.findById(categoryId).ifPresent(category -> subs.addCategory(category));
        Subs savedSubs = subsRepository.save(subs);
        //태그 생성
        for (Tag tag : tagList) {
            addSubsTag(savedSubs.getSubsId(),tag);
        }


        //subsRank 생성
        for (AdminSubsRankDto adminSubsRankDto : subsRankDtoList) {
            addSubsRank(savedSubs.getSubsId(), adminSubsRankDto);
        }


        return subs;
    }
    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }


    public Long addSubsRank(Long subsId, AdminSubsRankDto subsRankDto) {


        SubsRank subsRank = new SubsRank(subsRankDto.getRankName(), subsRankDto.getPrice(), subsRankDto.getRankLevel());
        Subs subs = subsRepository.findById(subsId).get();

        subsRank.addSubs(subs);

        return subsId;
    }

    public int addSubsContent(Long subsId, Long subsRankId, String content) {
        Subs subs = subsRepository.findById(subsId).get();
        SubsContent subsContent = new SubsContent(content);

        SubsRank subsRank = subs.getSubsRanks().stream()
                .filter(s -> s.getRankId().equals(subsRankId))
                .findFirst().get();

        subsContent.addSubsRank(subsRank);

        return subsRank.getContents().size();

    }

    public void addSubsContent(Long subsId, RankLevel rankLevel, String content) {
        Subs subs = subsRepository.findById(subsId).get();
        SubsContent subsContent = new SubsContent(content);

        SubsRank subsRank = subs.getSubsRanks().stream().filter(s -> s.getRankLevel().equals(rankLevel))
                .findFirst().get();

        subsContent.addSubsRank(subsRank);
    }

    public void addEvent(Long subsId) {
        Subs subs = subsRepository.findById(subsId).get();
        List<EventSubs> subsList = eventRepository.findAllWithSubs();

        //이미 Subs가 이벤트에 등록되어있다면
        if (subsList.stream().filter(eventSubs -> eventSubs.getSubs().equals(subs)).findAny().isPresent()) {
            return;
        }

        EventSubs eventSubs = new EventSubs(subs);
        eventRepository.save(eventSubs);

    }


    public void updateEvent(Long eventId, EventRegisterForm registerForm) {

        EventSubs eventSubs = eventRepository.findByIdWithSubs(eventId);

        LocalDateTime startDate = transferDateTime(registerForm.getStartDate());
        LocalDateTime endDate = transferDateTime(registerForm.getEndDate());

        eventSubs.updateEventSubs(registerForm.getInfoTag(), registerForm.getInfo(), startDate,endDate, registerForm.isValid());

    }




    public void deleteSubsContent(Long subsId,Long ContentId) {


    }

    public void updateSubsContent(Long subsId, String subsRankName, String content) {


    }


    public List<Subs> findSubs() {
        return subsRepository.findAll();
    }

    public List<Subs> findAllWithCategory() {
        return subsRepository.findAllWithCategory();
    }


    public Subs findSubsById(Long subsId) {
        return subsRepository.findById(subsId).get();

    }

    public Subs findSubsByIdWithCategory(Long subsId) {
        return subsRepository.findByIdWithCategory(subsId);
    }



    public void addSubsTag(Long subsId, TagForm tagForm) {
        Subs subs = subsRepository.findSubsByIdWithTag(subsId).get();
        Tag tag = tagRepository.findTagByTagName(tagForm.getTagName());
        subs.addTag(tag);
    }

    public void addSubsTag(Long subsId, Tag tag) {

        Subs subs = subsRepository.findSubsByIdWithTag(subsId).get();
        subs.addTag(tag);

    }


    public LocalDateTime transferDateTime(String stringDateTime) {
        String year = stringDateTime.substring(0, 4);
        String month = stringDateTime.substring(4, 6);
        String day = stringDateTime.substring(6);

        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0,0);
    }

    @Transactional
    public void updateImage(Long subsId, String imgUrl) {
        Subs subs = findSubsById(subsId);
        subs.updateImage(imgUrl);
    }

    @Transactional
    public void updateIcon(Long subsId, String imgIcon, String iconName) {
        Subs subs = findSubsById(subsId);
        subs.updateIcon(imgIcon);
        subs.updateIconName(iconName);

    }

    @Transactional
    public boolean updateCategory(Long categoryId, String categoryName, String categoryEng, String categoryImage) {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.updateCategory(categoryName, categoryEng);
            category.updateCategoryImage(categoryImage);

            return true;
        } else {
            return false;
        }

    }

    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).get();

    }

    @Transactional
    public void updateCategoryImage(Long categoryId, String imgUrl) {
        Category category = categoryRepository.findById(categoryId).get();
        category.updateCategoryImage(imgUrl);
    }


    public List<SubsRelTag> findSubsByIdWithTag(Long subsId) {
        return subsRepository.findSubRelTagBySubsId(subsId);
    }

    public boolean deleteSubs(Long subsId) {
        Optional<Subs> subs = subsRepository.findById(subsId);
        if (subs.isPresent()) {
            subsRepository.delete(subs.get());
            return true;
        }else{
            return false;
        }

    }

    public void changeSubsCategory(Subs subs, Long categoryId) {



    }
}
