package com.alham.ggudok.service.member;

import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.Review;
import com.alham.ggudok.entity.subs.Subs;
import com.alham.ggudok.repository.member.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;


    /**
     * 멤버 리뷰 쓰기
     *
     * @param member
     * @param subs
     * @return
     */
    @Transactional
    public Review writeReview(Member member, Subs subs, String content, int rating) {
        log.info("writeReview()");
        Optional<Review> optionalReview = member.getReviews().stream()
                .filter(r -> r.getSubs().getSubsId() == subs.getSubsId())
                .findAny();
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            review.update(content, rating);
            return review;
        }else{
            return Review.createReview(member, subs, content,rating);
        }


    }

    public List<Review> findMemberReviews(Member member) {
        log.info("findMemberReviews()");
        Optional<List<Review>> reviewByMember = reviewRepository.findReviewsByMember(member.getMemberId());

        if (reviewByMember.isPresent()) {
            return reviewByMember.get();
        }else{
            return new ArrayList<>();
        }
    }

    public Optional<Review> findMemberSubsReview(Member member, Long subsId) {
        log.info("findMemberSubsReview()");
        return reviewRepository.findReviewByMemberAndSubs(member.getMemberId(), subsId);
    }

    public Optional<List<Review>> findSubsReviewsBySubsId(Long subsId) {
        log.info("findSubsReviewsBySubsId()");
        return reviewRepository.findSubsReviewsBySubsId(subsId);
    }

    public Integer updateRatingAvg(Long subsId) {
        log.info("updateRatingAvg()");
        return reviewRepository.findRatingAvgById(subsId);
    }
}
