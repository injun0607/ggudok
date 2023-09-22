package com.alham.ggudok.service;

import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.subs.SubsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MainService {
    private final MemberService memberService;
    private final TagService tagService;
    private final SubsService subsService;
}
