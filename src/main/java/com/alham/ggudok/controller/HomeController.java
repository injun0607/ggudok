package com.alham.ggudok.controller;


import com.alham.ggudok.config.security.SecurityUtils;
import com.alham.ggudok.controller.session.SessionMemberDto;
import com.alham.ggudok.dto.MainDto;
import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.exception.ErrorResult;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.subs.SubsService;
import com.alham.ggudok.util.GgudokUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
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



    @GetMapping("/")
    public MainDto main(Principal principal) {

        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        //event페이지
        MainDto mainDto = new MainDto();


        if (memberDto != null) {
            //알고리즘 맞춤 서비스
            //해당 유저가 좋아요한 구독정보를 위주로
            //좋아요가 없으면 인기순위
            Map<String, List<Subs>> recommendCustomized = recommendCustomized(memberDto.getLoginId());
            mainDto.transRecommendCustomized(recommendCustomized);
            //기본 추천 서비스(ex. 나이 남성)
            Map<String, List<Subs>> recommendSubsBasic = recommendSubsBasic(memberDto.getLoginId());
            mainDto.transRecommendBasic(recommendSubsBasic);

        } else {
            //로그인 안한 사용자에게 보여주는 기본 subs들
            //인기순위순으로 보여준다.
            Map<String, List<Subs>> defaultSubs = defaultSubs();
            mainDto.transDefaultSubs(defaultSubs);
        }

        return mainDto;

    }

    private Map<String,List<Subs>> defaultSubs() {
        Map<String, List<Subs>> defaultSubs = new HashMap<>();
        return defaultSubs;
    }

    private Map<String, List<Subs>> recommendSubsBasic(String loginId) {
        List<Tag> tags = tagService.findTagsByLoginId(loginId);
        Map<String, List<Subs>> basicSubsListMap = new HashMap<>();
        List<Subs> genderSubsList = new ArrayList<>();
        List<Subs> ageSubsList = new ArrayList<>();
        for (Tag tag : tags) {
            String tagName = tag.getTagName();
            if (tagName.equals("남성") || tagName.equals("여성")) {
                genderSubsList = subsService.findSubsByTag(tag);
                basicSubsListMap.put(tagName, genderSubsList);
            } else if (GgudokUtil.isAgeFormat(tagName)) {
                ageSubsList = subsService.findSubsByTag(tag);
                basicSubsListMap.put(tagName, ageSubsList);
            }
        }
        return basicSubsListMap;
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
     *
     * 세션체크 맵핑
     */
    @GetMapping("/getSession")
    public SessionMemberDto getSession(HttpSession session) {
        return (SessionMemberDto) session.getAttribute(SESSION_MEMBER);

    }

    @GetMapping("/login_fail")
    public ResponseEntity loginFail(HttpServletRequest request) {
        AuthenticationException exception = (AuthenticationException)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        ErrorResult errorResult = new ErrorResult("bad", exception.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

}
