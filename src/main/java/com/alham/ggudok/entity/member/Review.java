package com.alham.ggudok.entity.member;

import com.alham.ggudok.entity.subs.Subs;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long reviewId;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subs_id")
    private Subs subs;




}
