package com.alham.ggudok.controller.member;

import com.alham.ggudok.config.security.SecurityUtils;
import com.alham.ggudok.dto.member.*;
import com.alham.ggudok.dto.subs.SubsDto;
import com.alham.ggudok.dto.subs.SubsMainDto;
import com.alham.ggudok.entity.Tag;
import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.MemberFavorSubs;
import com.alham.ggudok.entity.member.MemberHaveSubs;
import com.alham.ggudok.entity.member.Review;
import com.alham.ggudok.entity.subs.RankLevel;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.entity.subs.SubsContent;
import com.alham.ggudok.entity.subs.SubsRank;
import com.alham.ggudok.exception.ErrorResult;
import com.alham.ggudok.exception.member.MemberException;
import com.alham.ggudok.service.TagService;
import com.alham.ggudok.service.member.MemberService;
import com.alham.ggudok.service.member.ReviewService;
import com.alham.ggudok.service.subs.SubsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@CrossOrigin(origins = "*")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    private final ReviewService reviewService;

    private final TagService tagService;

    private final SubsService subsService;

    @Value("${upload.member}")
    private String uploadMember;


    @Value("${download.member}")
    private String downLoadMember;


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResult> memberExceptionHandler(MemberException e) {
        log.error(e.getMessage());
        ErrorResult errorResult = new ErrorResult("BAD", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    private boolean registerMember(@RequestBody MemberRegisterDto registerDto) {

        boolean isSuccess = memberService.registerMember(registerDto);
        if (isSuccess) {
            return true;
        } else {
            return false;
        }
    }

    /*
    이메일 인증하기
    이메일 인증 버튼누른후 -> 인증번호 입력
     */
    @PostMapping("/emailCheck")
    private Map<String, String> checkEmail(@RequestBody MemberRegisterDto registerDto) {
        String checkEmail = registerDto.getLoginId();
        String certCode = memberService.checkEmail(checkEmail);
        Map<String, String> map = new HashMap<>();
        map.put("certCode", certCode);
        return map;


    }

    @GetMapping("/memberInfo")
    private String viewMemberInfo(Principal principal) {
        MemberDto memberDto = (MemberDto) principal;
        String loginId = memberDto.getLoginId();
        memberService.viewMemberInfo(loginId);
        return null;

    }

    @GetMapping("/update")
    private MemberUpdateDto updateViewMember(Principal principal) {
        MemberDto memberDto = isLoginUser(principal);

        Member member = memberService.findByLoginId(memberDto.getLoginId());
        MemberUpdateDto updateDto = new MemberUpdateDto();
        updateDto.setAge(member.getAge());
        updateDto.setPhoneNumber(member.getPhoneNumber());
        updateDto.setGender(member.getGender());
        updateDto.setMemberImg(member.getProfileImage());

        return updateDto;
    }


    @PostMapping("/update")
    private boolean updateMember(@RequestBody MemberUpdateDto updateDto, Principal principal) {
        MemberDto memberDto = isLoginUser(principal);
        Member member = memberService.findByLoginId(memberDto.getLoginId());

        if (!passwordEncoder.matches(updateDto.getPassword(), member.getPassword())) {
            throw new MemberException("현재 비밀번호가 맞지 않습니다!");
        }

        if (!updateDto.getNewPassword().equals(updateDto.getNewPasswordCheck())) {
            throw new MemberException("새로운 비밀번호가 맞지 않습니다");
        }


        if (memberService.updateMember(updateDto, member)) {
            return true;
        } else {
            throw new MemberException("회원 정보 수정에 실패했습니다");
        }
    }


//    서버로 requestParam으로 파일을 받음
    @PostMapping("update/image")
    public ResponseEntity<Map> memberImageUpdate(HttpServletRequest servletRequest, @RequestParam(required = false , value = "profileImage")MultipartFile file, Principal principal) {
        MemberDto memberDto = isLoginUser(principal);
        Member member = memberService.findByLoginId(memberDto.getLoginId());
        String profileImage = servletRequest.getParameter("profileImage");
        Map<String, String> resultMap = new HashMap<>();
        String imgUrl = "";
        if (file == null || file.isEmpty()) {
            resultMap.put("error", "이미지 파일이 존재하지 않습니다");
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        try {

            String contentType = "";

            if (file.getContentType().equals("image/png")) {
                contentType = ".png";
            } else if (file.getContentType().equals("image/jpeg")) {
                contentType = ".jpg";
            } else if (file.getContentType().equals("image/gif")){
                contentType = ".gif";
            } else {
                resultMap.put("error", "올바른 이미지가 아닙니다.");
                return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
            }
            String fileName = member.getMemberId() + "_" + member.getMemberName();
            File dest = new File(uploadMember + fileName+contentType);
            file.transferTo(dest);

            imgUrl = downLoadMember+fileName;
            memberService.updateMemberProfile(member, imgUrl);

            resultMap.put("imageUrl", imgUrl);

            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PostMapping("/add/reltag")
//    public void


    @GetMapping("/reviews")
    public ResponseEntity memberReview(Principal principal) {
        MemberDto memberDto = isLoginUser(principal);
        Member findMember = memberService.findByLoginId(memberDto.getLoginId());

        List<Review> memberReviews = reviewService.findMemberReviews(findMember);
        List<ReviewDto> reviews = memberReviews.stream()
                .map(r -> new ReviewDto(r.getContent(),
                        r.getMember().getMemberName(),
                        r.getSubs().getSubsId(),
                        r.getSubs().getSubsName(),
                        r.getRating(),
                        r.getSubs().getImage()))
                .toList();
        HashMap<String, List<ReviewDto>> result = new HashMap<>();
        result.put("reviews", reviews);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("reviews/delete/{subsId}")
    public boolean deleteReview(Principal principal,@PathVariable("subsId")Long subsId) {
        MemberDto memberDto = isLoginUser(principal);

        memberService.removeReview(memberDto.getLoginId(),subsId);
        return true;
    }


    @GetMapping("/favor_subs")
    public SubsMainDto memberFavorSubsView(Principal principal) {
        MemberDto memberDto = isLoginUser(principal);

        Member loginMember = memberService.findByLoginIdWithFavorSubs(memberDto.getLoginId());
        List<MemberFavorSubs> memberFavorSubsList = loginMember.getMemberFavorSubsList();
        List<Long> subsIdList = memberFavorSubsList.stream().map(mfs -> mfs.getSubs().getSubsId()).collect(Collectors.toList());
        Map<Long, List<Tag>> subsTagMap = tagService.findTagListBySubsIdList(subsIdList);
        SubsMainDto subsMainDto = new SubsMainDto();
        List<SubsDto> result = new ArrayList<>();

        for (MemberFavorSubs memberFavorSubs : memberFavorSubsList) {
            Subs subs = memberFavorSubs.getSubs();
            SubsDto subsDto = new SubsDto();
            subsDto.setId(subs.getSubsId());
            subsDto.setIcon(subs.getIcon());
            subsDto.setName(subs.getSubsName());
            subsDto.setImage(subs.getImage());
            subsDto.setTags(subsTagMap.get(subs.getSubsId()));

            result.add(subsDto);
        }

        subsMainDto.setItems(result);
        return subsMainDto;
    }

    /**
     * 구독중인 서비스
     */
    @GetMapping("/have_subs")
    public MemberHaveSubsDto memberHaveSubsView(Principal principal) {
        MemberDto loginUser = isLoginUser(principal);

        //1.가지고있는 subs들을 가져와서 category별로 분류
        //2.category별로 있는 가격을 체크
        //3.모든 가격을 체크

        Member loginMember = memberService.findByLoginIdWithHaveSubs(loginUser.getLoginId());

        //멤버들이 구매한 subs들의 rankLevel을 분류함
        Map<Long, RankLevel> memberRankLevelMap = new HashMap<>();
        List<MemberHaveSubs> memberHaveSubsList = loginMember.getMemberHaveSubsList();
        List<Subs> subsList = new ArrayList<>();
        List<Long> subsIdList = new ArrayList<>();

        for (MemberHaveSubs memberHaveSubs : memberHaveSubsList) {
            memberRankLevelMap.put(memberHaveSubs.getSubs().getSubsId(),memberHaveSubs.getRankLevel());
            subsList.add(memberHaveSubs.getSubs());
            subsIdList.add(memberHaveSubs.getSubs().getSubsId());
        }

        MemberHaveSubsDto memberHaveSubsDto = new MemberHaveSubsDto();


        List<Subs> subsListWithCategory = subsService.findBySubsListWithCategory(subsList);

        //멤버가 가지고있는 subsRank들을 불러온다.
        //subsRank는 subsId와 memberHaveSubs에있는 rankRevel 이필요함
        List<SubsRank> memberHaveSubsRank = subsService.findSubsRankBySubsListWithAllContent(subsIdList);


        List<MemberHaveSubsWithCatDto> items = new ArrayList<>();

        for (Subs subs : subsListWithCategory) {
            String categoryName = subs.getCategory().getCategoryName();
            String categoryEng = subs.getCategory().getCategoryEng();

            MemberHaveSubsDetail memberHaveSubsDetail = new MemberHaveSubsDetail();
            memberHaveSubsDetail.setSubsName(subs.getSubsName());
            //subsRankList에서 해당 subs에 해당하는 subsRank를 모두가져온다.
            List<SubsRank> subsRankList = memberHaveSubsRank.stream().filter(mhs -> mhs.getSubs().getSubsId()
                    .equals(subs.getSubsId())).collect(Collectors.toList());

            //가져온 subsRank들에서 revel에 알맞는 contents들을 모두가져온다.
            for (SubsRank subsRank : subsRankList) {
                if (subsRank.getRankLevel().equals(memberRankLevelMap.get(subs.getSubsId()))) {
                    List<SubsContent> contents = subsRank.getContents();
                    List<String> contentList = contents.stream().map(c -> c.getContent()).collect(Collectors.toList());

                    memberHaveSubsDetail.setContent(contentList);
                    memberHaveSubsDetail.setPrice(subsRank.getPrice());
                }

            }

            MemberHaveSubsWithCatDto memberHaveSubsWithCatDto = new MemberHaveSubsWithCatDto();

            memberHaveSubsWithCatDto.setCategoryName(categoryName);

            if (items.contains(memberHaveSubsWithCatDto)) {

                memberHaveSubsWithCatDto = items.get(items.indexOf(memberHaveSubsWithCatDto));
                memberHaveSubsWithCatDto.getSubsList().add(memberHaveSubsDetail);

                int price = memberHaveSubsWithCatDto.getTotalPrice() + memberHaveSubsDetail.getPrice();
                memberHaveSubsWithCatDto.setTotalPrice(price);


            }else{
                memberHaveSubsWithCatDto.setCategoryEng(categoryEng);

                List<MemberHaveSubsDetail> memberHaveSubsDetails = new ArrayList<>();
                memberHaveSubsDetails.add(memberHaveSubsDetail);
                memberHaveSubsWithCatDto.setSubsList(memberHaveSubsDetails);

                memberHaveSubsWithCatDto.setTotalPrice(memberHaveSubsDetail.getPrice());

                items.add(memberHaveSubsWithCatDto);

            }

        }
        int totalPrice = items.stream().mapToInt(item -> item.getTotalPrice()).sum();

        memberHaveSubsDto.setItems(items);
        memberHaveSubsDto.setTotalAvg(totalPrice);

        return memberHaveSubsDto;

    }



    @GetMapping("/tagsort_init")
    public void memberTagSortInit() {
        memberService.userRecommendTag();
    }



    @NotNull
    private static MemberDto isLoginUser(Principal principal) {

        MemberDto memberDto = SecurityUtils.transPrincipal(principal);
        if (memberDto == null) {
            throw new MemberException("로그인 되지 않은 회원입니다!");
        }
        return memberDto;
    }
}
