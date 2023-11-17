package com.alham.ggudok.config.security.auth;


import com.alham.ggudok.config.security.auth.userInfo.GoogleInfo;
import com.alham.ggudok.config.security.auth.userInfo.KakaoInfo;
import com.alham.ggudok.config.security.auth.userInfo.NaverInfo;
import com.alham.ggudok.config.security.auth.userInfo.OAuthUserInfo;
import com.alham.ggudok.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuthUserInfo oAuthUserInfo;


    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuthUserInfo oAuthUserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuthUserInfo = oAuthUserInfo;
    }

    public static OAuthAttributes of(OAuth2Type type, String userNameAttributeName, Map<String, Object> attributes) {

        if (type.equals(OAuth2Type.KAKAO)) {
            return ofKakao(userNameAttributeName, attributes);
        } else if (type.equals(OAuth2Type.NAVER)) {
            return ofNaver(userNameAttributeName, attributes);
        }else{
            return ofGoogle(userNameAttributeName, attributes);
        }

    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuthUserInfo(new GoogleInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName,Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuthUserInfo(new KakaoInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName,Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuthUserInfo(new NaverInfo(attributes))
                .build();
    }


    public Member toEntity(OAuthUserInfo oAuthUserInfo) {
        Member member = new Member(oAuthUserInfo.getloginId(),oAuthUserInfo.getloginId());
        return member;
    }
}
