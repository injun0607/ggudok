package com.alham.ggudok.controller.subs;

import com.alham.ggudok.config.security.SecurityUtils;
import com.alham.ggudok.config.security.MemberDto;
import com.alham.ggudok.dto.member.MemberRegisterDto;
import com.alham.ggudok.dto.member.MemberSubsDto;
import com.alham.ggudok.dto.member.ReviewDto;
import com.alham.ggudok.dto.subs.*;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Gender;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.Review;
import com.alham.ggudok.entity.subs.Category;
import com.alham.ggudok.entity.subs.RankLevel;
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
import java.util.*;
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



    Random random = new Random();
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
        List<Long> subsIdList = subsList.stream().map(s -> s.getSubsId()).collect(Collectors.toList());
        Map<Long, List<Tag>> subsTagMap = tagService.findTagListBySubsIdList(subsIdList);
        Map<Long, List<SubsRank>> subsRankMap = subsService.findSubsRankMap(subsIdList);

        List<SubsDto> subsDtoList = setSubsDto(subsList,subsTagMap,subsRankMap);

        SubsMainDto subsMainDto = new SubsMainDto();
        subsMainDto.setItems(subsDtoList);

        return subsMainDto;

    }

    @GetMapping("/search")
    public SubsMainDto search(@RequestParam(defaultValue = "") String q) {

        //빈 검색어가 들어왔을때 방어코드
        if (q.isEmpty()) {
            return new SubsMainDto();
        }

        //검색조건은 태그또는 구독서비스 이름

        List<Subs> subsListByQuery = subsService.findSubsListByQuery(q);
        List<Long> subsIdList = subsListByQuery.stream().map(s -> s.getSubsId()).collect(Collectors.toList());
        Map<Long, List<Tag>> subsTagMap = tagService.findTagListBySubsIdList(subsIdList);


        Map<Long, List<SubsRank>> subsRankMap = subsService.findSubsRankMap(subsIdList);


        List<SubsDto> subsDtoList = setSubsDto(subsListByQuery, subsTagMap, subsRankMap);


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
        Subs subs = subsService.findSubsByIdWithCategory(subsId);

        //로그인이 되어있는 상태라면 memberSubsDto 생성
        if (memberDto != null) {
            Member loginMember = memberService.findByLoginIdWithFavorSubs(memberDto.getLoginId());

            loginMember.getMemberFavorSubsList()
                    .stream()
                    .filter(memberFavorSubs -> memberFavorSubs.getSubs().getSubsId() == subsId)
                    .findFirst().ifPresent(mfs -> memberSubsDto.setSubsLike(true));

            loginMember.getMemberHaveSubsList()
                    .stream()
                    .filter(memberHaveSubs -> memberHaveSubs.getSubs().getSubsId() == subsId)
                    .findFirst().ifPresent(mfs -> memberSubsDto.setSubsHave(true));

            ReviewDto reviewDto = new ReviewDto();

            Optional<Review> optionalMemberReview = reviewService.findMemberSubsReview(loginMember,subsId);

            if (optionalMemberReview.isPresent()) {
                Review review = optionalMemberReview.get();
                reviewDto.setSubsName(subs.getSubsName());
                reviewDto.setMemberName(loginMember.getMemberName());
                reviewDto.setSubsId(subsId);
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
        //태그리스트 가져오기
        List<Tag> tagList = tagService.findTagsBySubsId(subsId);
        subsDetailDto.setTags(tagList);

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
        //평균별점 계산
        Optional<List<Review>> optionalReviews = reviewService.findSubsReviewsBySubsId(subsId);
        if (optionalReviews.get().size() != 0) {
            List<Review> reviews = optionalReviews.get();
            List<ReviewDto> reviewDtoList = new ArrayList<>();
            for (Review r : reviews) {
                reviewDtoList.add(new ReviewDto(r.getContent(),
                        r.getMember().getMemberName(),
                        subsId ,subs.getSubsName(),
                        r.getRating(),
                        subs.getImage(),
                        r.getMember().getProfileImage()));
            }
            subsDetailDto.setReviews(reviewDtoList);
            subsDetailDto.setRatingAvg(subs.getRatingAvg());
        }

        //비슷한 아이템 생성
        //카테고리가 똑같은 곳에서 찾아 추천
        List<SubsDto> similarItems = new ArrayList<>();
        String categoryName = subs.getCategory().getCategoryName();
        Tag category = tagList.stream().filter(c -> c.getTagName().equals(categoryName)).findFirst().get();
        List<Subs> similarTagSubsList = subsService.findSubsByTag(category);
        List<Long> similarSubsIdList = similarTagSubsList.stream().map(s -> s.getSubsId()).collect(Collectors.toList());

        Map<Long, List<Tag>> tagListBySubsIdMap = tagService.findTagListBySubsIdList(similarSubsIdList);
        similarTagSubsList.remove(subs);
        for (int i = 0; i < similarTagSubsList.size(); i++) {

            if (i > 9) {
                break;
            }
            Subs similarTagSubs = similarTagSubsList.get(i);
            SubsDto subsDto = new SubsDto();
            subsDto.setId(similarTagSubs.getSubsId());
            subsDto.setName(similarTagSubs.getSubsName());
            subsDto.setImage(similarTagSubs.getImage());
            subsDto.setIcon(similarTagSubs.getIcon());
            subsDto.setTags(tagListBySubsIdMap.get(similarTagSubs.getSubsId()));


            similarItems.add(subsDto);


        }


        //subsMainDetail Dto 생성
        SubsMainDetailDto subsMainDetailDto = new SubsMainDetailDto();
        subsMainDetailDto.setItemDetail(subsDetailDto);
        subsMainDetailDto.setMemberInfo(memberSubsDto);
        subsMainDetailDto.setSimilarItems(similarItems);

        return subsMainDetailDto;
    }

    @GetMapping("/subs_rank/{subsId}")
    public List<SubsRankDetailDto> subsRanksList(@PathVariable("subsId")Long subsId,Principal principal) {
        MemberDto memberDto = isLoginUser(principal);

        List<SubsRankDetailDto> subsRankDetailDtoList = new ArrayList<>();
        List<SubsRank> subsRankList = subsService.findContentBySubsId(subsId);

        for (SubsRank subsRank : subsRankList) {

            SubsRankDetailDto subsRankDetailDto = new SubsRankDetailDto();

            subsRankDetailDto.setPrice(subsRank.getPrice());
            subsRankDetailDto.setRankName(subsRank.getRankName());
            subsRankDetailDto.setRankLevel(subsRank.getRankLevel());
            subsRankDetailDto.setContent(subsRank.getContents().stream().map(sr->sr.getContent()).collect(Collectors.toList()));
            subsRankDetailDtoList.add(subsRankDetailDto);

        }

        return subsRankDetailDtoList;

    }





    @PostMapping("/like/{subsId}")
    public boolean likeSubs(@PathVariable("subsId") Long subsId, Principal principal) {
        MemberDto memberDto = isLoginUser(principal);

        Member loginMember = memberService.findByLoginIdWithFavorSubs(memberDto.getLoginId());
        Subs findSubs = subsService.findSubsByIdWithTag(subsId);
        memberService.likeSubs(loginMember,findSubs);
        memberService.updateMemberTagRecommend(memberDto.getLoginId(), findSubs);
        return true;


    }

    @PostMapping("/dislike/{subsId}")
    public boolean dislikeSubs(@PathVariable("subsId") Long subsId, Principal principal) {
        MemberDto memberDto = isLoginUser(principal);
        Subs subs = subsService.findSubsById(subsId);
        memberService.removeMemberFavorSubs(memberDto.getLoginId(), subs);
        memberService.updateMemberTagRecommend(memberDto.getLoginId(),subs);
        return true;

    }

    @PostMapping("/write_review")
    public boolean writeReview(Principal principal, @RequestBody ReviewDto reviewDto) {
        MemberDto memberDto = isLoginUser(principal);
        Long subsId = reviewDto.getSubsId();
        Member member = memberService.findMemberWithReviewsByloginId(memberDto.getLoginId());

        //구매하지않은 사람은 리뷰 쓰지 못하게하기
        if(!memberService.findByLoginIdWithHaveSubs(memberDto.getLoginId())
                .getMemberHaveSubsList()
                .stream()
                .filter(mhs->mhs.getSubs().getSubsId().equals(subsId))
                .findFirst()
                .isPresent()){
            return false;
        }

        Subs subs = subsService.findSubsById(subsId);
        reviewService.writeReview(member, subs, reviewDto.getContents(), reviewDto.getRating());
        Integer ratingAvg = reviewService.updateRatingAvg(subs.getSubsId());
        subsService.updateRatingAvg(subs,ratingAvg);

        return true;
    }

    @PostMapping("/buy")
    public boolean buySubs(Principal principal ,@RequestBody SubsBuyDto subsBuyDto) {
        MemberDto memberDto = isLoginUser(principal);
        Subs findSubs = subsService.findSubsByIdWithTag(subsBuyDto.getSubsId());
        if (memberService.buySubs(memberDto.getLoginId(), findSubs, subsBuyDto.getRankLevel())) {
            memberService.updateMemberTagRecommend(memberDto.getLoginId(), findSubs);
        }

        return true;
    }

    @GetMapping("/buy_cancel/{subsId}")
    public ResponseEntity<Map> showBuyCancel(Principal principal,@PathVariable("subsId") Long subsId) {
        MemberDto memberDto = isLoginUser(principal);

        Map<String, Object> resultMap = new HashMap<>();

        memberService.findByLoginIdWithHaveSubs(memberDto.getLoginId())
                .getMemberHaveSubsList()
                .stream()
                .filter(mhs->mhs.getSubs().getSubsId() == subsId)
                .findFirst()
                .ifPresent(mhs-> {
                    resultMap.put("haveRank", mhs.getRankLevel());
                });

        List<SubsRankDetailDto> subsRankDetailDtoList = new ArrayList<>();
        List<SubsRank> subsRankList = subsService.findContentBySubsId(subsId);

        for (SubsRank subsRank : subsRankList) {

            SubsRankDetailDto subsRankDetailDto = new SubsRankDetailDto();
            subsRankDetailDto.setPrice(subsRank.getPrice());
            subsRankDetailDto.setRankName(subsRank.getRankName());
            subsRankDetailDto.setRankLevel(subsRank.getRankLevel());
            subsRankDetailDto.setContent(subsRank.getContents().stream().map(sr->sr.getContent()).collect(Collectors.toList()));
            subsRankDetailDtoList.add(subsRankDetailDto);

        }

        resultMap.put("subsRanks", subsRankDetailDtoList);

        return new ResponseEntity(resultMap,HttpStatus.OK);
    }

    @PostMapping("/buy_cancel")
    public boolean buyCancel(Principal principal, @RequestBody SubsBuyDto subsBuyDto) {
        MemberDto loginUser = isLoginUser(principal);
        Subs subs = subsService.findSubsById(subsBuyDto.getSubsId());
        memberService.removeMemberHaveSubs(loginUser.getLoginId(), subs);
        memberService.updateMemberTagRecommend(loginUser.getLoginId(),subs);

        return true;
    }

//    @GetMapping("/sort_init")
//    public boolean sortInit() {
//        subsService.updateRecommendSort();
//        return true;
//    }

    private static MemberDto isLoginUser(Principal principal) {

        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto == null) {
            throw new MemberException("로그인한 사용자만 이용이 가능합니다.");
        } else {
            return memberDto;
        }
    }

/*
    //TODO init임시
    @GetMapping("/init_sort")
    public void initSort() {

        Subs healthCare = subsService.findBySubsName("healthCare");
        Subs dosirak = subsService.findBySubsName("dosirak");
        Subs healthCare2 = subsService.findBySubsName("healthCare2");
        Subs netfilx = subsService.findBySubsName("netfilx");
        Subs watcha = subsService.findBySubsName("watcha");
        Subs randomSubs = subsService.findSubsById(2l);



        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setMemberName("injun");
        memberRegisterDto.setAge(20);
        memberRegisterDto.setPassword("123123");
        memberRegisterDto.setPasswordCheck("123123");
        memberRegisterDto.setGender(Gender.MAN);
        memberRegisterDto.setLoginId("injun06010@naver.com");

        memberService.registerMember(memberRegisterDto);

        MemberRegisterDto memberRegisterDto2 = new MemberRegisterDto();
        memberRegisterDto2.setMemberName("injun");
        memberRegisterDto2.setAge(20);
        memberRegisterDto2.setPassword("123123");
        memberRegisterDto2.setPasswordCheck("123123");
        memberRegisterDto2.setGender(Gender.MAN);
        memberRegisterDto2.setLoginId("injun0601@naver.com");

        memberService.registerMember(memberRegisterDto2);

        MemberRegisterDto memberRegisterDto3 = new MemberRegisterDto();
        memberRegisterDto3.setMemberName("injun");
        memberRegisterDto3.setAge(20);
        memberRegisterDto3.setPassword("123123");
        memberRegisterDto3.setPasswordCheck("123123");
        memberRegisterDto3.setGender(Gender.MAN);
        memberRegisterDto3.setLoginId("injun0602@naver.com");

        memberService.registerMember(memberRegisterDto3);

        MemberRegisterDto memberRegisterDto4 = new MemberRegisterDto();
        memberRegisterDto4.setMemberName("injun");
        memberRegisterDto4.setAge(20);
        memberRegisterDto4.setPassword("123123");
        memberRegisterDto4.setPasswordCheck("123123");
        memberRegisterDto4.setGender(Gender.MAN);
        memberRegisterDto4.setLoginId("injun0603@naver.com");

        memberService.registerMember(memberRegisterDto4);

        MemberRegisterDto memberRegisterDto5 = new MemberRegisterDto();
        memberRegisterDto5.setMemberName("injun");
        memberRegisterDto5.setAge(20);
        memberRegisterDto5.setPassword("123123");
        memberRegisterDto5.setPasswordCheck("123123");
        memberRegisterDto5.setGender(Gender.MAN);
        memberRegisterDto5.setLoginId("injun0604@naver.com");

        memberService.registerMember(memberRegisterDto5);

        memberService.buySubs("injun06010@naver.com", netfilx, RankLevel.DEFAULT);
        memberService.buySubs("injun06010@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun06010@naver.com", healthCare, RankLevel.DEFAULT);
        memberService.buySubs("injun06010@naver.com", dosirak, RankLevel.DEFAULT);
        memberService.buySubs("injun06010@naver.com", healthCare2, RankLevel.DEFAULT);


        memberService.buySubs("injun0601@naver.com", netfilx, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", healthCare, RankLevel.DEFAULT);
        memberService.buySubs("injun0601@naver.com", dosirak, RankLevel.DEFAULT);

        memberService.buySubs("injun0602@naver.com", netfilx, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", watcha, RankLevel.DEFAULT);
        memberService.buySubs("injun0602@naver.com", healthCare, RankLevel.DEFAULT);

        memberService.buySubs("injun0603@naver.com", netfilx, RankLevel.DEFAULT);
        memberService.buySubs("injun0603@naver.com", watcha, RankLevel.DEFAULT);

        memberService.buySubs("injun0604@naver.com", netfilx, RankLevel.DEFAULT);

        memberService.buySubs("choiseo26@naver.com", healthCare, RankLevel.DEFAULT);
        memberService.buySubs("choiseo26@naver.com", healthCare2, RankLevel.DEFAULT);
        memberService.buySubs("choiseo26@naver.com", dosirak, RankLevel.DEFAULT);

        Member injun1 = memberService.findByLoginIdWithFavorSubs("injun06010@naver.com");
        Member injun2 = memberService.findByLoginIdWithFavorSubs("injun0601@naver.com");
        Member injun3 = memberService.findByLoginIdWithFavorSubs("injun0602@naver.com");
        Member injun4 = memberService.findByLoginIdWithFavorSubs("injun0603@naver.com");
        Member injun5 = memberService.findByLoginIdWithFavorSubs("injun0604@naver.com");
        Member seohee = memberService.findByLoginIdWithFavorSubs("choiseo26@naver.com");

        memberService.likeSubs(injun1, netfilx);
        memberService.likeSubs(injun2, netfilx);



        memberService.likeSubs(injun1, healthCare);
        memberService.likeSubs(injun2, healthCare);
        memberService.likeSubs(injun3, healthCare);
        memberService.likeSubs(injun4, healthCare);
        memberService.likeSubs(injun5, healthCare);

        memberService.likeSubs(seohee,healthCare);
        memberService.likeSubs(seohee,healthCare2);
        memberService.likeSubs(seohee,dosirak);


        memberService.likeSubs(injun1, watcha);
        memberService.likeSubs(injun2, watcha);
        memberService.likeSubs(injun3, watcha);
        memberService.likeSubs(injun4, watcha);
        memberService.likeSubs(injun5, watcha);

        memberService.likeSubs(injun1, dosirak);
        memberService.likeSubs(injun2, dosirak);

        memberService.likeSubs(injun1, healthCare2);
        memberService.likeSubs(injun2, healthCare2);
        memberService.likeSubs(injun3, healthCare2);

        reviewService.writeReview(injun1, netfilx, "넷플1", 5);
        reviewService.writeReview(injun2, netfilx, "넷플1", 4);
        reviewService.writeReview(injun3, netfilx, "넷플1", 3);
        reviewService.writeReview(injun4, netfilx, "넷플1", 2);
        reviewService.writeReview(injun5, netfilx, "넷플1", 1);
        reviewService.writeReview(seohee, netfilx, "넷플은 너무 자극적이야", 1);
        reviewService.writeReview(seohee, randomSubs, "랜덤 리뷰지롱", 4);

        reviewService.writeReview(injun1, watcha, "왓챠1", 5);
        reviewService.writeReview(injun2, watcha, "왓챠1", 5);
        reviewService.writeReview(injun3, watcha, "왓챠1", 5);
        reviewService.writeReview(injun4, watcha, "왓챠1", 4);
        reviewService.writeReview(injun5, watcha, "왓챠1", 3);
        reviewService.writeReview(seohee, watcha, "왓챠는 이제 끊을거야", 3);

        reviewService.writeReview(injun1, healthCare, "디즈니1", 5);
        reviewService.writeReview(injun2, healthCare, "디즈니1", 5);
        reviewService.writeReview(injun3, healthCare, "디즈니1", 5);
        reviewService.writeReview(injun4, healthCare, "디즈니1", 5);

        reviewService.writeReview(injun1, dosirak, "tving1", 4);
        reviewService.writeReview(injun2, dosirak, "tving1", 4);
        reviewService.writeReview(injun3, dosirak, "tving1", 5);
        reviewService.writeReview(injun4, dosirak, "tving1", 5);

        reviewService.writeReview(injun1, healthCare2, "laftel1", 4);
        reviewService.writeReview(injun2, healthCare2, "laftel1", 5);
        reviewService.writeReview(injun3, healthCare2, "laftel1", 5);
        reviewService.writeReview(injun4, healthCare2, "laftel1", 5);
        reviewService.writeReview(injun5, healthCare2, "laftel1", 5);

        Integer netRating = reviewService.updateRatingAvg(netfilx.getSubsId());
        Integer watRating = reviewService.updateRatingAvg(watcha.getSubsId());
        Integer hel2Rating = reviewService.updateRatingAvg(healthCare2.getSubsId());
        Integer helRating = reviewService.updateRatingAvg(healthCare.getSubsId());
        Integer doRating = reviewService.updateRatingAvg(dosirak.getSubsId());

        subsService.updateRatingAvg(netfilx,netRating);
        subsService.updateRatingAvg(watcha,watRating);
        subsService.updateRatingAvg(healthCare2,hel2Rating);
        subsService.updateRatingAvg(healthCare,helRating);
        subsService.updateRatingAvg(dosirak,doRating);
    }

 */

    private List<SubsDto> setSubsDto(List<Subs>subsList,Map<Long,List<Tag>>subsTagMap, Map<Long,List<SubsRank>> subsRankMap) {

        List<SubsDto> subsDtoList = new ArrayList<>();

        for (Subs subs : subsList) {
            SubsDto subsDto = new SubsDto();
            List<SubsRankDto> subsRankDtos = new ArrayList<>();
            subsDto.setTags(subsTagMap.get(subs.getSubsId()));

            //subsRankData 가공
            List<SubsRank> ranksBySubsId = subsRankMap.get(subs.getSubsId());

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

            subsDto.setRatingAvg(subs.getRatingAvg());

            subsDtoList.add(subsDto);

        }

        return subsDtoList;
    }
}
