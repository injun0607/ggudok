package com.alham.ggudok.service;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.util.GgudokUtil;
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

    /**
     * subsIdList를 받아서 해당 subs에 있는 태그들을 모두
     * List로 반환한다. (중복 허용 - 추천태그를 위한 메서드)
     * @param subsIdList
     * @return
     */
    public List<Tag> findTagsBySubIdList(List<Long> subsIdList) {
        List<Subs> subsList = subsRepository.findSubsBySubsIdListWithTag(subsIdList);
        List<Tag> tagList = new ArrayList<>();
        subsList.stream()
                .forEach(s->s.getSubsRelTags().stream().forEach(srt->tagList.add(srt.getTag())));

        return tagList;
    }


    public Tag checkAgeTag(int age) {
        String transAge = String.valueOf(age).substring(0, 1) + "0";
        return tagRepository.findByTagNameLikeAge(transAge);
    }


    public Tag checkGender(Gender gender) {
        if (gender == Gender.MAN) {
            return tagRepository.findTagByTagName(GgudokUtil.MAN);
        } else {
            return tagRepository.findTagByTagName(GgudokUtil.WOMAN);
        }
    }

    public List<Tag> findGenderAndAge(List<Tag> tagList) {

        List<Tag> result = new ArrayList<>();

        for (Tag tag : tagList) {
            if (GgudokUtil.isAgeFormat(tag.getTagName())) {
                result.add(tag);
            } else if (tag.getTagName().equals(GgudokUtil.MAN)||tag.getTagName().equals(GgudokUtil.WOMAN)) {
                result.add(tag);
            }
        }
        return result;
    }
}
