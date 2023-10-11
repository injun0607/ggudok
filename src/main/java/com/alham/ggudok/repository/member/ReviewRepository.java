package com.alham.ggudok.repository.member;

import com.alham.ggudok.entity.member.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r join fetch r.member m join fetch r.subs s where m.memberId =:memberId")
    Optional<List<Review>> findReviewsByMember(@Param("memberId") Long memberId);

    @Query("select r from Review r join fetch r.member m join fetch r.subs s where m.memberId =:memberId and s.subsId =:subsId")
    Optional<Review> findReviewByMemberAndSubs(@Param("memberId") Long memberId, @Param("subsId") Long subsId);


    @Query("select r from Review r join fetch r.member m join fetch r.subs s where s.subsId =:subsId")
    Optional<List<Review>> findSubsReviewsBySubsId(@Param("subsId") Long subsId);

    @Query("select avg(r.rating) from Review r where r.subs.subsId = :subsId")
    Integer findRatingAvgById(@Param("subsId") Long subsId);
}
