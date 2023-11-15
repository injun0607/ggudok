package com.alham.ggudok.service;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRelTag;
import com.alham.ggudok.repository.TagRepository;
import com.alham.ggudok.repository.member.MemberRepository;
import com.alham.ggudok.repository.subs.SubsRepository;
import com.alham.ggudok.util.GgudokUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final MemberRepository memberRepository;

    private final SubsRepository subsRepository;


    public List<Tag> findTagsByLoginId(String loginId) {
        log.info("findTagsByLoginId()");
        Optional<Member> optionalMember = memberRepository.findByLoginIdWithTags(loginId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member.getMemberRelTags().stream().map(m -> m.getTag()).toList();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Tag> findTagsBySubsId(Long subsId) {
        log.info("findTagsBySubsId()");
        Optional<Subs> optionalSubs = subsRepository.findSubsByIdWithTag(subsId);
        if (optionalSubs.isPresent()) {
            Subs subs = optionalSubs.get();
            return subs.getSubsRelTags().stream().map(subsRelTag -> subsRelTag.getTag()).toList();
        } else {
            return new ArrayList<>();
        }

    }

    /**
     * 성능 최적화
     * subsRelTagList 를 불러와 Map에 subsId 에해당하는 Tag들을 모두 넣어 반환
     * @return Map<Long,List<Tag>> resultMap
     */
    public Map<Long,List<Tag>> findAllSubsTag() {
        log.info("findAllSubsTag()");
        List<SubsRelTag> subsRelTagList = subsRepository.findAllSubsRelTag();
        Map<Long, List<Tag>> resultMap = new HashMap<>();

        for (SubsRelTag subsRelTag : subsRelTagList) {
            Long subsId = subsRelTag.getSubs().getSubsId();
            Tag tag = subsRelTag.getTag();
            if (resultMap.containsKey(subsId)) {
                resultMap.get(subsId).add(tag);
            }else{
                List<Tag> tagList = new ArrayList<>();
                tagList.add(tag);
                resultMap.put(subsId, tagList);
            }
        }

        return resultMap;
    }

    /**
     * subsRelTagList 를 불러와 Map에 subsId 에해당하는 Tag들을 모두 넣어 반환
     *
     * @param subsIdList
     * @return
     */
    public Map<Long,List<Tag>> findTagListBySubsIdList(List<Long> subsIdList) {
        log.info("findTagListBySubsIdList()");
        List<SubsRelTag> subsRelTagList = subsRepository.findSubRelTagBySubsIdList(subsIdList);
        Map<Long, List<Tag>> resultMap = new HashMap<>();

        for (SubsRelTag subsRelTag : subsRelTagList) {
            Long subsId = subsRelTag.getSubs().getSubsId();
            Tag tag = subsRelTag.getTag();
            if (resultMap.containsKey(subsId)) {
                resultMap.get(subsId).add(tag);
            }else{
                List<Tag> tagList = new ArrayList<>();
                tagList.add(tag);
                resultMap.put(subsId, tagList);
            }
        }

        return resultMap;

    }

    /**
     * subsIdList를 받아서 해당 subs에 있는 태그들을 모두
     * List로 반환한다. (리스트내 동일한 태그 중복 허용 - 추천태그를 위한 메서드)
     * @param subsIdList
     * @return
     */
    public List<Tag> findTagsBySubIdList(List<Long> subsIdList) {
        log.info("findTagsBySubIdList()");
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

    public void deleteTag(Long tagId) {
        log.info("deleteTag()");
        Tag tag = tagRepository.findById(tagId).get();

        subsRepository.deleteSubsRelTagByTagId(tagId);
        memberRepository.deleteMemberRelTagByTagId(tagId);

        tagRepository.delete(tag);


    }
}
