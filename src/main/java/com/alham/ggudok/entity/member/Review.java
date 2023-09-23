package com.alham.ggudok.entity.member;

import com.alham.ggudok.entity.subs.Subs;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long reviewId;

    private String content;

    private int rating;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subs_id")
    private Subs subs;


    public static Review createReview(Member member,Subs subs,String content,int rating) {
        Review review = new Review();
        review.addMember(member);
        review.setSubs(subs);
        review.content = content;
        return review;
    }

    private void addMember(Member member) {
        this.member = member;
        this.member.getReviews().add(this);

    }

    private void setSubs(Subs subs) {
        this.subs = subs;
    }





}
