package com.alham.ggudok.controller.subs;

import com.alham.ggudok.config.security.SecurityUtils;
import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.dto.member.MemberSubsDto;
import com.alham.ggudok.dto.subs.*;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.subs.CategoryService;
import com.alham.ggudok.service.subs.SubsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subs")
@CrossOrigin(origins = "*")
public class SubsController {

    private final CategoryService categoryService;
    private final SubsService subsService;
    private final TagService tagService;

    private final MemberService memberService;

    @GetMapping("/{category_eng}")
    public SubsMainDto showSubsWithCategory(@PathVariable("category_eng") String categoryEng) {

        Category category = categoryService.findCateByEngWithSubs(categoryEng);
        List<Subs> subsList = category.getSubsList();

        List<SubsDto> subsDtoList = new ArrayList<>();


        //subs 관련 데이터 가공
        for (Subs subs : subsList) {

            SubsDto subsDto = new SubsDto();
            List<SubsRankDto> subsRankDtos = new ArrayList<>();
            subsDto.setTags(tagService.findTagsBySubsId(subs.getSubsId()));

            //subsRankData 가공
            List<SubsRank> ranksBySubsId = subsService.findRanksBySubsId(subs.getSubsId());

            for (SubsRank subsRank : ranksBySubsId) {
                SubsRankDto subsRankDto = new SubsRankDto();
                subsRankDto.setRankName(subsRank.getRankName());
                subsRankDto.setPrice(subsRank.getPrice());
                subsRankDto.setRankLevel(subsRank.getRankLevel());
                subsRankDtos.add(subsRankDto);
            }

            subsDto.setRanks(subsRankDtos);
            subsDto.setCategory(subs.getCategory().getCategoryName());

            subsDto.setId(subs.getSubsId());
            subsDto.setName(subs.getSubsName());
            subsDto.setInfo(subs.getInfo());
            subsDto.setIcon(subs.getIcon());
            subsDto.setImage(subs.getImage());

            subsDtoList.add(subsDto);

        }

        SubsMainDto subsMainDto = new SubsMainDto();
        subsMainDto.setItems(subsDtoList);

        return subsMainDto;

    }

    @GetMapping("/detail/{subsId}")
    public SubsMainDetailDto showSubsDetail(@PathVariable("subsId")Long subsId,Principal principal){
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);

        if (memberDto != null) {
            Member loginMember = memberService.findByLoginIdWithFavorSubs(memberDto.getLoginId());
            MemberSubsDto memberSubsDto = new MemberSubsDto();
//            memberSubsDto.setSubsLike();
//            memberSubsDto.setReview();
        }
        Subs subs = subsService.findSubsById(subsId);

        SubsDetailDto subsDetailDto = new SubsDetailDto();
        subsDetailDto.setId(subs.getSubsId());
        subsDetailDto.setName(subs.getSubsName());
        subsDetailDto.setInfo(subs.getInfo());
        subsDetailDto.setIcon(subs.getIcon());
        subsDetailDto.setImage(subs.getImage());
        subsDetailDto.setCategory(subs.getCategory().getCategoryName());

        subsDetailDto.setTags(tagService.findTagsBySubsId(subsId));

        List<SubsRank> contentBySubsId = subsService.findContentBySubsId(subsId);
        List<SubsRankDetailDto> subsRankDetailDtoList = new ArrayList<>();

        for (SubsRank subsRank : contentBySubsId) {
            SubsRankDetailDto subsRankDetailDto = new SubsRankDetailDto();
            subsRankDetailDto.setRankName(subsRank.getRankName());
            subsRankDetailDto.setRankLevel(subsRank.getRankLevel());
            subsRankDetailDto.setContent(subsRank.getContents()
                    .stream()
                    .map(s->s.getContent()).toList());
            subsRankDetailDto.setPrice(subsRank.getPrice());
            subsRankDetailDtoList.add(subsRankDetailDto);

        }
        subsDetailDto.setRanks(subsRankDetailDtoList);

        SubsMainDetailDto subsMainDetailDto = new SubsMainDetailDto();
        subsMainDetailDto.setItemDetail(subsDetailDto);


        return subsMainDetailDto;
    }

    @PostMapping("/like/{subsId}")
    public boolean likeSubs(@PathVariable("subsId") Long subsId, Principal principal) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto != null) {
            Member loginMember = memberService.findByLoginId(principal.getName());
            Subs subs = subsService.findSubsById(subsId);
            memberService.createMemberFavorSubs(loginMember, subs);
            subsService.likeSubs(subs);
            return true;
        }
        return false;

    }






}
