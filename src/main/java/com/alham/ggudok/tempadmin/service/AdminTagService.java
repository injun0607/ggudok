package com.alham.ggudok.tempadmin.service;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminTagService {

    private final TagRepository tagRepository;

    public Tag saveTag(String tagName) {

        return tagRepository.save(new Tag(tagName));
    }

    public List<Tag> findAllTag() {
        return tagRepository.findAll();
    }




}
