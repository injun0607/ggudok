package com.alham.ggudok.service.subs;

import com.alham.ggudok.dto.subs.SubsRecommendDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.EventSubs;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.repository.subs.CategoryRepository;
import com.alham.ggudok.repository.subs.EventRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.util.GgudokUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SubsService {

    private final SubsRepository subsRepository;

    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    /**
     * 해당 subs 와 관련있는 태그들을 모두 찾아온다.
     * @param subsId
     * @return
     */
    public List<Tag> findTagsBySubsId(Long subsId) {
        log.info("findTagsBySubsId()");
        Optional<Subs> subsByIdWithTag = subsRepository.findSubsByIdWithTag(subsId);
        List<Tag> tags = new ArrayList<>();

        subsByIdWithTag
                .ifPresent(subs -> subs.getSubsRelTags().stream()
                        .forEach(subsRelTag -> tags.add(subsRelTag.getTag()))
                );

        return tags;
    }

    public List<SubsRank> findRanksBySubsId(Long subsId) {
        log.info("findRanksBySubsId()");
        Optional<Subs> subsByIdWithRank = subsRepository.findSubsByIdWithRank(subsId);
        List<SubsRank> subsRanks = new ArrayList<>();

        subsByIdWithRank
                .ifPresent(subs -> subs.getSubsRanks().stream()
                        .forEach(subsRank -> subsRanks.add(subsRank))
                );

        return subsRanks;
    }

    public Map<Long, List<SubsRank>> findSubsRankMap(List<Long> subsIdList) {
        log.info("findSubsRankMap()");
        List<SubsRank> subsRankList = subsRepository.findSubsRankByIdListWithAllContent(subsIdList);
        Map<Long, List<SubsRank>> resultMap = new HashMap<>();

        for (SubsRank subsRank : subsRankList) {
            Long subsId = subsRank.getSubs().getSubsId();
            if (resultMap.containsKey(subsId)) {
                resultMap.get(subsId).add(subsRank);

            } else {
                List<SubsRank> subsRanks = new ArrayList<>();
                subsRanks.add(subsRank);

                resultMap.put(subsId, subsRanks);
            }

        }

        return resultMap;
    }

    public List<SubsRank> findContentBySubsId(Long subsId) {
        log.info("findContentBySubsId()");
        return subsRepository.findSubsByIdWithAllContent(subsId);
    }

    public Subs findSubsByIdWithTag(Long subsId) {
        log.info("findSubsByIdWithTag()");
        Optional<Subs> subsByIdWithTag = subsRepository.findSubsByIdWithTag(subsId);
        if (subsByIdWithTag.isPresent()) {
            return subsByIdWithTag.get();
        } else {
            return new Subs("no-subs");
        }
    }

    public Subs findSubsById(Long subsId) {
        log.info("findSubsById()");
        return subsRepository.findById(subsId).get();
    }

    /**
     * 해당 태그를 가지고있는 Subs들을 모두찾아 반환한다.
     * @param tag
     * @return
     */

    public List<Subs> findSubsByTag(Tag tag) {
        log.info("findSubsByTag()");
        Optional<List<Subs>> optionalSubsList = subsRepository.findSubsListByTag(tag);
        if (optionalSubsList.isPresent()) {
            return optionalSubsList.get();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * subs들의 추천순위를 업데이트 하는 메소드
     * @return
     */

    @Transactional
    public List<Subs> updateRecommendSort() {
        log.info("updateRecommendSort()");
        List<Subs> allSubs = subsRepository.findAll();


        Map<Long, Double> subsMap = recommendSubs();
        Set<Long> subsIdList = subsMap.keySet();

        Map<Long, Integer> sortRank = new HashMap<>();

        int sort = 0;
        for (Long subsId : subsIdList) {
            sortRank.put(subsId, ++sort);
        }

        for (Subs subs : allSubs) {
            if (sortRank.containsKey(subs.getSubsId())) {
                subs.updateRecommendSort(sortRank.get(subs.getSubsId()));
            }

        }

        return allSubs;

    }

    /**
     *
     * @param subsList(With Tag)
     * @param tag
     * @return tag로 걸러진 subsList
     */
    public List<Subs> findSubsListByTag(List<Subs> subsList,Tag tag) {
        log.info("findSubsListByTag()");
        return subsList.stream()
                .filter(subs -> subs.getSubsRelTags().stream().filter(srt -> srt.getTag().equals(tag)).findAny().isPresent())
                .collect(Collectors.toList());
    }

    /**
     *
     * @param subsList(With Tag)
     * @param tagList
     * @return tagList에 있는 tag들중 한개라도 포함된 subsList 반환
     */
    public List<Subs> findSubsListByTag(List<Subs> subsList,List<Tag> tagList) {
        log.info("findSubsListByTag()");
        return subsList.stream()
                .filter(subs ->
                        subs.getSubsRelTags().stream()
                                .filter(srt -> tagList.contains(srt)).findAny().isPresent())
                .collect(Collectors.toList());
    }


    /**
     *
     * @param subsList(With Tag)
     * @param tagList
     * @return 태그리스트에 있는 태그들이 모두포함된 subsList
     */
    public List<Subs> findSubsListByTagList(List<Subs> subsList ,List<Tag> tagList) {
        log.info("findSubsListByTagList()");
        return subsList.stream().filter(
                subs -> subs.getSubsRelTags().stream()
                        .map(srt -> srt.getTag())
                        .collect(Collectors.toList())
                        .containsAll(tagList)
        ).collect(Collectors.toList());
    }

    public Subs findBySubsName(String subsName) {
        log.info("findBySubsName()");
        return subsRepository.findSubsBySubsName(subsName);
    }

    public List<SubsRecommendDto> countHaveSubs() {
        log.info("countHaveSubs()");
        return subsRepository.countHaveSubs();
    }

    public List<SubsRecommendDto> countFavorSubs() {
        log.info("countFavorSubs()");
        return subsRepository.countFavorSubs();
    }

    public List<SubsRecommendDto> sumRating() {
        log.info("sumRating()");
        return subsRepository.sumRating();

    }

    public List<SubsRecommendDto> countReview() {
        log.info("countReview()");
        return subsRepository.countReview();
    }

    /**
     * 추천 구독서비스 알고리즘
     * 1. 구매 횟수 당 4점
     * 2. 좋아요 횟수 당 3점
     * 3. 별점이 높은 것 (별점당 0.4점)
     * 4. 리뷰 개수 당 1점
     * return Map<Long,Double> key: subsId, value: score
     */
    public Map<Long,Double> recommendSubs() {
        log.info("recommendSubs()");
        Map<Long, Double> resultMap = new HashMap<>();

        List<SubsRecommendDto> haveSubsScore = countHaveSubs();
        subsScoreCal(resultMap, haveSubsScore, 4);

        List<SubsRecommendDto> haveFavorScore = countFavorSubs();
        subsScoreCal(resultMap, haveFavorScore, 3);

        List<SubsRecommendDto> reviewRatingScore = sumRating();
        subsScoreCal(resultMap, reviewRatingScore, 0.4);

        List<SubsRecommendDto> haveReviewScore = countReview();
        subsScoreCal(resultMap, haveReviewScore, 1);

        Map<Long, Double> sortedMap = GgudokUtil.mapSortByValueDescending(resultMap);

        return sortedMap;
    }

    public Map<Long, Double> subsScoreCal(Map<Long, Double> resultMap, List<SubsRecommendDto> recommendDtoList, double calScore) {
        log.info("subsScoreCal()");
        for (SubsRecommendDto subsRecommendDto : recommendDtoList) {
            Long subsId = subsRecommendDto.getSubsId();
            Integer score = subsRecommendDto.getScore();
            if (resultMap.containsKey(subsId)) {
                resultMap.put(subsId, resultMap.get(subsId) + score * calScore);
            }else{
                resultMap.put(subsId, score * calScore);
            }
        }

        return resultMap;
    }

    public List<Subs> findRecommendSubsList() {
        log.info("findRecommendSubsList()");
       return subsRepository.findRecommendSubsList();
    }

    public List<Subs> findSubsListByTagListOr(List<Tag> tagList) {
        log.info("findSubsListByTagListOr()");
        return subsRepository.findSubsListByTagListOr(tagList);
    }

    public List<Subs> findAllSubsList() {
        log.info("findAllSubsList()");
        return subsRepository.findAllSubsList();
    }


    @Transactional
    public void updateRatingAvg(Subs subs,int ratingAvg) {
        log.info("updateRatingAvg()");
        subs.updateRatingAvg(ratingAvg);
    }

    /*
    등록된 이벤트 불러오기
     */
    public List<EventSubs> findAllEventSubs() {
        log.info("findAllEventSubs()");

        return eventRepository.findAllWithSubsValid();
    }

    public Subs findSubsByIdWithCategory(Long subsId) {
        log.info("findSubsByIdWithCategory()");
        return subsRepository.findByIdWithCategory(subsId);
    }

    public List<SubsRank> findSubsRankBySubsListWithAllContent(List<Long> subsIdList) {
        log.info("findSubsRankBySubsListWithAllContent()");
        return subsRepository.findSubsRankBySubsListWithAllContent(subsIdList);
    }

    /**
     * subsList에 있는 subs들의 category들을 일괄적으로 가져옴
     * @param subsList
     * @return List<Subs>
     */
    public List<Subs> findBySubsListWithCategory(List<Subs> subsList) {
        log.info("findBySubsListWithCategory()");
        return subsRepository.findBySubsListWithCategory(subsList);
    }


    public List<Subs> findSubsListByQuery(String searchQuery) {
        log.info("findSubsListByQuery()");
        return subsRepository.findSubsListByQuery(searchQuery);

    }

    public List<Category> findAllCategory() {
        log.info("findAllCategory()");
        return categoryRepository.findAll();
    }
}
