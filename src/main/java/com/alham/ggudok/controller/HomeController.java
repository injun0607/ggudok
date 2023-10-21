package com.alham.ggudok.controller;


import com.alham.ggudok.config.security.SecurityUtils;
import com.alham.ggudok.controller.session.SessionMemberDto;
import com.alham.ggudok.dto.MainDto;
import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.dto.subs.EventPageDto;
import com.alham.ggudok.dto.subs.EventSubsDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.EventSubs;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.exception.ErrorResult;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.member.ReviewService;
import com.alham.ggudok.service.subs.SubsService;
import com.alham.ggudok.util.GgudokUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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



    @GetMapping("/")
    public MainDto main(Principal principal) {

        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        //event페이지
        MainDto mainDto = new MainDto();
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

            allSubsList.removeAll(memberHaveSubsList);
            allSubsList.removeAll(memberFavorSubsList);

            List<Tag> basicTag = member.getMemberRelTags().stream()
                    .filter(mrt -> mrt.isBasic())
                    .map(mrt -> mrt.getTag())
                    .collect(Collectors.toList());

            List<Subs> subsListRecommendBasic = subsService.findSubsListByTagList(allSubsList,basicTag);
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

                }else{
                    break;
                }
            }

            List<Subs> subsListRecommendCustom = subsService.findSubsListByTag(allSubsList, recommendTagList);
            if (subsListRecommendCustom.size() != 0) {
                mainDto.transRecommendCustomized(subsListRecommendCustom);
            }else{
                mainDto.transDefaultSubs(allSubsList);
            }

        } else {
            mainDto.transRecommendBasic(allSubsList);
            mainDto.transDefaultSubs(allSubsList);

        }

        return mainDto;

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

//    @PostMapping("/login")
//    public LoginDto memberLogin(MemberLoginDto memberLoginDto, HttpSession session) {
//        Member loginMember = memberService.memberLoginCheck(memberLoginDto);
//        LoginDto loginDto = new LoginDto();
//        if(loginMember != null) {
//
//            MemberDto memberDto = new MemberDto();
//            memberDto.setMemberName(loginMember.getMemberName());
//            memberDto.setLoginId(loginMember.getLoginId());
//
//            session.setAttribute(SESSION_MEMBER, memberDto);
//
//            loginDto.setSessionId(session.getId());
//            loginDto.setMemberDto(memberDto);
//
//            return loginDto;
//        } else {
//            return loginDto;
//        }
//    }

    @GetMapping("/logoutSuccess")
    public String memberLogout() {
        return "logout";
    }

    @GetMapping("/seohee")
    public String seohee() {

        return "서히야 안녕";
    }


    /**
     * 세션체크 맵핑
     */
    @GetMapping("/getSession")
    public SessionMemberDto getSession(HttpSession session) {
        return (SessionMemberDto) session.getAttribute(SESSION_MEMBER);

    }

    @GetMapping("/login_fail")
    public ResponseEntity loginFail(@RequestParam(value = "error")boolean error, @RequestParam(value = "exception") String errorMessage) {

        ErrorResult errorResult;
        if (errorMessage.equals("id")) {
            errorResult = new ErrorResult("bad", "아이디가 올바르지 않습니다");
        } else if (errorMessage.equals("password")) {
            errorResult = new ErrorResult("bad", "비밀번호가 올바르지 않습니다");
        } else{
            errorResult = new ErrorResult("bad", "회원정보가 올바르지 않습니다");
        }
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);


    }


    @GetMapping("/init_review")
    public void initReview() {
        Member seo = memberService.findByLoginId("choiseo26@naver.com");
        Member yh = memberService.findByLoginId("yhgu0607@naver.com");
        Member in = memberService.findByLoginId("injun0607@naver.com");

        Subs healthCare = subsService.findBySubsName("healthCare");
        Subs dosirak = subsService.findBySubsName("dosirak");
        Subs healthCare2 = subsService.findBySubsName("healthCare2");

        reviewService.writeReview(seo, healthCare, "건강이 매우좋아졌어요", 4);
        reviewService.writeReview(seo, dosirak, "서히가 배부러요", 3);
        reviewService.writeReview(seo, healthCare2, "서히부인 운동해요", 5);

        memberService.likeSubs(seo,healthCare);
        memberService.likeSubs(seo,healthCare2);

        reviewService.writeReview(in, healthCare, "인준아저씨 건강해요 ", 5);
        reviewService.writeReview(in, dosirak, "사히부인 밥이 더맛있어요", 2);
        reviewService.writeReview(in, healthCare2, "인준아저씨 운동해요", 4);

        reviewService.writeReview(yh, healthCare, "깡깡이 리뷰!", 3);
        reviewService.writeReview(yh, dosirak, "낑낑이 리뷰!", 5);
        reviewService.writeReview(yh, healthCare2, "껑껑이 리뷰!", 4);

    }

}
