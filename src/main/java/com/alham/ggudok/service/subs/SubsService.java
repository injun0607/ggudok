package com.alham.ggudok.service.subs;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Tuple> countHaveSubs() {
        return null;
    }

    public void countFavorSubs() {
    }

    public void sumRating() {

    }

    public void countReview() {

    }
}
