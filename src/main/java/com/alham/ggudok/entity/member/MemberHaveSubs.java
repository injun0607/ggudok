package com.alham.ggudok.entity.member;

import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.entity.subs.Subs;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 유저가 구독중인 서비스
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberHaveSubs {

    @Id
    @GeneratedValue
    @Column(name = "mhs_id")
    private Long mhsId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subs_id")
    private Subs subs;

    @Enumerated(STRING)
    private RankLevel rankLevel;

    /**
     * 멤버가 구독중인 구독서비스
     * @param member
     * @param subs
     * @return
     */
    public static MemberHaveSubs createHaveSubs(Member member, Subs subs, RankLevel rankLevel) {
        MemberHaveSubs memberHaveSubs = new MemberHaveSubs();
        memberHaveSubs.addMember(member);
        memberHaveSubs.addSubs(subs,rankLevel);

        return memberHaveSubs;
    }

    private void addMember(Member member) {
        this.member = member;
        member.getMemberHaveSubsList().add(this);
    }

    private void addSubs(Subs subs,RankLevel rankLevel) {
        this.subs = subs;
        updateRankLevel(rankLevel);
    }

    public void updateRankLevel(RankLevel rankLevel) {
        this.rankLevel = rankLevel;
    }


}
