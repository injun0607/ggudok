package com.alham.ggudok.config.security.auth;

import com.alham.ggudok.entity.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;

    public SessionMember(Member member) {
        this.name = member.getMemberName();
        this.email = member.getLoginId();
    }
}
