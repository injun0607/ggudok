package com.alham.ggudok.scheduler;

import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.subs.SubsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTask {

    private final SubsService subsService;

    private final MemberService memberService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void executeTask() {
        subsService.updateRecommendSort();
        memberService.userRecommendTag();
        log.info("구독서비스 스케줄러 실행()");
    }
}
