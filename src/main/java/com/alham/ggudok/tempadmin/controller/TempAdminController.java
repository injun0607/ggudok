package com.alham.ggudok.tempadmin.controller;

import com.alham.ggudok.dto.TagDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.*;
import com.alham.ggudok.repository.subs.EventRepository;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.member.ReviewService;
import com.alham.ggudok.service.subs.SubsService;
import com.alham.ggudok.tempadmin.dto.TagForm;
import com.alham.ggudok.tempadmin.dto.subs.*;
import com.alham.ggudok.tempadmin.dto.subs.category.CategoryDto;
import com.alham.ggudok.tempadmin.dto.subs.category.CategoryListDto;
import com.alham.ggudok.tempadmin.dto.subs.category.CategoryRegisterDto;
import com.alham.ggudok.tempadmin.service.AdminTagService;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
import com.alham.ggudok.util.GgudokUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class TempAdminController {

    private final AdminSubsService adminSubsService;

    private final MemberService memberService;
    private final SubsService subsService;

    private final AdminTagService tagService;

    private final EventRepository eventRepository;

    private final AdminTagService adminTagService;

    private final ReviewService reviewService;



    @Value("${upload.subs.main}")
    private String uploadSubsMain;

    @Value("${upload.subs.icon}")
    private String uploadSubsIcon;

    @Value("${upload.category}")
    private String uploadCategory;

    @Value("${upload.event}")
    private String uploadEvent;


    @Value("${download.subs.main}")
    private String downLoadSubsMain;

    @Value("${download.subs.icon}")
    private String downLoadSubsIcon;

    @Value("${download.category}")
    private String downLoadCategory;

    @Value("${download.member}")
    private String downLoadMember;

    @Value("${download.event}")
    private String downloadEvent;


    @GetMapping("/category")
    @ResponseBody
    public CategoryListDto showCategories() {
        CategoryListDto categoryListDto = new CategoryListDto();
        List<Category> categoryList = adminSubsService.findAllCategories();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(category.getCategoryId());
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setCategoryEng(category.getCategoryEng());
            categoryDto.setCategoryImage(category.getCategoryImage());

            categoryDtoList.add(categoryDto);
        }

        categoryListDto.setCategoryList(categoryDtoList);

        return categoryListDto;
    }

    @PostMapping("/category/delete")
    @ResponseBody
    public boolean categoryDelete(@RequestBody CategoryRegisterDto categoryRegisterDto) {
        try {
            adminSubsService.deleteCategory(categoryRegisterDto.getCategoryId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/category/update/{categoryId}")
    @ResponseBody
    public boolean categoryUpdate(@RequestBody CategoryRegisterDto categoryRegisterDto,@PathVariable("categoryId") Long categoryId) {

        if (adminSubsService.updateCategory(categoryId
                , categoryRegisterDto.getCategoryName()
                , categoryRegisterDto.getCategoryEng()
                , categoryRegisterDto.getCategoryImage())) {
            return true;
        } else {
            return false;
        }

    }

    @GetMapping("/category/update/{categoryId}")
    @ResponseBody
    public CategoryDto showCategoryDetail(@PathVariable("categoryId") Long categoryId) {

        Category category = adminSubsService.findCategoryById(categoryId);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setCategoryEng(category.getCategoryEng());
        categoryDto.setCategoryImage(category.getCategoryImage());

        return categoryDto;

    }




    @PostMapping("/category/image")
    public ResponseEntity addCategoryImage(@RequestParam("categoryImage") MultipartFile file) {

        String imgUrl = "";
        Map<String, String> resultMap = new HashMap<>();
        if (file.isEmpty()) {
            resultMap.put("error", "이미지가 존재하지 않습니다");
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        try {

            String contentType = GgudokUtil.checkImageType(file.getContentType());
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadCategory + fileName+contentType);

            file.transferTo(dest);

            imgUrl = downLoadCategory+fileName+contentType;
            resultMap.put("imageUrl", imgUrl);

            return new ResponseEntity<>(resultMap, HttpStatus.OK);

        } catch (IOException e) {
            resultMap.put("error", "이미지가 존재하지 않습니다");

            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/category/register")
    @ResponseBody
    public boolean addCategory(@RequestBody CategoryRegisterDto categoryRegisterDto) {

        adminSubsService.createCategory(categoryRegisterDto.getCategoryName(),categoryRegisterDto.getCategoryEng(),categoryRegisterDto.getCategoryImage());

        return true;
    }

    @GetMapping("subs")
    @ResponseBody
    public AdminSubsListDto showSubs() {
        List<Subs> subsList = adminSubsService.findAllWithCategory();

        List<AdminSubsDto> adminSubsDtoList = new ArrayList<>();
        for (Subs subs : subsList) {
            AdminSubsDto adminSubsDto = new AdminSubsDto();
            adminSubsDto.setSubsName(subs.getSubsName());
            adminSubsDto.setCategoryName(subs.getCategory().getCategoryName());
            adminSubsDto.setSubsId(subs.getSubsId());
            adminSubsDto.setSubsImage(subs.getImage());

            List<SubsRelTag> srtList = adminSubsService.findSubsByIdWithTag(subs.getSubsId());
            List<TagDto> tagList = new ArrayList<>();
            for (SubsRelTag subsRelTag : srtList) {
                TagDto tagDto = new TagDto();
                tagDto.setTagId(subsRelTag.getTag().getTagId());
                tagDto.setTagName(subsRelTag.getTag().getTagName());

                tagList.add(tagDto);
            }

            adminSubsDto.setTagList(tagList);

            adminSubsDtoList.add(adminSubsDto);

        }

        AdminSubsListDto adminSubsListDto = new AdminSubsListDto();
        adminSubsListDto.setSubsList(adminSubsDtoList);

        return adminSubsListDto;
    }

    @GetMapping("subs/register")
    @ResponseBody
    public SubsRegisterViewForm showSubsForm() {

        SubsRegisterViewForm subsRegisterViewForm = new SubsRegisterViewForm();

        CategoryListDto categoryListDto = new CategoryListDto();
        List<Category> categoryList = adminSubsService.findAllCategories();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(category.getCategoryId());
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setCategoryEng(category.getCategoryEng());
            categoryDto.setCategoryImage(category.getCategoryImage());

            categoryDtoList.add(categoryDto);
        }

        List<Tag> tagList = tagService.findAllTag();
        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagDto tagDto = new TagDto();

            tagDto.setTagId(tag.getTagId());
            tagDto.setTagName(tag.getTagName());
            tagDtoList.add(tagDto);
        }

        categoryListDto.setCategoryList(categoryDtoList);

        subsRegisterViewForm.setCategoryList(categoryDtoList);
        subsRegisterViewForm.setTagList(tagDtoList);



        return subsRegisterViewForm;
    }




    @PostMapping("subs/register")
    @ResponseBody
    public boolean addSubs(@RequestBody SubsRegisterDto subsRegisterDto) {
        List<TagDto> tagDtoList = subsRegisterDto.getTagList();

        List<AdminSubsRankDto> subsRankDtoList = subsRegisterDto.getSubsRankList();

        List<Tag> tagList = new ArrayList<>();
        for (TagDto tagDto : tagDtoList) {
            Tag tag = adminTagService.findTagByTagId(tagDto.getTagId());

            tagList.add(tag);
        }

        Subs subs = adminSubsService.createSubs(subsRegisterDto.getSubsName(), subsRegisterDto.getCategoryId());
        adminSubsService.updateSubsInfo(subs,subsRegisterDto.getSubsInfo());
        adminSubsService.updateSubsImage(subs,subsRegisterDto.getSubsImage());

        for (Tag tag : tagList) {
            adminSubsService.addSubsTag(subs.getSubsId(),tag);
        }


        for (AdminSubsRankDto adminSubsRankDto : subsRankDtoList) {
            adminSubsService.addSubsRank(subs.getSubsId(), adminSubsRankDto);

            List<SubsContentForm> contentList = adminSubsRankDto.getContentList();
            for (SubsContentForm subsContentForm : contentList) {
                adminSubsService.addSubsContent(subs.getSubsId(),adminSubsRankDto.getRankLevel(),subsContentForm.getContent());
            }

        }

        return true;
    }


    @PostMapping("subs/delete")
    @ResponseBody
    public boolean deleteSubs(@RequestBody AdminSubsDto adminSubsDto) {
        if (adminSubsService.deleteSubs(adminSubsDto.getSubsId())) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("subs/{subsId}")
    @ResponseBody
    public AdminSubsDetailDto manageSubsRank(@PathVariable("subsId") Long subsId, Model model) {
        Subs subs = subsService.findSubsByIdWithCategory(subsId);
        List<SubsRank> subsRankList = subsService.findContentBySubsId(subsId);
        List<Tag> tagList = subsService.findTagsBySubsId(subsId);

        //subsTag관련
        List<TagDto> tagDtoList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagDto tagDto = new TagDto();

            tagDto.setTagId(tag.getTagId());
            tagDto.setTagName(tag.getTagName());
            tagDtoList.add(tagDto);
        }

        //subsRank관련
        List<AdminSubsRankDto> rankDtoList = new ArrayList<>();
        for (SubsRank subsRank : subsRankList) {
            AdminSubsRankDto adminSubsRankDto = new AdminSubsRankDto();
            adminSubsRankDto.setRankId(subsRank.getRankId());
            adminSubsRankDto.setRankLevel(subsRank.getRankLevel());
            adminSubsRankDto.setRankName(subsRank.getRankName());
            adminSubsRankDto.setPrice(subsRank.getPrice());

            List<SubsContent> contents = subsRank.getContents();
            List<SubsContentForm> contentFormList = new ArrayList<>();
            for (SubsContent content : contents) {
                SubsContentForm subsContentForm = new SubsContentForm();
                subsContentForm.setContent(content.getContent());
                subsContentForm.setContentId(content.getContentId());

                contentFormList.add(subsContentForm);
            }
            adminSubsRankDto.setContentList(contentFormList);

            rankDtoList.add(adminSubsRankDto);

        }

        AdminSubsDetailDto adminSubsDetailDto = new AdminSubsDetailDto();

        adminSubsDetailDto.setCategoryName(subs.getCategory().getCategoryName());
        adminSubsDetailDto.setCategoryId(subs.getCategory().getCategoryId());
        adminSubsDetailDto.setSubsId(subs.getSubsId());
        adminSubsDetailDto.setSubsName(subs.getSubsName());
        adminSubsDetailDto.setSubsImage(subs.getImage());
        adminSubsDetailDto.setTagList(tagDtoList);
        adminSubsDetailDto.setSubsRankList(rankDtoList);
        adminSubsDetailDto.setSubsInfo(subs.getInfo());


        return adminSubsDetailDto;
    }

    @PostMapping("/subs/update/{subsId}")
    @ResponseBody
    public boolean updateSubsDetail(@RequestBody SubsUpdateDto subsUpdateDto,@PathVariable("subsId")Long subsId) {

        Subs subs = adminSubsService.findSubsById(subsId);

        //카테고리 체크
        if (subs.getCategory().getCategoryId() != subsUpdateDto.getCategoryId()) {
            adminSubsService.changeSubsCategory(subs, subsUpdateDto.getCategoryId());
        }

        //subs기본정보 업데이트
        adminSubsService.updateSubsName(subs,subsUpdateDto.getSubsName());
        adminSubsService.updateSubsInfo(subs,subsUpdateDto.getSubsInfo());
        adminSubsService.updateSubsImage(subs,subsUpdateDto.getSubsImage());

        //태그 정보 업데이트
        List<Tag> tagList = new ArrayList<>();
        List<TagDto> tagDtoList = subsUpdateDto.getTagList();
        for (TagDto tagDto : tagDtoList) {
            Tag tag = adminTagService.findTagByTagId(tagDto.getTagId());

            tagList.add(tag);
        }
        adminSubsService.updateSubsTag(subs, tagList);

        //subsRank정보 업데이트
        List<AdminSubsRankDto> subsRankDtoList = subsUpdateDto.getSubsRankList();

        adminSubsService.updateSubsRank(subs, subsRankDtoList);

        return false;
    }


    @PostMapping("subs/register/image")
    public ResponseEntity<Map> uploadSubsMainImage(@RequestParam(value = "subsImage",required = false) MultipartFile file) {

        String imgUrl = "";
        Map<String, String> resultMap = new HashMap<>();

        try {
            String contentType = GgudokUtil.checkImageType(file.getContentType());
            if (contentType.equals(GgudokUtil.NOT_IMAGE)) {
                resultMap.put("error", "올바른 이미지 파일이 아닙니다");
                return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
            }
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename()+contentType;


            File dest = new File(uploadSubsMain + fileName);

            file.transferTo(dest);

            imgUrl = downLoadSubsMain + fileName;

            resultMap.put("imageUrl", imgUrl);

            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (IOException e) {
            resultMap.put("error", "이미지 처리중 에러발생");
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("tag")
    @ResponseBody
    public List<TagDto> showTags() {
        List<Tag> tags = tagService.findAllTag();

        List<TagDto> tagList = new ArrayList<>();
        for (Tag tag : tags) {
            TagDto tagDto = new TagDto();
            tagDto.setTagName(tag.getTagName());
            tagDto.setTagId(tag.getTagId());

            tagList.add(tagDto);
        }

        return tagList;
    }

    @PostMapping("tag/add")
    @ResponseBody
    public boolean addTag(@RequestBody TagForm tagForm) {

        tagService.saveTag(tagForm.getTagName());

        return true;
    }

    @PostMapping("tag/delete")
    @ResponseBody
    public boolean deleteTag(@RequestBody TagDto tagDto) {

        tagService.deleteTag(tagDto.getTagId());
        return true;
    }


    /**
     * 이벤트 리스트
     * @return
     */
    @GetMapping("event")
    @ResponseBody
    public List<EventShowDto> showEventList() {
        List<EventShowDto> eventList = new ArrayList<>();

        List<EventSubs> allEvent = eventRepository.findAllWithSubs();
        for (EventSubs eventSubs : allEvent) {
            EventShowDto eventShowDto = new EventShowDto();
            eventShowDto.setEventId(eventSubs.getEventId());
            eventShowDto.setSubsId(eventSubs.getSubs().getSubsId());
            eventShowDto.setSubsName(eventSubs.getSubs().getSubsName());
            eventShowDto.setInfo(eventSubs.getInfo());
            eventShowDto.setEventImage(eventSubs.getEventImage());
            eventShowDto.setInfoTag(eventSubs.getInfoTag());

            String startTime = "" + eventSubs.getStartDate().getYear() + eventSubs.getStartDate().getMonthValue()
                    + transferStringDay(eventSubs.getStartDate().getDayOfMonth());

            String endTime = "" + eventSubs.getEndDate().getYear() + eventSubs.getEndDate().getMonthValue()
                    + transferStringDay(eventSubs.getEndDate().getDayOfMonth());

            eventShowDto.setStartDate(startTime);
            eventShowDto.setEndDate(endTime);

            eventList.add(eventShowDto);
        }

        return eventList;

    }

    /**
     * 이벤트 등록 페이지
     * @return
     */
    @GetMapping("event/register")
    @ResponseBody
    public EventRegisterShowForm showRegisterEventForm() {
        EventRegisterShowForm eventRegisterForm = new EventRegisterShowForm();

        List<Category> allCategories = adminSubsService.findAllCategories();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : allCategories) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(category.getCategoryId());
            categoryDto.setCategoryName(category.getCategoryName());

            categoryDtoList.add(categoryDto);
        }

        eventRegisterForm.setCategoryList(categoryDtoList);

        List<AdminSubsDto> adminSubsDtoList = new ArrayList<>();
        List<Subs> subsList = adminSubsService.findAllWithCategory();

        for (Subs subs : subsList) {
            AdminSubsDto adminSubsDto = new AdminSubsDto();

            adminSubsDto.setSubsId(subs.getSubsId());
            adminSubsDto.setSubsName(subs.getSubsName());
            adminSubsDto.setCategoryName(subs.getCategory().getCategoryName());

            adminSubsDtoList.add(adminSubsDto);
        }

        eventRegisterForm.setSubsList(adminSubsDtoList);


        return eventRegisterForm;

    }

    /**
     * 이벤트 이미지 등록
     * @param file
     * @return
     */
    @PostMapping("event/register/image")
    public ResponseEntity<Map> uploadEventImage(@RequestParam(value = "eventImage",required = false) MultipartFile file) {

        String imgUrl = "";
        Map<String, String> resultMap = new HashMap<>();

        try {
            String contentType = GgudokUtil.checkImageType(file.getContentType());
            if (contentType.equals(GgudokUtil.NOT_IMAGE)) {
                resultMap.put("error", "올바른 이미지 파일이 아닙니다");
                return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
            }
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename()+contentType;


            File dest = new File(uploadEvent + fileName);

            file.transferTo(dest);

            imgUrl = downloadEvent + fileName;

            resultMap.put("imageUrl", imgUrl);

            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (IOException e) {
            resultMap.put("error", "이미지 처리중 에러발생");
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * 이벤트 등록 요청
     * @param eventRegisterForm
     * @return
     */
    @PostMapping("event/register")
    @ResponseBody
    public boolean registerEventForm(@RequestBody EventRegisterForm eventRegisterForm) {

        adminSubsService.addEvent(eventRegisterForm);
        return true;

    }

    /**
     * 이벤트 상세 정보
     * @param eventId
     * @return
     */
    @GetMapping("event/{eventId}")
    @ResponseBody
    public EventDetailFormDto showEventDetail(@PathVariable("eventId") Long eventId) {
        EventSubs eventSubs = eventRepository.findByIdWithSubs(eventId);

        EventDetailFormDto eventForm = new EventDetailFormDto();

        eventForm.setSubsId(eventSubs.getSubs().getSubsId());
        eventForm.setSubsName(eventSubs.getSubs().getSubsName());
        eventForm.setCategoryName(eventSubs.getSubs().getCategory().getCategoryName());
        eventForm.setInfo(eventSubs.getInfo());
        eventForm.setInfoTag(eventSubs.getInfoTag());


        String startTime = "" + eventSubs.getStartDate().getYear() + eventSubs.getStartDate().getMonthValue()
                + transferStringDay(eventSubs.getStartDate().getDayOfMonth());

        String endTime = "" + eventSubs.getEndDate().getYear() + eventSubs.getEndDate().getMonthValue()
                + transferStringDay(eventSubs.getEndDate().getDayOfMonth());

        eventForm.setStartDate(startTime);
        eventForm.setEndDate(endTime);
        eventForm.setValid(eventSubs.getIsValid());
        eventForm.setEventImage(eventSubs.getEventImage());

        return eventForm;
    }

    @PostMapping("update/recommend")
    @ResponseBody
    public boolean updateRecommend() {
        subsService.updateRecommendSort();
        memberService.userRecommendTag();
        return true;

    }

    /**
     * 이벤트 수정
     * @param eventUpdateForm
     * @return
     */
    @PostMapping("event/update")
    @ResponseBody
    public boolean updateEvent(@RequestBody EventUpdateForm eventUpdateForm) {

        adminSubsService.updateEvent(eventUpdateForm);

        return true;
    }

    @PostMapping("event/delete")
    @ResponseBody
    public boolean deleteEvent(@RequestBody EventUpdateForm eventUpdateForm) {
        adminSubsService.deleteEvent(eventUpdateForm);

        return true;
    }

    @PostMapping("/init_review")
    @ResponseBody
    public boolean initReview() {

        HashMap<String, ArrayList<String>> reviewContent = GgudokUtil.reviewInit();
        List<Member> allMember = memberService.findAllMember();
        for (int i = 1; i <= 20; i++) {
            String loginId = "test" + i;
            allMember.stream().filter(m->m.getLoginId().equals(loginId+"@naver.com"))
                    .findFirst()
                    .ifPresent(m->memberService.updateMemberProfile(m,downLoadMember+loginId+".png"));
        }

        Random random = new Random();


        int beforeNum = 0;
        List<Subs> allSubsList = subsService.findAllSubsList();
        for (Subs subs : allSubsList) {
            ArrayList<String> reviews = reviewContent.get(subs.getSubsName());
            if (reviews == null) {
                continue;
            }
            for (String review : reviews) {
                int i = random.nextInt(20) + 1;

                while (true) {
                    i = random.nextInt(20) + 1;
                    if (i != beforeNum) {
                        break;
                    }
                }

                beforeNum = i;
                String memberLoginId = "test"+i+"@naver.com";
                Member findMember = memberService.findByLoginIdWithTags(memberLoginId);
                if (memberService.buySubs(findMember.getLoginId(), subs, RankLevel.DEFAULT)) {
                    memberService.updateMemberTagRecommend(memberLoginId, subs);
                }
                int rating = random.nextInt(4) + 2;
                reviewService.writeReview(findMember, subs, review, rating);
                Integer ratingAvg = reviewService.updateRatingAvg(subs.getSubsId());
                subsService.updateRatingAvg(subs,ratingAvg);

            }

        }
    return true;




    }


    /*
    이벤트 날짜 표시시 day 7 -> 07로 바꿔주는 역할
     */
    public String transferStringDay(int day) {

        String result = String.valueOf(day);
        if (result.length() == 1) {
            result = "0" + result;
        }

        return result;
    }
}
