package com.alham.ggudok.repository.member;

import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.entity.member.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    @Query("select m from Member m join fetch m.memberRelTags mt join fetch mt.tag where m.loginId = :loginId")
    Optional<Member> findByLoginIdWithTags(@Param("loginId") String loginId);


    @Query("select m from Member m left join fetch m.memberFavorSubsList mfs left join fetch mfs.subs s where m.loginId = :loginId")
    Optional<Member> findByLoginIdWithFavorSubs(@Param("loginId")String loginId);

}
