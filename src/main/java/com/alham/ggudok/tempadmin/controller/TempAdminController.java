package com.alham.ggudok.tempadmin.controller;

import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.subs.*;
import com.alham.ggudok.repository.subs.EventRepository;
import com.alham.ggudok.tempadmin.dto.TagForm;
import com.alham.ggudok.tempadmin.dto.subs.*;
import com.alham.ggudok.tempadmin.service.AdminTagService;
import com.alham.ggudok.tempadmin.service.subs.AdminSubsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class TempAdminController {

    private final AdminSubsService adminSubsService;

    private final AdminTagService tagService;

    private final EventRepository eventRepository;


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

    @Value("${upload.dir}")
    private String uploadDir;


    @PostMapping("subs/{subsId}/upload")
    public String uploadSubsImage(@RequestParam("file") MultipartFile file,Model model) {
        String imgUrl = "";
        if (file.isEmpty()) {
            return "/admin/subs/subsRankList";
        }

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + "/" + fileName);
            file.transferTo(dest);
            imgUrl = dest.getAbsolutePath();

            return "/admin/subs/subsRankList";
        } catch (IOException e) {
            return "/admin/subs/subsRankList";
        }

    }

    @GetMapping("subs/{subsId}/subsRank")
    public String showSubsRankForm(@PathVariable("subsId") Long subsId, Model model) {
        Subs subs = adminSubsService.findSubsById(subsId);

        model.addAttribute("subs", subs);
        model.addAttribute("form", new AdminSubsRankDto());

        return "/admin/subs/subsRankForm";
    }

    @PostMapping("subs/{subsId}/subsRank")
    public String addSubsRankForm(@PathVariable("subsId") Long subsId, AdminSubsRankDto adminSubsRankDto) {

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
    public String addSubsTag(@PathVariable("subsId") Long subsId, TagForm tagForm) {
        adminSubsService.addSubsTag(subsId, tagForm);

        return "redirect:/admin/subs/{subsId}/tag";
    }

    @GetMapping("subs/{subsId}/tag")
    public String showSubsTag(@PathVariable("subsId") Long subsId, Model model) {

        List<Tag> allTag = tagService.findAllTag();
        List<Tag> tagsBySubsId = tagService.findTagsBySubsId(subsId);
        model.addAttribute("subsId", subsId);
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

    @GetMapping("event")
    public String showEvent(Model model) {
        List<EventSubs> eventSubsList = eventRepository.findAllWithSubs();

        model.addAttribute("eventList", eventSubsList);

        return "/admin/subs/eventList";
    }

    @PostMapping("event/add/{subsId}")
    public String addEvent(@PathVariable("subsId") Long subsId) {

        adminSubsService.addEvent(subsId);

        return "redirect:/admin/event";
    }

    @GetMapping("event/{eventId}")
    public String showEventDetail(Model model, @PathVariable("eventId") Long eventId) {
        EventSubs eventSubs = eventRepository.findByIdWithSubs(eventId);
        EventRegisterForm registerForm = new EventRegisterForm();

        registerForm.setInfo(eventSubs.getInfo());
        registerForm.setInfoTag(eventSubs.getInfoTag());

        String startTime = "" + eventSubs.getStartDate().getYear() + eventSubs.getStartDate().getMonthValue()
                + transferStringDay(eventSubs.getStartDate().getDayOfMonth());

        String endTime = "" + eventSubs.getEndDate().getYear() + eventSubs.getEndDate().getMonthValue()
                + transferStringDay(eventSubs.getEndDate().getDayOfMonth());

        registerForm.setStartDate(startTime);
        registerForm.setEndDate(endTime);
        registerForm.setValid(eventSubs.getIsValid());
        registerForm.setImage(eventSubs.getEventImage());

        model.addAttribute("eventSubs", eventSubs);
        model.addAttribute("form", registerForm);

        return "/admin/subs/eventAddForm";

    }

    @PostMapping("event/{eventId}")
    public String addEventDetail(@PathVariable("eventId") Long eventId,EventRegisterForm registerForm) {
        adminSubsService.updateEvent(eventId,registerForm);

        return "redirect:/admin/event/"+eventId;
    }


    public String transferStringDay(int day) {
        String result = String.valueOf(day);
        if (result.length() == 1) {
            result = "0" + result;
        }

        return result;
    }
}
