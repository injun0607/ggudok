package com.alham.ggudok.entity.subs;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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

    @OneToMany(mappedBy = "subsRank",cascade = ALL)
    private List<SubsContents> contents;





}
