package com.alham.ggudok.tempadmin.controller;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsContent;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.tempadmin.dto.TagForm;
import com.alham.ggudok.tempadmin.dto.subs.AdminSubsRankDto;
import com.alham.ggudok.tempadmin.dto.subs.CategoryRegisterDto;
import com.alham.ggudok.tempadmin.dto.subs.SubsContentForm;
import com.alham.ggudok.tempadmin.dto.subs.SubsRegisterDto;
import com.alham.ggudok.tempadmin.service.AdminTagService;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class TempAdminController {

    private final AdminSubsService adminSubsService;

    private final AdminTagService tagService;



    @PostMapping("/category/register")
    public String addCategory(CategoryRegisterDto categoryRegisterDto) {
        adminSubsService.createCategory(categoryRegisterDto.getCategoryName());

        return "redirect:/admin/subs";
    }

    @GetMapping("/category/register")
    public String showCategoryForm(Model model) {
        model.addAttribute("form", new CategoryRegisterDto());
        return "admin/subs/categoryForm";
    }

    @GetMapping("subs")
    public String showSubs(Model model) {
        List<Subs> subsList = adminSubsService.findSubs();
        model.addAttribute("subsList", subsList);
        return "admin/subs/subsList";
    }

    @GetMapping("subs/register")
    public String showSubsForm(Model model) {
        List<Category> categories = adminSubsService.findAllCategories();

        model.addAttribute("form", new SubsRegisterDto());
        model.addAttribute("categories", categories);
        return "admin/subs/subsForm";
    }

    @PostMapping("subs/register")
    public String addSubs(SubsRegisterDto subsRegisterDto) {
        adminSubsService.createSubs(subsRegisterDto.getSubsName(), subsRegisterDto.getCategoryId());
        return "redirect:/admin/subs";
    }

    @GetMapping("subs/{subsId}")
    public String manageSubsRank(@PathVariable("subsId") Long subsId, Model model) {
        Subs subs = adminSubsService.findSubsById(subsId);
        List<SubsRank> subsRanks = subs.getSubsRanks();

        model.addAttribute("subsId", subs.getSubsId());
        model.addAttribute("subsName", subs.getSubsName());
        model.addAttribute("subsRanks", subsRanks);


        return "/admin/subs/subsRankList";
    }

    @GetMapping("subs/{subsId}/subsRank")
    public String showSubsRankForm(@PathVariable("subsId") Long subsId,Model model) {
        Subs subs = adminSubsService.findSubsById(subsId);

        model.addAttribute("subs", subs);
        model.addAttribute("form", new AdminSubsRankDto());

        return "/admin/subs/subsRankForm";
    }

    @PostMapping("subs/{subsId}/subsRank")
    public String addSubsRankForm(@PathVariable("subsId") Long subsId,AdminSubsRankDto adminSubsRankDto) {

        adminSubsService.addSubsRank(subsId, adminSubsRankDto);

        return "redirect:/admin/subs/{subsId}";
    }

    @GetMapping("subs/{subsId}/{subsRankId}/contents")
    public String showSubsContent(@PathVariable("subsId") Long subsId,
                                  @PathVariable("subsRankId") Long subsRankId,
                                  Model model) {

        Subs subs = adminSubsService.findSubsById(subsId);
        SubsRank subsRank = subs.getSubsRanks().stream().filter(rank -> rank.getRankId() == subsRankId).findFirst().get();
        List<SubsContent> contents = subsRank.getContents();

        model.addAttribute("subs", subs);
        model.addAttribute("subsRank", subsRank);
        model.addAttribute("contents", contents);

        return "/admin/subs/subsContentList";
    }

    @GetMapping("subs/{subsId}/{subsRankId}/contents/add")
    public String showSubsContentForm(@PathVariable("subsId") Long subsId,
                                  @PathVariable("subsRankId") Long subsRankId,
                                  Model model) {

        Subs subs = adminSubsService.findSubsById(subsId);
        SubsRank subsRank = subs.getSubsRanks().stream().filter(rank -> rank.getRankId() == subsRankId).findFirst().get();
        List<SubsContent> contents = subsRank.getContents();

        model.addAttribute("form", new SubsContentForm());
        model.addAttribute("subs", subs);
        model.addAttribute("subsRank", subsRank);

        return "/admin/subs/subsContentForm";
    }




    @PostMapping("subs/{subsId}/{subsRankId}/contents")
    public String addSubsContent(@PathVariable("subsId") Long subsId,
                                  @PathVariable("subsRankId") Long subsRankId,
                                  SubsContentForm subsContentForm) {

        adminSubsService.addSubsContent(subsId, subsRankId, subsContentForm.getContent());


        return "redirect:/admin/subs/{subsId}/{subsRankId}/contents";
    }

    @PostMapping("subs/{subsId}/tag")
    public String addSubsTag(@PathVariable("subsId") Long subsId ,TagForm tagForm) {
        adminSubsService.addSubsTag(subsId, tagForm);

        return "redirect:/admin/subs/{subsId}/tag";
    }

    @GetMapping("subs/{subsId}/tag")
    public String showSubsTag(@PathVariable("subsId")Long subsId,Model model) {

        List<Tag> allTag = tagService.findAllTag();
        List<Tag> tagsBySubsId = tagService.findTagsBySubsId(subsId);
        model.addAttribute("subsId",subsId);
        model.addAttribute("form", new TagForm());
        model.addAttribute("tags", allTag);
        model.addAttribute("subsTags", tagsBySubsId);

        return "/admin/subs/subsTagAddForm";

    }


    @GetMapping("tag/add")
    public String showTagForm(Model model) {

        model.addAttribute("form", new TagForm());

        return "/admin/tagForm";
    }

    @GetMapping("tag")
    public String showTags(Model model) {
        List<Tag> tags = tagService.findAllTag();
        model.addAttribute("tags", tags);

        return "/admin/tagList";
    }

    @PostMapping("tag/add")
    public String showTagForm(TagForm tagForm) {

        tagService.saveTag(tagForm.getTagName());

        return "redirect:/admin/tag";
    }




}
