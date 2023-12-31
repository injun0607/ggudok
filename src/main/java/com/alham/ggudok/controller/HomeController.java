package com.alham.ggudok.controller;


import com.alham.ggudok.config.security.MemberDto;
import com.alham.ggudok.config.security.SecurityUtils;
import com.alham.ggudok.controller.session.SessionMemberDto;
import com.alham.ggudok.dto.ContactUsDto;
import com.alham.ggudok.dto.LoginDto;
import com.alham.ggudok.dto.MainDto;
import com.alham.ggudok.dto.member.MemberLoginDto;
import com.alham.ggudok.dto.subs.EventPageDto;
import com.alham.ggudok.dto.subs.EventSubsDto;
import com.alham.ggudok.dto.subs.SubsDto;
import com.alham.ggudok.dto.subs.SubsMainDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.EventSubs;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.exception.ErrorResult;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.member.ReviewService;
import com.alham.ggudok.service.subs.CategoryService;
import com.alham.ggudok.service.subs.SubsService;
import com.alham.ggudok.tempadmin.dto.subs.category.CategoryDto;
import com.alham.ggudok.tempadmin.dto.subs.category.CategoryListDto;
import com.alham.ggudok.util.GgudokUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.alham.ggudok.controller.session.SessionConst.SESSION_MEMBER;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HomeController {

    private final MemberService memberService;
    private final TagService tagService;
    private final SubsService subsService;

    private final ReviewService reviewService;

    private final CategoryService categoryService;




    @GetMapping("/main")
    public MainDto main(Principal principal) {

        MemberDto memberDto = SecurityUtils.transPrincipal(principal);

        MainDto mainDto = new MainDto();

        List<Category> categoryList =categoryService.findAllCategoryWithSubsCnt();

        List<CategoryDto> categoryDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(category.getCategoryId());
            categoryDto.setCategoryEng(category.getCategoryEng());
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setCategoryImage(category.getCategoryImage());

            categoryDtoList.add(categoryDto);
        }

        mainDto.setCategoryList(categoryDtoList);

        List<Subs> allSubsList = subsService.findAllSubsList();
        List<EventSubs> allEventSubs = subsService.findAllEventSubs();
        List<EventSubsDto> eventSubsDtoList = new ArrayList<>();

        for (EventSubs eventSub : allEventSubs) {

            EventSubsDto eventSubsDto = new EventSubsDto();
            eventSubsDto.setSubsId(eventSub.getSubs().getSubsId());
            eventSubsDto.setInfo(eventSub.getInfo());
            eventSubsDto.setInfoTag(eventSub.getInfoTag());
            eventSubsDto.setCategoryName(eventSub.getSubs().getCategory().getCategoryName());
            eventSubsDto.setSubsName(eventSub.getSubs().getSubsName());
            eventSubsDto.setImage(eventSub.getEventImage());

            String startDate = GgudokUtil.transferDateTime(eventSub.getStartDate());
            String endDate = GgudokUtil.transferDateTime(eventSub.getEndDate());

            eventSubsDto.setStartDate(startDate);
            eventSubsDto.setEndDate(endDate);

            eventSubsDtoList.add(eventSubsDto);
        }

        mainDto.setEventSubs(eventSubsDtoList);


        /*
        로그인 한 사용자 추천 알고리즘 시작
         */
        if (memberDto != null) {

            List<Tag> tagList = tagService.findTagsByLoginId(memberDto.getLoginId());
            //기본 추천 서비스(ex. 나이 남성)
            Member member = memberService.findByLoginIdWithTags(memberDto.getLoginId());

            List<Subs> memberHaveSubsList = member.getMemberHaveSubsList()
                    .stream().map(mhs -> mhs.getSubs())
                    .collect(Collectors.toList());
            List<Subs> memberFavorSubsList = member.getMemberFavorSubsList()
                    .stream()
                    .map(mfs -> mfs.getSubs())
                    .collect(Collectors.toList());

            //구독한것은 추천리스트 삭제
            allSubsList.removeAll(memberHaveSubsList);

            List<Tag> basicTag = member.getMemberRelTags().stream()
                    .filter(mrt -> mrt.isBasic())
                    .map(mrt -> mrt.getTag())
                    .collect(Collectors.toList());

            List<String> defatulTagList = new ArrayList<>();
            for (Tag tag : basicTag) {
                if (tag.getTagName().contains("대")) {
                    mainDto.setAgeTag(tag.getTagName());
                }else{
                    mainDto.setGenderTag(tag.getTagName());
                }
            }



            List<Subs> subsListRecommendBasic = subsService.findSubsListByTagList(allSubsList, basicTag);
            mainDto.transRecommendBasic(subsListRecommendBasic);
            //알고리즘 맞춤 서비스
            //해당 유저가 좋아요한 구독정보를 위주로
            //좋아요가 없으면 인기순위
            //추천태그는 7개만
            List<Tag> recommendTagList = new ArrayList<>();
            for (int i = 0; i < tagList.size(); i++) {
                if (tagList.get(i) != null) {
                    //태그는 최대 7개까지
                    if (i > 7) {
                        break;
                    }
                    recommendTagList.add(tagList.get(i));

                } else {
                    break;
                }
            }

            List<Subs> subsListRecommendCustom = subsService.findSubsListByTag(allSubsList, recommendTagList);
            if (subsListRecommendCustom.size() != 0) {
                mainDto.transRecommendCustomized(subsListRecommendCustom);
            } else {
                mainDto.transDefaultSubs(allSubsList);
            }

        } else {
            mainDto.transRecommendBasic(allSubsList);
            mainDto.transDefaultSubs(allSubsList);

        }

        return mainDto;

    }

    @GetMapping("/category")
    public CategoryListDto categoryList() {

        List<Category> categoryList = subsService.findAllCategory();

        CategoryListDto categoryListDto = new CategoryListDto();

        List<CategoryDto> categoryDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryId(category.getCategoryId());
            categoryDto.setCategoryEng(category.getCategoryEng());
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setCategoryImage(category.getCategoryImage());

            categoryDtoList.add(categoryDto);
        }

        categoryListDto.setCategoryList(categoryDtoList);

        return categoryListDto;
    }


    @GetMapping("/event")
    public EventPageDto showEvent() {

        List<EventSubs> allEventSubs = subsService.findAllEventSubs();
        List<EventSubsDto> eventSubsDtoList = new ArrayList<>();

        for (EventSubs eventSub : allEventSubs) {
            EventSubsDto eventSubsDto = new EventSubsDto();
            eventSubsDto.setSubsId(eventSub.getSubs().getSubsId());
            eventSubsDto.setInfo(eventSub.getInfo());
            eventSubsDto.setInfoTag(eventSub.getInfoTag());
            eventSubsDto.setCategoryName(eventSub.getSubs().getCategory().getCategoryName());
            eventSubsDto.setSubsName(eventSub.getSubs().getSubsName());
            eventSubsDto.setImage(eventSub.getEventImage());

            String startDate = GgudokUtil.transferDateTime(eventSub.getStartDate());
            String endDate = GgudokUtil.transferDateTime(eventSub.getEndDate());

            eventSubsDto.setStartDate(startDate);
            eventSubsDto.setEndDate(endDate);

            eventSubsDtoList.add(eventSubsDto);
        }
        EventPageDto eventPageDto = new EventPageDto();
        eventPageDto.setEventSubs(eventSubsDtoList);

        return eventPageDto;
    }


    @GetMapping("/best_items")
    public SubsMainDto bestItems() {
        List<Subs> allSubsList = subsService.findAllSubsList();
        Map<Long, List<Tag>> allSubsTag = tagService.findAllSubsTag();
        SubsMainDto subsMainDto = new SubsMainDto();
        List<SubsDto> subsDtoList = new ArrayList<>();


        for (int i = 0; i < 20; i++) {
            Subs subs = allSubsList.get(i);

            SubsDto subsDto = new SubsDto();

            subsDto.setId(subs.getSubsId());
            subsDto.setName(subs.getSubsName());
            subsDto.setIcon(subsDto.getIcon());
            subsDto.setImage(subs.getImage());
            subsDto.setRatingAvg(subs.getRatingAvg());
            subsDto.setTags(allSubsTag.get(subs.getSubsId()));

            subsDtoList.add(subsDto);

        }
        subsMainDto.setItems(subsDtoList);
        return subsMainDto;
    }

    @PostMapping("/login")
    public LoginDto memberLogin(@RequestBody MemberLoginDto memberLoginDto, HttpSession session) {
        Member loginMember = memberService.memberLoginCheck(memberLoginDto);
        LoginDto loginDto = new LoginDto();
        if(loginMember != null) {

            MemberDto memberDto = new MemberDto();
            memberDto.setMemberName(loginMember.getMemberName());
            memberDto.setLoginId(loginMember.getLoginId());

            session.setAttribute(SESSION_MEMBER, memberDto);

            loginDto.setSessionId(session.getId());
            loginDto.setMemberDto(memberDto);

            return loginDto;
        } else {
            return loginDto;
        }
    }

    @GetMapping("/logoutSuccess")
    public String memberLogout() {
        return "logout";
    }

    /**
     * 세션체크 맵핑
     */
    @GetMapping("/getSession")
    public ResponseEntity getSession(Principal principal) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        SessionMemberDto sessionMemberDto = new SessionMemberDto();
        if (memberDto != null) {
            sessionMemberDto.setMemberName(memberDto.getMemberName());
            sessionMemberDto.setLoginId(memberDto.getLoginId());
            sessionMemberDto.setRole(memberDto.getRole());
            return new ResponseEntity(sessionMemberDto, HttpStatus.OK);

        }else{
           return new ResponseEntity<>(sessionMemberDto,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login_fail")
    public ResponseEntity loginFail(@RequestParam(value = "error")boolean error, @RequestParam(value = "exception") String errorMessage) {

        ErrorResult errorResult;
        if (errorMessage.equals("id")) {
            errorResult = new ErrorResult("bad", "아이디가 올바르지 않습니다");
        } else if (errorMessage.equals("password")) {
            errorResult = new ErrorResult("bad", "비밀번호가 올바르지 않습니다");
        } else if(errorMessage.equals("oauth2")){
            errorResult = new ErrorResult("bad", "소셜정보가 올바르지 않습니다");
        }else{
            errorResult = new ErrorResult("bad", "회원정보가 올바르지 않습니다");
        }
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);


    }

    @PostMapping("/contact_us")
    public void contactUs(@RequestBody ContactUsDto contactUsDto) {

        String email = "injun_office0607@naver.com";
        String contents= contactUsDto.getContents()+"\n sendEmail : "+contactUsDto.getSubmitEmail() +"\n sendName : " + contactUsDto.getSubmitName();
        GgudokUtil.sendEmail(email, contactUsDto.getTitle(), contents);

    }


}
