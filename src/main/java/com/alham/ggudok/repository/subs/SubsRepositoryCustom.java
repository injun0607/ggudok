package com.alham.ggudok.repository.subs;

import com.alham.ggudok.dto.subs.SubsRecommendDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubsRepositoryCustom {

    List<SubsRank>findSubsByIdWithAllContent(@Param("subsId") Long subsId);

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



}
