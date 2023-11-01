package com.alham.ggudok.entity.subs;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "rank_Id")
public class SubsRank {

    @Id
    @GeneratedValue
    @Column(name = "rank_id")
    private Long rankId;

    private String rankName;
    private int price;

    @Enumerated(STRING)
    private RankLevel rankLevel;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subs_id")
    private Subs subs;

    @OneToMany(mappedBy = "subsRank", cascade = ALL, orphanRemoval = true)
    private List<SubsContent> contents = new ArrayList<>();

    public SubsRank(String rankName, int price, RankLevel rankLevel) {
        this.rankName = rankName;
        this.price = price;
        this.rankLevel = rankLevel;
    }

    public void addSubs(Subs subs) {
        this.subs = subs;
        subs.getSubsRanks().add(this);
    }

    public void updateSubs(Subs subs) {
        this.subs = subs;
    }

    public void updateSubsRank(String rankName, int price) {
        this.rankName = rankName;
        this.price = price;
    }

    public void updateSubsContents(List<SubsContent> contentList) {
        this.contents.clear();
        for (SubsContent subsContent : contentList) {
            subsContent.addSubsRank(this);
        }
    }

    public void deleteContents() {
        this.contents.clear();
    }
}
