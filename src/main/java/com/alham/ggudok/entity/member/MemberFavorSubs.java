package com.alham.ggudok.entity.member;

import com.alham.ggudok.entity.subs.Subs;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 유저가 좋아요 표시누른 구독서비스
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberFavorSubs {

    @Id
    @GeneratedValue
    @Column(name = "mfs_id")
    private Long mfsId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subs_id")
    private Subs subs;

    /**
     * 멤버가 좋아하는 구독서비스
     * @param member
     * @param subs
     * @return MemberFavorSubs
     */
    public static MemberFavorSubs createFavorSubs(Member member, Subs subs) {
        MemberFavorSubs memberFavorSubs = new MemberFavorSubs();
        memberFavorSubs.addMember(member);
        memberFavorSubs.setSubs(subs);
        return memberFavorSubs;
    }


    private void addMember(Member member) {
        this.member = member;
        member.getMemberFavorSubsList().add(this);
    }

    private void setSubs(Subs subs) {
        this.subs = subs;
    }



}
