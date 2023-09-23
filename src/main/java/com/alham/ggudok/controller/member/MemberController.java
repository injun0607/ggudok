package com.alham.ggudok.controller.member;

import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.dto.member.MemberUpdateDto;
import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.exception.ErrorResult;
import com.alham.ggudok.exception.member.MemberException;
import com.alham.ggudok.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "*")
@Slf4j
public class MemberController {

    private final MemberService memberService;
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


    @PostMapping("/update")
    private String updateMember(MemberUpdateDto updateDto) {

        memberService.updateMember(updateDto);
        return null;
    }

//    @PostMapping("/add/reltag")
//    public void




}
