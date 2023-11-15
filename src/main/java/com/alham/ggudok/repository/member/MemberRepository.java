package com.alham.ggudok.repository.member;

import com.alham.ggudok.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    @Query("select m from Member m join fetch m.memberRelTags mt join fetch mt.tag where m.loginId = :loginId order by mt.tagSort asc")
    Optional<Member> findByLoginIdWithTags(@Param("loginId") String loginId);


    @Query("select m from Member m left join fetch m.memberFavorSubsList mfs left join fetch mfs.subs s where m.loginId = :loginId")
    Optional<Member> findByLoginIdWithFavorSubs(@Param("loginId")String loginId);

    @Query("select m from Member m left join fetch m.memberHaveSubsList mhs left join fetch mhs.subs s where m.loginId = :loginId")
    Optional<Member> findByLoginIdWithHaveSubs(@Param("loginId")String loginId);

    @Query("select m from Member m left join fetch m.reviews mr left join fetch mr.subs s where m.loginId = :loginId")
    Optional<Member> findMemberWithReviewsByloginId(@Param("loginId") String loginId);

    @Query("select m from Member m left join fetch m.memberHaveSubsList mhs left join fetch mhs.subs")
    List<Member> findAllWithHaveSubs();

    @Query("select m from Member m left join fetch m.memberFavorSubsList mfs left join fetch mfs.subs s")
    List<Member> findAllWithFavorSubs();

    @Query("select m from Member m left join fetch m.memberRelTags mt left join fetch mt.tag")
    List<Member> findAllWithTag();

    @Modifying
    @Query("delete from MemberRelTag mrt where mrt.tag.tagId = :tagId")
    void deleteMemberRelTagByTagId(@Param("tagId") Long tagId);
}
