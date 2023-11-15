package com.alham.ggudok.controller.session;

import com.alham.ggudok.entity.member.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionMemberDto {
    private String memberName;
    private String loginId;
    private Role role;
}
