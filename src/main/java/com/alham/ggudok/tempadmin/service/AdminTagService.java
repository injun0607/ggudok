package com.alham.ggudok.tempadmin.service;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminTagService {

    private final TagRepository tagRepository;

    private final SubsRepository subsRepository;

    public Tag saveTag(String tagName) {

        return tagRepository.save(new Tag(tagName));
    }

    public List<Tag> findAllTag() {
        return tagRepository.findAll();
    }

    public List<Tag> findTagsBySubsId(Long subsId) {

        Optional<Subs> optionalSubs = subsRepository.findSubsByIdWithTag(subsId);
        if (optionalSubs.isPresent()) {
            Subs subs = optionalSubs.get();
            return subs.getSubsRelTags().stream().map(subsRelTag -> subsRelTag.getTag()).toList();
        } else {
            return new ArrayList<>();
        }

    }





}
