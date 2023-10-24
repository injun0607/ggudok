package com.alham.ggudok.repository.subs;

import com.alham.ggudok.dto.subs.SubsRecommendDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.entity.subs.SubsRelTag;

import java.util.List;
import java.util.Optional;

public interface SubsRepositoryCustom {

    List<SubsRank>findSubsByIdWithAllContent(Long subsId);

    /**
     * subsIdList를 매개변수로 받아 subsRank를 반환한다.
     * @param subsIdList
     * @return
     */
    List<SubsRank> findSubsRankByIdListWithAllContent(List<Long> subsIdList);

    Optional<List<Subs>> findSubsListByTag(Tag tag);

    /**
     * 태그리스트에 있는 태그이름으로
     * 해당 태그리스트에 있는 태그들을 찾는다.
     * @param tagList
     * @return
     */
    List<Subs> findSubsListByTagList(List<Tag> tagList);
    List<Subs> findSubsListByTagListOr(List<Tag> tagList);
    List<SubsRecommendDto> countHaveSubs();
    List<SubsRecommendDto> countFavorSubs();
    List<SubsRecommendDto> sumRating();
    List<SubsRecommendDto> countReview();
    List<Subs> findAllSubsList();

    List<Subs> findSubsBySubsIdListWithTag(List<Long> subsIdList);

    List<Subs> findBySubsListWithCategory(List<Subs> subsIdList);

    List<SubsRank> findSubsRankBySubsListWithAllContent(List<Long> subsIdList);

    List<SubsRelTag> findAllSubsRelTag();

    List<SubsRelTag> findSubRelTagBySubsId(Long subsId);

    List<SubsRelTag> findSubRelTagBySubsIdList(List<Long> subsIdList);

    List<Subs> findSubsListByQuery(String searchQuery);
}
