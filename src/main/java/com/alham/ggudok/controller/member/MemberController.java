package com.alham.ggudok.controller.member;

import com.alham.ggudok.config.security.SecurityUtils;
import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.dto.member.MemberUpdateDto;
import com.alham.ggudok.dto.member.ReviewDto;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.Review;
import com.alham.ggudok.exception.ErrorResult;
import com.alham.ggudok.exception.member.MemberException;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.member.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "*")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    private final ReviewService reviewService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResult> memberExceptionHandler(MemberException e) {
        log.error(e.getMessage());
        ErrorResult errorResult = new ErrorResult("BAD", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    private boolean registerMember(@RequestBody MemberRegisterDto registerDto) {

        boolean isSuccess = memberService.registerMember(registerDto);
        if (isSuccess) {
            return true;
        } else {
            return false;
        }
    }

    /*
    이메일 인증하기
    이메일 인증 버튼누른후 -> 인증번호 입력
     */
    @PostMapping("/emailCheck")
    private Map<String, String> checkEmail(@RequestBody MemberRegisterDto registerDto) {
        String checkEmail = registerDto.getLoginId();
        String certCode = memberService.checkEmail(checkEmail);
        Map<String, String> map = new HashMap<>();
        map.put("certCode", certCode);
        return map;


    }

    @GetMapping("/memberInfo")
    private String viewMemberInfo(Principal principal) {
        MemberDto memberDto = (MemberDto) principal;
        String loginId = memberDto.getLoginId();
        memberService.viewMemberInfo(loginId);
        return null;

    }

    @GetMapping("/update")
    private MemberUpdateDto updateViewMember(Principal principal) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto == null) {
            new MemberException("로그인 되지 않은 회원입니다");
        }

        Member member = memberService.findByLoginId(memberDto.getLoginId());
        MemberUpdateDto updateDto = new MemberUpdateDto();
        updateDto.setAge(member.getAge());
        updateDto.setPhoneNumber(member.getPhoneNumber());
        updateDto.setGender(member.getGender());

        return updateDto;
    }


    @PostMapping("/update")
    private boolean updateMember(@RequestBody MemberUpdateDto updateDto, Principal principal) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto == null) {
            throw new MemberException("로그인 되지 않은 회원입니다!");
        }
        Member member = memberService.findByLoginId(memberDto.getLoginId());

        if (!passwordEncoder.matches(updateDto.getPassword(), member.getPassword())) {
            throw new MemberException("현재 비밀번호가 맞지 않습니다!");
        }

        if (!updateDto.getNewPassword().equals(updateDto.getNewPasswordCheck())) {
            throw new MemberException("새로운 비밀번호가 맞지 않습니다");
        }


        if (memberService.updateMember(updateDto, member)) {
            return true;
        } else {
            throw new MemberException("회원 정보 수정에 실패했습니다");
        }
    }


//    @PostMapping("/add/reltag")
//    public void


    @GetMapping("/reviews")
    public ResponseEntity memberReview(Principal principal) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto == null) {
            throw new MemberException("로그인 되지 않은 회원입니다!");
        }
        Member findMember = memberService.findByLoginId(memberDto.getLoginId());

        List<Review> memberReviews = reviewService.findMemberReviews(findMember);
        List<ReviewDto> reviews = memberReviews.stream()
                .map(r -> new ReviewDto(r.getContent(),
                        r.getMember().getMemberName(),
                        r.getSubs().getSubsId(),
                        r.getSubs().getSubsName(),
                        r.getRating()))
                .toList();
        HashMap<String, List<ReviewDto>> result = new HashMap<>();
        result.put("reviews", reviews);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("reviews/delete/{subsId}")
    public boolean deleteReview(Principal principal,@PathVariable("subsId")Long subsId) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto == null) {
            throw new MemberException("로그인 되지 않은 회원입니다!");
        }

        memberService.removeReview(memberDto.getLoginId(),subsId);
        return true;
    }
}
