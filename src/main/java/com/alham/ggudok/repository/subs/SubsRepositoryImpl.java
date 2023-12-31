package com.alham.ggudok.repository.subs;


import com.alham.ggudok.dto.subs.QSubsRecommendDto;
import com.alham.ggudok.dto.subs.SubsRecommendDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.alham.ggudok.entity.QTag.tag;
import static com.alham.ggudok.entity.member.QMemberFavorSubs.memberFavorSubs;
import static com.alham.ggudok.entity.member.QMemberHaveSubs.memberHaveSubs;
import static com.alham.ggudok.entity.member.QReview.review;
import static com.alham.ggudok.entity.subs.QCategory.*;
import static com.alham.ggudok.entity.subs.QSubs.subs;
import static com.alham.ggudok.entity.subs.QSubsContent.subsContent;
import static com.alham.ggudok.entity.subs.QSubsRank.subsRank;
import static com.alham.ggudok.entity.subs.QSubsRelTag.subsRelTag;
@Repository
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
    public List<SubsRank> findSubsRankByIdListWithAllContent(List<Long> subsIdList) {
        return queryFactory.selectFrom(subsRank)
                .join(subsRank.contents, subsContent).fetchJoin()
                .join(subsRank.subs, subs).fetchJoin()
                .where(subsRank.subs.subsId.in(subsIdList))
                .fetch();
    }

    @Override
    public List<Subs> findBySubsListWithCategory(List<Subs> subsList) {
        return queryFactory
                .selectFrom(subs)
                .leftJoin(subs.category, category).fetchJoin()
                .where(subs.in(subsList))
                .fetch();
    }

    @Override
    public Optional<List<Subs>> findSubsListByTag(Tag tagHint) {
        return Optional.of(queryFactory
                .selectFrom(subs)
                .join(subs.subsRelTags, subsRelTag).fetchJoin()
                .join(subsRelTag.tag, tag).fetchJoin()
                .where(tag.tagName.eq(tagHint.getTagName()))
                .orderBy(subs.recommendSort.asc())
                .fetch());
    }

    @Override
    public List<Subs> findSubsListByTagList(List<Tag> tagList) {
        List<String> tagNameList = tagList.stream().map(t -> t.getTagName()).toList();
        QSubs subsSc = new QSubs("subSc");

        return queryFactory
                .selectFrom(subs)
                .leftJoin(subs.subsRelTags, subsRelTag).fetchJoin()
                .leftJoin(subsRelTag.tag,tag).fetchJoin()
                .where()
                .orderBy(subs.recommendSort.asc())
                .fetch();
    }

    @Override
    public List<Subs> findSubsListByTagListOr(List<Tag> tagList) {
        List<String> tagNameList = tagList.stream().map(t -> t.getTagName()).toList();

        return queryFactory
                .selectFrom(subs)
                .join(subs.subsRelTags, subsRelTag).fetchJoin()
                .join(subsRelTag.tag, tag).fetchJoin()
                .where(tagNameListEq(tagNameList))
                .groupBy(subs.subsId)
                .orderBy(subs.recommendSort.asc())
                .fetch();
    }

    @Override
    public List<SubsRecommendDto> countHaveSubs() {
        List<SubsRecommendDto> subsRecommendDtoList = queryFactory
                .select(new QSubsRecommendDto(memberHaveSubs.subs.subsId.as("subsId"),
                        memberHaveSubs.count().intValue()))
                .from(memberHaveSubs)
                .groupBy(memberHaveSubs.subs.subsId)
                .orderBy(memberHaveSubs.count().desc())
                .fetch();


        return subsRecommendDtoList;
    }

    @Override
    public List<SubsRecommendDto> countFavorSubs() {
        List<SubsRecommendDto> subsRecommendDtoList = queryFactory
                .select(new QSubsRecommendDto(memberFavorSubs.subs.subsId.as("subsId"),
                        memberFavorSubs.count().intValue()))
                .from(memberFavorSubs)
                .groupBy(memberFavorSubs.subs.subsId)
                .orderBy(memberFavorSubs.count().desc())
                .fetch();

        return subsRecommendDtoList;
    }

    @Override
    public List<SubsRecommendDto> sumRating() {
        List<SubsRecommendDto> subsRecommendDtoList = queryFactory
                .select(new QSubsRecommendDto(review.subs.subsId.as("subsId"),
                        review.rating.sum()))
                .from(review)
                .groupBy(review.subs.subsId)
                .fetch();

        return subsRecommendDtoList;

    }

    @Override
    public List<SubsRecommendDto> countReview() {
        List<SubsRecommendDto> subsRecommendDtoList = queryFactory
                .select(new QSubsRecommendDto(review.subs.subsId.as("subsId"),
                        review.count().intValue()))
                .from(review)
                .groupBy(review.subs.subsId)
                .orderBy(review.count().desc())
                .fetch();

        return subsRecommendDtoList;
    }

    @Override
    public List<Subs> findAllSubsList() {
        return queryFactory
                .selectFrom(subs)
                .leftJoin(subs.subsRelTags, subsRelTag).fetchJoin()
                .leftJoin(subsRelTag.tag,tag).fetchJoin()
                .orderBy(subs.recommendSort.asc())
                .fetch();
    }

    @Override
    public List<Subs> findSubsBySubsIdListWithTag(List<Long> subsIdList) {
        return queryFactory
                .selectFrom(subs)
                .leftJoin(subs.subsRelTags, subsRelTag).fetchJoin()
                .leftJoin(subsRelTag.tag, tag).fetchJoin()
                .where(subs.subsId.in(subsIdList))
                .orderBy(subs.recommendSort.asc())
                .fetch();
    }

    @Override
    public List<SubsRank> findSubsRankBySubsListWithAllContent(List<Long> subsIdList) {
        return queryFactory.selectFrom(subsRank)
                .join(subsRank.contents, subsContent).fetchJoin()
                .where(subsRank.subs.subsId.in(subsIdList))
                .fetch();
    }

    @Override
    public List<SubsRelTag> findAllSubsRelTag() {

        return queryFactory.selectFrom(subsRelTag)
                .join(subsRelTag.tag, tag).fetchJoin()
                .join(subsRelTag.subs, subs).fetchJoin()
                .fetch();
    }

    @Override
    public List<SubsRelTag> findSubRelTagBySubsId(Long subsId) {
        return queryFactory.selectFrom(subsRelTag)
                .join(subsRelTag.tag, tag).fetchJoin()
                .join(subsRelTag.subs,subs).fetchJoin()
                .where(subsRelTag.subs.subsId.eq(subsId))
                .fetch();
    }

    @Override
    public List<SubsRelTag> findSubRelTagBySubsIdList(List<Long> subsIdList) {
        return queryFactory.selectFrom(subsRelTag)
                .join(subsRelTag.tag, tag).fetchJoin()
                .join(subsRelTag.subs,subs).fetchJoin()
                .where(subsRelTag.subs.subsId.in(subsIdList))
                .fetch();
    }

    @Override
    public List<Subs> findSubsListByQuery(String searchQuery) {
        return queryFactory.selectFrom(subs)
                .join(subs.subsRelTags, subsRelTag).fetchJoin()
                .join(subsRelTag.tag, tag).fetchJoin()
                .where(subs.subsName.contains(searchQuery).or(tag.tagName.contains(searchQuery)))
                .fetch();
    }



    private BooleanExpression tagNameListEq(List<String> tagNameList) {
        return !tagNameList.isEmpty() ? tag.tagName.in(tagNameList) : null;
    }


}
