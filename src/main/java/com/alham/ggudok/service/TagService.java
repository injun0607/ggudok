package com.alham.ggudok.service;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag checkAgeTag(int age) {
        String transAge = String.valueOf(age).substring(0, 1) + "0";
        return tagRepository.findByTagNameLikeAge(transAge);
    }


    public Tag checkGender(Gender gender) {
        if (gender == Gender.MAN) {
            return tagRepository.findTagByTagName("남성");
        } else {
            return tagRepository.findTagByTagName("여성");
        }
    }
}
