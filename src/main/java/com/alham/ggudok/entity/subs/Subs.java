package com.alham.ggudok.entity.subs;

import com.alham.ggudok.entity.member.Review;
import jakarta.persistence.*;
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
public class Subs {
    @Id
    @GeneratedValue
    @Column(name = "subs_id")
    private Long subsId;

    private String subsName;

    @OneToMany(mappedBy = "subs",cascade = ALL)
    List<SubsRank> subsRanks = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;



    @OneToMany(mappedBy = "subs",cascade = ALL)
    private List<SubsRelTag> subsRelTags = new ArrayList<>();

    @OneToMany(mappedBy = "subs")
    private List<Review> reviews = new ArrayList<>();

    public Subs(String subsName) {
        this.subsName = subsName;
    }
}
