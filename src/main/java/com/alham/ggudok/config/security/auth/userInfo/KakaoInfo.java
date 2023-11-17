package com.alham.ggudok.config.security.auth.userInfo;

import java.util.Map;

public class KakaoInfo extends OAuthUserInfo{

    public KakaoInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    //카카오는 아이디가 아닌 닉네임이기때문에 닉네임 세팅
    @Override
    public String getloginId() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (account == null || profile == null) {
            return null;
        }

        String nickName = (String) profile.get("nickname");
        if (!nickName.contains("@")) {
            nickName = nickName + "@kakao.com";
        }

        return nickName;
    }


}
