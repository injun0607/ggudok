package com.alham.ggudok.entity.subs;

import com.alham.ggudok.entity.ImageResourceEntity;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Review;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 구독 서비스
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "subsId")
public class Subs extends ImageResourceEntity {
    @Id
    @GeneratedValue
    @Column(name = "subs_id")
    private Long subsId;

    private String subsName;

    //구독서비스 설명
    @Column(length = 2000)
    private String info;

    private Integer recommendSort = Integer.MAX_VALUE - 10000;

    private int ratingAvg;

    @OneToMany(mappedBy = "subs", cascade = ALL,orphanRemoval = true)
    List<SubsRank> subsRanks = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(mappedBy = "subs", cascade = ALL,orphanRemoval = true)
    private List<SubsRelTag> subsRelTags = new ArrayList<>();

    @OneToMany(mappedBy = "subs")
    private List<Review> reviews = new ArrayList<>();

    public Subs(String subsName) {
        this.subsName = subsName;
    }

    public void addCategory(Category category) {
        this.category = category;
        category.getSubsList().add(this);
    }

    public void updateCategory(Category updateCategory) {
        this.category.getSubsList().remove(this);
        this.category = updateCategory;
    }

    public void addTag(Tag tag) {
        SubsRelTag subsRelTag = SubsRelTag.createSubsRelTag(this, tag);
    }


    public void updateRecommendSort(int sort) {
        this.recommendSort = sort;
    }

    public void updateRatingAvg(int ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public void updateSubsName(String subsName) {
        this.subsName = subsName;
    }

    public void updateInfo(String subsInfo) {
        this.info = subsInfo;
    }

    public void updateSubsRank(List<SubsRank> alreadySubsRank) {
        this.subsRanks = alreadySubsRank;
        for (SubsRank subsRank : alreadySubsRank) {
            subsRank.updateSubs(this);
        }
    }

    /**
     * 수정시 사용하는 메소드
     * @param tagList
     */
    public void updateSubsRelTags(List<Tag> tagList) {
        this.subsRelTags.clear();
        for (Tag tag : tagList) {
            SubsRelTag.createSubsRelTag(this, tag);
        }
    }
}
