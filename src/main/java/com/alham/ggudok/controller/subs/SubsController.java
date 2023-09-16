package com.alham.ggudok.controller.subs;

import com.alham.ggudok.dto.subs.SubsDto;
import com.alham.ggudok.dto.subs.SubsMainDto;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.service.subs.CategoryService;
import com.alham.ggudok.service.subs.SubsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subs")
public class SubsController {

    private final CategoryService categoryService;
    private final SubsService subsService;


    @GetMapping("/{category_eng}")
    public void showSubsWithCategory(@PathVariable("category_eng") String categoryEng) {

        Category category = categoryService.findCateByEngWithSubs(categoryEng);
        List<Subs> subsList = category.getSubsList();

        List<SubsDto> subsDtoList = new ArrayList<>();


        for (Subs subs : subsList) {
            SubsDto subsDto = new SubsDto();

            subsDto.setCategory(subs.getCategory().getCategoryName());
            subsDto.setSubsId(subs.getSubsId());
            subsDto.setSubsName(subs.getSubsName());
            subsDtoList.add(subsDto);
            subsDto.setTags(subsService.findTagsBySubsId(subs.getSubsId()));

        }




        SubsMainDto subsMainDto = new SubsMainDto();
        subsMainDto.setItems(subsDtoList);


    }


}
