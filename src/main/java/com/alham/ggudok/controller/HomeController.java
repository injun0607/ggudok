package com.alham.ggudok.controller;


import com.alham.ggudok.config.security.SecurityUtils;
import com.alham.ggudok.controller.session.SessionMemberDto;
import com.alham.ggudok.dto.MainDto;
import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.exception.ErrorResult;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.member.ReviewService;
import com.alham.ggudok.service.subs.SubsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if (memberDto != null) {
            //알고리즘 맞춤 서비스
            //해당 유저가 좋아요한 구독정보를 위주로
            //좋아요가 없으면 인기순위
            subsService.findRecommendSubsListByTag();
            //기본 추천 서비스(ex. 나이 남성)
            Map<String, List<Subs>> recommendSubsBasic = recommendSubsBasic(memberDto.getLoginId());
            mainDto.transRecommendBasic(recommendSubsBasic);

        } else {
            List<Subs> defRecomSubsList = subsService.findRecommendSubsList();
        }

        return mainDto;

    }

    private Map<String, List<Subs>> defaultSubs() {
        Map<String, List<Subs>> defaultSubs = new HashMap<>();
        return defaultSubs;
    }

    /*
     * 추천 서비스
     *  - 1순위 : 구매횟수가 많은것(횟수당 4점)
        - 2순위: 좋아요 횟수가 많은것(좋아요당 3점)
        - 3순위: 별점이 높은것(별점당 0.4점)
        - 4순위: 리뷰가 많은것(리뷰당 1점)
        *
     */

    //basic서비스는 나이와 성별로 추천
    private Map<String, List<Subs>> recommendSubsBasic(String loginId) {
        subsService.countHaveSubs();
        subsService.countFavorSubs();
        subsService.sumRating();
        subsService.countReview();




        return null;
    }

    private Map<String, List<Subs>> recommendCustomized(String loginId) {
        Map<String, List<Subs>> customizedSubsListMap = new HashMap<>();
        return customizedSubsListMap;
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
