package com.alham.ggudok.controller.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionMemberDto {
    private String memberName;
    private String loginId;
}
