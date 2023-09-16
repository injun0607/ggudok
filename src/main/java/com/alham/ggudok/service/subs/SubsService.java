package com.alham.ggudok.service.subs;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.entity.subs.SubsRelTag;
import com.alham.ggudok.repository.subs.SubsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public Optional<Subs> findSubsByIdWithTag(Long subsId) {
        return subsRepository.findSubsByIdWithTag(subsId);
    }

    public Subs findSubsById(Long subsId) {
        return subsRepository.findById(subsId).get();
    }
}
