package com.alham.ggudok.entity.subs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class EventSubs {

    @Id
    @GeneratedValue
    @Column(name = "event_id")
    private Long eventId;

    @OneToOne
    @JoinColumn(name = "subs_id")
    private Subs subs;

    private String infoTag = "";

    //이벤트 간략정보
    private String info = "";

    //시작일
    private LocalDateTime startDate = LocalDateTime.now();

    //종료일
    private LocalDateTime endDate = LocalDateTime.now();

    //활성화 여부
    private Boolean isValid = false;

    public EventSubs(Subs subs) {
        this.subs = subs;
    }

    public void updateEventSubs(String infoTag, String info, LocalDateTime startDate, LocalDateTime endDate, boolean isValid) {
        this.infoTag = infoTag;
        this.info = info;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isValid = isValid;
    }
}
