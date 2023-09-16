package com.alham.ggudok.entity.subs;

import com.alham.ggudok.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SubsRelTag {
    @Id
    @GeneratedValue
    @Column(name = "srt_id")
    private Long srtId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subs_id")
    private Subs subs;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public static SubsRelTag createSubsRelTag(Subs subs, Tag tag) {
        SubsRelTag subsRelTag = new SubsRelTag(subs,tag);
        subs.getSubsRelTags().add(subsRelTag);
        return subsRelTag;
    }

    private SubsRelTag(Subs subs, Tag tag) {
        this.subs = subs;
        this.tag = tag;
    }
}
