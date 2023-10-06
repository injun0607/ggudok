package com.alham.ggudok.entity.member;

import com.alham.ggudok.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberRelTag {


    @Id
    @GeneratedValue
    @Column(name = "mrt_id")
    private Long mrtId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    private int tagSort = Integer.MAX_VALUE - 10000;

    /**
     * 멤버 연관 태그 생성
     * @param member
     * @param tag
     * @return
     */
    public static MemberRelTag createRelTag(Member member, Tag tag) {
        MemberRelTag memberRelTag = new MemberRelTag();
        memberRelTag.addMember(member);
        memberRelTag.setTag(tag);

        return memberRelTag;
    }

    private void setTag(Tag tag) {
        this.tag = tag;
    }

    private void addMember(Member member) {
        this.member = member;
        member.getMemberRelTags().add(this);
    }

    public void updateTagSort(int tagSort) {
        this.tagSort = tagSort;
    }
}
