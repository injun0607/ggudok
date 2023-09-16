package com.alham.ggudok.controller.subs;

import com.alham.ggudok.dto.subs.*;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.service.subs.CategoryService;
import com.alham.ggudok.service.subs.SubsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/{category_eng}")
    public SubsMainDto showSubsWithCategory(@PathVariable("category_eng") String categoryEng) {

        Category category = categoryService.findCateByEngWithSubs(categoryEng);
        List<Subs> subsList = category.getSubsList();

        List<SubsDto> subsDtoList = new ArrayList<>();


        //subs 관련 데이터 가공
        for (Subs subs : subsList) {

            SubsDto subsDto = new SubsDto();
            List<SubsRankDto> subsRankDtos = new ArrayList<>();
            subsDto.setTags(subsService.findTagsBySubsId(subs.getSubsId()));

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
            subsDto.setIcon(subs.getIcon());
            subsDto.setImage(subs.getImage());

            subsDtoList.add(subsDto);

        }

        SubsMainDto subsMainDto = new SubsMainDto();
        subsMainDto.setItems(subsDtoList);

        return subsMainDto;

    }

    @GetMapping("/detail/{subsId}")
    public SubsDetailDto showSubsDetail(@PathVariable("subsId")Long subsId){
        Subs subs = subsService.findSubsById(subsId);

        SubsDetailDto subsDetailDto = new SubsDetailDto();
        subsDetailDto.setId(subs.getSubsId());
        subsDetailDto.setName(subs.getSubsName());
        subsDetailDto.setIcon(subs.getIcon());
        subsDetailDto.setImage(subs.getImage());
        subsDetailDto.setCategory(subs.getCategory().getCategoryName());

        subsDetailDto.setTags(subsService.findTagsBySubsId(subsId));

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

        return subsDetailDto;
    }






}
