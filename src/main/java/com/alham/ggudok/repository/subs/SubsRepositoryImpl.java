package com.alham.ggudok.repository.subs;


import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.alham.ggudok.entity.QTag.tag;
import static com.alham.ggudok.entity.subs.QSubs.subs;
import static com.alham.ggudok.entity.subs.QSubsContent.subsContent;
import static com.alham.ggudok.entity.subs.QSubsRank.subsRank;
import static com.alham.ggudok.entity.subs.QSubsRelTag.subsRelTag;

public class SubsRepositoryImpl implements SubsRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public SubsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<SubsRank> findSubsByIdWithAllContent(Long subsId) {
        return queryFactory.selectFrom(subsRank)
                .join(subsRank.contents, subsContent).fetchJoin()
                .where(subsRank.subs.subsId.eq(subsId))
                .fetch();
    }

    @Override
    public Optional<List<Subs>> findSubsListByTag(Tag tagHint) {
        return Optional.of(queryFactory
                .selectFrom(subs)
                .join(subs.subsRelTags, subsRelTag).fetchJoin()
                .join(subsRelTag.tag, tag).fetchJoin()
                .where(tag.tagName.eq(tagHint.getTagName()))
                .fetch());
    }

    @Override
    public Optional<List<Subs>> findSubsListByTagList(List<Tag> tagList) {
        return Optional.of(queryFactory
                .selectFrom(subs)
                .join(subs.subsRelTags, subsRelTag).fetchJoin()
                .join(subsRelTag.tag, tag).fetchJoin()
                .where()
                .fetch());
    }
}
