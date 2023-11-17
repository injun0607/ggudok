package com.alham.ggudok.repository.security;


import com.alham.ggudok.entity.security.MemberSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberSecurityRepository extends JpaRepository<MemberSecurity, Long> {

    Optional<MemberSecurity> findMemberSecurityByLoginId(String loginId);

    Optional<MemberSecurity> findByRefreshToken(String refreshToken);
}
