package com.alham.ggudok.controller.subs;

import com.alham.ggudok.config.security.SecurityUtils;
import com.alham.ggudok.dto.member.MemberDto;
import com.alham.ggudok.dto.member.MemberSubsDto;
import com.alham.ggudok.dto.member.ReviewDto;
import com.alham.ggudok.dto.subs.*;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.Review;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.exception.ErrorResult;
import com.alham.ggudok.exception.member.MemberException;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.member.ReviewService;
import com.alham.ggudok.service.subs.CategoryService;
import com.alham.ggudok.service.subs.SubsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subs")
@CrossOrigin(origins = "*")
public class SubsController {

    private final CategoryService categoryService;
    private final SubsService subsService;
    private final TagService tagService;

    private final ReviewService reviewService;

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResult> memberExceptionHandler(MemberException e) {
        log.error(e.getMessage());
        ErrorResult errorResult = new ErrorResult("BAD", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param categoryEng
     * @return
     */
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

    /**
     * subs상세페이지
     *
     * @param subsId
     * @param principal
     * @return
     */
    //TODO DTO 만드는부분 리팩터링
    @GetMapping("/detail/{subsId}")
    public SubsMainDetailDto showSubsDetail(@PathVariable("subsId") Long subsId, Principal principal) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        MemberSubsDto memberSubsDto = new MemberSubsDto();
        Subs subs = subsService.findSubsById(subsId);

        //로그인이 되어있는 상태라면 memberSubsDto 생성
        if (memberDto != null) {
            Member loginMember = memberService.findByLoginIdWithFavorSubs(memberDto.getLoginId());

            loginMember.getMemberFavorSubsList()
                    .stream()
                    .filter(memberFavorSubs -> memberFavorSubs.getSubs().getSubsId() == subsId)
                    .findFirst().ifPresent(mfs -> memberSubsDto.setSubsLike(true));

            ReviewDto reviewDto = new ReviewDto();

            Optional<Review> optionalMemberReview = reviewService.findMemberSubsReview(loginMember,subsId);

            if (optionalMemberReview.isPresent()) {
                Review review = optionalMemberReview.get();
                reviewDto.setSubsName(subs.getSubsName());
                reviewDto.setSubsId(subsId);
                reviewDto.setMemberName(loginMember.getMemberName());
                reviewDto.setRating(review.getRating());
                reviewDto.setContents(review.getContent());
                memberSubsDto.setReview(reviewDto);

            }

        }

        //subsDetail Dto 생성
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

        //subs관련 rank Dto 변환
        for (SubsRank subsRank : contentBySubsId) {
            SubsRankDetailDto subsRankDetailDto = new SubsRankDetailDto();
            subsRankDetailDto.setRankName(subsRank.getRankName());
            subsRankDetailDto.setRankLevel(subsRank.getRankLevel());
            subsRankDetailDto.setContent(subsRank.getContents()
                    .stream()
                    .map(s -> s.getContent()).toList());
            subsRankDetailDto.setPrice(subsRank.getPrice());
            subsRankDetailDtoList.add(subsRankDetailDto);

        }
        subsDetailDto.setRanks(subsRankDetailDtoList);

        //리뷰 Dto 생성
        Optional<List<Review>> optionalReviews = reviewService.findSubsReviewsBySubsId(subsId);
        if (optionalReviews.isPresent()) {
            List<Review> reviews = optionalReviews.get();
            List<ReviewDto> reviewDtoList = reviews.stream()
                    .map(r ->  new ReviewDto(r.getContent(), subsId, r.getMember().getMemberName(), subs.getSubsName(), r.getRating()))
                    .collect(Collectors.toList());
            subsDetailDto.setReviews(reviewDtoList);
        }

        //subsMainDetail Dto 생성
        SubsMainDetailDto subsMainDetailDto = new SubsMainDetailDto();
        subsMainDetailDto.setItemDetail(subsDetailDto);
        subsMainDetailDto.setMemberInfo(memberSubsDto);


        return subsMainDetailDto;
    }

    @PostMapping("/like/{subsId}")
    public boolean likeSubs(@PathVariable("subsId") Long subsId, Principal principal) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto != null) {
            Member loginMember = memberService.findByLoginIdWithFavorSubs(principal.getName());
            Subs subs = subsService.findSubsById(subsId);
            if (loginMember.getMemberFavorSubsList().stream().filter(mfs -> mfs.getSubs().getSubsId() == subsId).findFirst().isPresent()) {
                return false;
            }
            memberService.createMemberFavorSubs(loginMember, subs);
            subsService.likeSubs(subs);
            return true;
        }
        return false;

    }

    @PostMapping("/dislike/{subsId}")
    public boolean dislikeSubs(@PathVariable("subsId") Long subsId, Principal principal) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto != null) {
            Subs subs = subsService.findSubsById(subsId);
            memberService.removeMemberFavorSubs(memberDto.getLoginId(), subs);
            subsService.dislike(subs);
            return true;
        }
        return false;

    }

    @PostMapping("/write_review/{subsId}")
    public boolean writeReview(@PathVariable("subsId") Long subsId, Principal principal, @RequestBody ReviewDto reviewDto) {
        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto == null) {
            throw new MemberException("로그인한 사용자만 이용이 가능합니다.");
        }
        Member member = memberService.findByLoginId(memberDto.getLoginId());
        Subs subs = subsService.findSubsById(subsId);
        reviewService.writeReview(member, subs, reviewDto.getContents(), reviewDto.getRating());

        return true;
    }
}
