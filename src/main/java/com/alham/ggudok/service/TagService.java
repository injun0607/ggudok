package com.alham.ggudok.service;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final MemberRepository memberRepository;

    private final SubsRepository subsRepository;


    public List<Tag> findTagsByLoginId(String loginId) {

        Optional<Member> optionalMember = memberRepository.findByLoginIdWithTags(loginId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member.getMemberRelTags().stream().map(m -> m.getTag()).toList();
        } else {
            return new ArrayList<>();
        }
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
