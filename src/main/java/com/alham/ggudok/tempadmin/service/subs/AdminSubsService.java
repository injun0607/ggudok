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
import com.alham.ggudok.tempadmin.dto.subs.EventUpdateForm;
import com.alham.ggudok.tempadmin.dto.subs.SubsContentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void addEvent(EventRegisterForm eventRegisterForm) {
        Subs subs = subsRepository.findById(eventRegisterForm.getSubsId()).get();

        EventSubs eventSubs = new EventSubs(subs);

        LocalDateTime startDate = transferDateTime(eventRegisterForm.getStartDate());
        LocalDateTime endDate = transferDateTime(eventRegisterForm.getEndDate());


        eventSubs.updateEventSubs(eventRegisterForm.getInfoTag(),
                eventRegisterForm.getInfo(),
                startDate,
                endDate,
                eventRegisterForm.getEventImage(),
                eventRegisterForm.isValid());

        eventRepository.save(eventSubs);

    }


    public void updateEvent(EventUpdateForm updateForm) {

        EventSubs eventSubs = eventRepository.findByIdWithSubs(updateForm.getEventId());

        LocalDateTime startDate = transferDateTime(updateForm.getStartDate());
        LocalDateTime endDate = transferDateTime(updateForm.getEndDate());

        eventSubs.updateEventSubs(updateForm.getInfoTag(), updateForm.getInfo(), startDate,endDate,updateForm.getEventImage(), updateForm.isValid());

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


    /**
     *
     * @param stringDateTime(YYYYMMDD 형태)
     * @return
     */
    public LocalDateTime transferDateTime(String stringDateTime) {
        String year = stringDateTime.substring(0, 4);
        String month = stringDateTime.substring(4, 6);
        String day = stringDateTime.substring(6);

        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),0,0);
    }


    public void updateSubsImage(Subs subs, String imgUrl) {
        subs.updateImage(imgUrl);
    }


    public void updateIcon(Long subsId, String imgIcon, String iconName) {
        Subs subs = findSubsById(subsId);
        subs.updateIcon(imgIcon);
        subs.updateIconName(iconName);

    }

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
        Category changeCategory = categoryRepository.findById(categoryId).get();
        subs.updateCategory(changeCategory);

    }

    public void updateSubsName(Subs subs, String subsName) {
        subs.updateSubsName(subsName);
    }

    public void updateSubsInfo(Subs subs, String subsInfo) {
        subs.updateInfo(subsInfo);
    }

    /**
     * subs의 태그정보를 업데이트
     * @param subs
     * @param tagList
     */
    public void updateSubsTag(Subs subs, List<Tag> tagList) {
        subs.updateSubsRelTags(tagList);
    }

    public List<SubsRank> findSubsRankListById(Long subsId) {

        return subsRepository.findSubsByIdWithAllContent(subsId);

    }

    public void updateSubsRank(Subs subs, List<AdminSubsRankDto> subsRankDtoList) {

        Subs withSubsRank = subsRepository.findSubsByIdWithRank(subs.getSubsId()).get();
        List<SubsRank> subsRankList = withSubsRank.getSubsRanks();
//        List<SubsRank> SubsRankWithContent = findSubsRankListById(subs.getSubsId());
        List<RankLevel> alreadyRankLevel = subsRankList.stream().map(sr->sr.getRankLevel()).collect(Collectors.toList());
        //삭제된 subsRank가 있다면 삭제

        alreadyRankLevel.removeAll(subsRankDtoList.stream().map(srd -> srd.getRankLevel()).collect(Collectors.toList()));
        for (int i = subsRankList.size()-1; i > -1; i--) {
            SubsRank subsRank = subsRankList.get(i);

            for (RankLevel rankLevel : alreadyRankLevel) {
                if (subsRank.getRankLevel() == rankLevel) {
                    subsRankList.remove(i);
                }
            }
        }

        //rankLevel로 같은 랭크인지 확인
        for (AdminSubsRankDto adminSubsRankDto : subsRankDtoList) {
            Optional<SubsRank> optionalSubsRank = subsRankList.stream()
                    .filter(sr -> sr.getRankLevel().equals(adminSubsRankDto.getRankLevel())).findFirst();

            //존재한다면 수정
            if (optionalSubsRank.isPresent()) {
                SubsRank subsRank = optionalSubsRank.get();
                //subsRank 기본정보수정
                subsRank.updateSubsRank(adminSubsRankDto.getRankName(),adminSubsRankDto.getPrice());
                //subsRankContent 수정
                subsRank.deleteContents();
                List<SubsContent> contentList = new ArrayList<>();
                List<SubsContentForm> newContentList = adminSubsRankDto.getContentList();
                for (SubsContentForm subsContentForm : newContentList) {
                    SubsContent subsContent = new SubsContent(subsContentForm.getContent());
                    contentList.add(subsContent);
                }
                subsRank.updateSubsContents(contentList);

            } else {
                //존재하지 않으면 새로 생성
                addSubsRank(subs.getSubsId(), adminSubsRankDto);
                adminSubsRankDto.getContentList().stream().forEach(scf->addSubsContent(subs.getSubsId(),adminSubsRankDto.getRankLevel(),scf.getContent()));
            }
        }

        //원래 subsRank삭제
    }

    public void deleteEvent(EventUpdateForm eventUpdateForm) {

        EventSubs eventSubs = eventRepository.findByIdWithSubs(eventUpdateForm.getEventId());
        eventRepository.delete(eventSubs);
    }
}
