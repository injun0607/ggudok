package com.alham.ggudok.entity.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_USER","일반 사용자")
    ,ADMIN("ROLE_ADMIN", "관리자")
    ,WEB("ROLE_WEB", "웹 사용자");

    private final String key;
    private final String value;


}
