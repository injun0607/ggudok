package com.alham.ggudok.service.subs;

import com.alham.ggudok.dto.subs.SubsRecommendDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.repository.subs.SubsRepository;
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




    /**
     * 해당 subs 와 관련있는 태그들을 모두 찾아온다.
     * @param subsId
     * @return
     */
    public List<Tag> findTagsBySubsId(Long subsId) {
        Optional<Subs> subsByIdWithTag = subsRepository.findSubsByIdWithTag(subsId);
        List<Tag> tags = new ArrayList<>();

        subsByIdWithTag
                .ifPresent(subs -> subs.getSubsRelTags().stream()
                        .forEach(subsRelTag -> tags.add(subsRelTag.getTag()))
                );

        return tags;
    }

    public List<SubsRank> findRanksBySubsId(Long subsId) {
        Optional<Subs> subsByIdWithRank = subsRepository.findSubsByIdWithRank(subsId);
        List<SubsRank> subsRanks = new ArrayList<>();

        subsByIdWithRank
                .ifPresent(subs -> subs.getSubsRanks().stream()
                        .forEach(subsRank -> subsRanks.add(subsRank))
                );

        return subsRanks;
    }

    public List<SubsRank> findContentBySubsId(Long subsId) {
        return subsRepository.findSubsByIdWithAllContent(subsId);
    }

    public Subs findSubsByIdWithTag(Long subsId) {
        Optional<Subs> subsByIdWithTag = subsRepository.findSubsByIdWithTag(subsId);
        if (subsByIdWithTag.isPresent()) {
            return subsByIdWithTag.get();
        } else {
            return new Subs("no-subs");
        }
    }

    public Subs findSubsById(Long subsId) {
        return subsRepository.findById(subsId).get();
    }

    /**
     * 해당 태그를 가지고있는 Subs들을 모두찾아 반환한다.
     * @param tag
     * @return
     */

    public List<Subs> findSubsByTag(Tag tag) {
        Optional<List<Subs>> optionalSubsList = subsRepository.findSubsListByTag(tag);
        if (optionalSubsList.isPresent()) {
            return optionalSubsList.get();
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<Subs> updateRecommendSort() {
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

    @Transactional
    public void likeSubs(Subs subs) {
        subs.likeSubs();
    }

    @Transactional
    public void dislike(Subs subs) {
        subs.dislikeSubs();
    }

    public Subs findBySubsName(String subsName) {
        return subsRepository.findSubsBySubsName(subsName);
    }

    public void buySubs(Long subsId, RankLevel rankLevel) {


    }

    public List<SubsRecommendDto> countHaveSubs() {
        return subsRepository.countHaveSubs();
    }

    public List<SubsRecommendDto> countFavorSubs() {
        return subsRepository.countFavorSubs();
    }

    public List<SubsRecommendDto> sumRating() {
        return subsRepository.sumRating();

    }

    public List<SubsRecommendDto> countReview() {
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
        Map<Long, Double> resultMap = new HashMap<>();

        List<SubsRecommendDto> haveSubsScore = countHaveSubs();
        subsScoreCal(resultMap, haveSubsScore, 4);

        List<SubsRecommendDto> haveFavorScore = countFavorSubs();
        subsScoreCal(resultMap, haveFavorScore, 3);

        List<SubsRecommendDto> reviewRatingScore = sumRating();
        subsScoreCal(resultMap, reviewRatingScore, 0.4);

        List<SubsRecommendDto> haveReviewScore = countReview();
        subsScoreCal(resultMap, haveReviewScore, 1);

        Map<Long, Double> sortedMap = resultMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2)->e2,
                        LinkedHashMap::new
                ));

        return sortedMap;
    }

    public Map<Long, Double> subsScoreCal(Map<Long, Double> resultMap, List<SubsRecommendDto> recommendDtoList, double calScore) {
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
       return subsRepository.findRecommendSubsList();
    }

    public List<Subs> findRecommendSubsListByTag() {
        return subsRepository.findRecommendSubsListByTag();
    }
}
